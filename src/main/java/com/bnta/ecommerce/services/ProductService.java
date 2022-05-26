package com.bnta.ecommerce.services;

import com.bnta.ecommerce.models.Product;
import com.bnta.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockService stockService;

    public ProductService() {
    }

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public List<Product> returnRelevantProducts(int stockRequired, String manufacturer, String model, Double minPrice, Double maxPrice) throws Exception{

        if (minPrice > maxPrice){
            throw new Exception("Minimum price must be lower than maximum price!");
        }

        List<Product> inStock = productRepository.findProductsMinStock(stockRequired);
        List<Product> priceRange = productRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual(minPrice, maxPrice);
        List<Product> result = inStock.stream().filter(priceRange::contains).collect(Collectors.toList());
        if (manufacturer != null){
            List<Product> byManufacturer = productRepository.findByManufacturerContainingIgnoreCase(manufacturer.trim());
            if (byManufacturer.isEmpty()){
                throw new Exception("No cars by this manufacturer stocked!");
            }
            result = result.stream().filter(o -> byManufacturer.contains(o)).collect(Collectors.toList());
        }
        if (model != null){
            List<Product> byModel = productRepository.findByModelContainingIgnoreCase(model.trim());
            if (byModel.isEmpty()){
                throw new Exception("Model not stocked!");
            }
            result = result.stream().filter(byModel::contains).collect(Collectors.toList());
        }
        if (inStock.isEmpty()){
            throw new Exception("No cars in stock!");
        }
        if (priceRange.isEmpty()) {
            throw new Exception("No cars in this price range!");
        }
        if (result.isEmpty()){
            throw new Exception("No cars found to meet this criteria!");
        }
        return result;
    }

    public Product createProduct(Product product) throws Exception {
        if (product.getId() != null && productRepository.findById(product.getId()).isPresent()){
            throw new Exception("Product ID already in use!");
        }
        Product result = productRepository.save(product);
        result.setStock(stockService.addToStock(result));
        return result;
    }

    public Product alterProduct(Long id, String manufacturer, String model, Double price) throws Exception{
        Optional<Product> oProduct = productRepository.findById(id);

        if (oProduct.isEmpty()){
            throw new Exception("Product not found!");
        }
        Product product = oProduct.get();
        if (manufacturer != null){
            product.setManufacturer(manufacturer);
        }
        if (model != null){
            product.setModel(model);
        }
        if (price != null){
            product.setPrice(price);
        }
        productRepository.save(product);
        return product;
    }

    public List<Product> searchForProducts(String query) throws Exception{
        List<Product> results = Arrays.stream(query.split(" "))
                .map(q -> productRepository.findEitherManufacturerOrModel(q.trim()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        if (results.isEmpty()){
            throw new Exception("No cars found!");
        }
        return results;
    }
}
