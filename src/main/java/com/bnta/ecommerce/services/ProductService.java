package com.bnta.ecommerce.services;

import com.bnta.ecommerce.models.Product;
import com.bnta.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Product> returnRelevantProducts(Boolean inStockRequired){
        int stock = 0;
        if (inStockRequired){
            stock = 1;
        }
        return productRepository.findByStockGreaterThanEqual(stock);
    }
}
