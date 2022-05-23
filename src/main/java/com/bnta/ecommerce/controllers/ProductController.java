package com.bnta.ecommerce.controllers;

import com.bnta.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    public ProductController() {
    }

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
}
