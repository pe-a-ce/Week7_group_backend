package com.bnta.ecommerce.services;

import com.bnta.ecommerce.models.Product;
import com.bnta.ecommerce.repositories.ProductRepository;
import com.bnta.ecommerce.repositories.StockRepository;
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

    public List<Product> returnRelevantProducts(Boolean inStockRequired, String category, Double minPrice, Double maxPrice) throws Exception{

        if (minPrice > maxPrice){
            throw new Exception("Minimum price must be lower than maximum price!");
        }
        int stock = inStockRequired ? 1 : 0;

        List<Product> inStock = productRepository.findInStockProducts(stock);
        List<Product> priceRange = productRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual(minPrice, maxPrice);
        List<Product> result = inStock.stream().filter(priceRange::contains).collect(Collectors.toList());
        if (category != null){
            List<Product> inCategory = productRepository.findByCategoryIgnoreCase(category);
            result = result.stream().filter(inCategory::contains).collect(Collectors.toList());
        }
        return result;
    }
}
