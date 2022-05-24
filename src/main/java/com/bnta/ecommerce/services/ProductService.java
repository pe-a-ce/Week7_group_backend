package com.bnta.ecommerce.services;

import com.bnta.ecommerce.models.Product;
import com.bnta.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductService() {
    }

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public List<Product> returnRelevantProducts(int stockRequired, String manufacturer, Double minPrice, Double maxPrice) throws Exception{

        if (minPrice > maxPrice){
            throw new Exception("Minimum price must be lower than maximum price!");
        }

        List<Product> inStock = productRepository.findProductsMinStock(stockRequired);
        List<Product> priceRange = productRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual(minPrice, maxPrice);
        List<Product> result = inStock.stream().filter(priceRange::contains).collect(Collectors.toList());
        if (manufacturer != null){
            List<Product> byManufacturer = productRepository.findByManufacturerIgnoreCase(manufacturer);
            if (byManufacturer.isEmpty()){
                throw new Exception("No cars by this manufacturer stocked!");
            }
            result = result.stream().filter(byManufacturer::contains).collect(Collectors.toList());
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
}
