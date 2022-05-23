package com.bnta.ecommerce.controllers;

import com.bnta.ecommerce.models.Product;
import com.bnta.ecommerce.repositories.ProductRepository;
import com.bnta.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class ProductController {

    @Autowired
    private ProductService productService;

    public ProductController() {
    }
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    @GetMapping("{search}")
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(required = false, name = "isInStockRequired", defaultValue = "false") Boolean inStockRequired
    ){
        return ResponseEntity.ok().body(productService.returnRelevantProducts(inStockRequired));
    }
}
