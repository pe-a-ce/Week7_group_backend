package com.bnta.ecommerce.controllers;

import com.bnta.ecommerce.models.Product;
import com.bnta.ecommerce.repositories.ProductRepository;
import com.bnta.ecommerce.services.ProductService;
import com.bnta.ecommerce.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/search") // Get Product, by various optional parameters
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(required = false, name = "Show only in-stock items", defaultValue = "false") Boolean inStockRequired,
            @RequestParam(required = false, name = "Manufacturer", defaultValue = "%") String manufacturer,
            @RequestParam(required = false, name = "Minimum Price", defaultValue = "0") Double minPrice,
            @RequestParam(required = false, name = "Maximum Price", defaultValue = "100") Double maxPrice
    ) {
        try{
            return ResponseEntity.ok().body(productService.returnRelevantProducts(inStockRequired ? 1 : 0, manufacturer, minPrice, maxPrice));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/fieldSearch")
    public ResponseEntity<List<Product>> getByManufacturerAndModel(
            @RequestParam(required = false, name = "Manufacturer", defaultValue = "%") String manufacturer,
            @RequestParam(required = false, name = "Model", defaultValue = "%") String model
    ) {
        try{
            return ResponseEntity.ok().body(productService.searchByManufacturerAndModel(manufacturer, model));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        try {
            product.setId(null);
            return ResponseEntity.ok().body(productService.createProduct(product));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(productService.deleteProduct(id));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
