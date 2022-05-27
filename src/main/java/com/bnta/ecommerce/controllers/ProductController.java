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

    @GetMapping("/get_all_products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    @GetMapping("/filter_products") // Get list of Products, by various optional parameters
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(required = false, name = "Show only in-stock items", defaultValue = "false") Boolean inStockRequired,
            @RequestParam(required = false, name = "Manufacturer") String manufacturer,
            @RequestParam(required = false, name = "Model") String model,
            @RequestParam(required = false, name = "Minimum Price", defaultValue = "0") Double minPrice,
            @RequestParam(required = false, name = "Maximum Price", defaultValue = "100") Double maxPrice
    ) {
        try{
            return ResponseEntity.ok().body(productService.returnRelevantProducts(inStockRequired ? 1 : 0, manufacturer, model, minPrice, maxPrice));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/field_search") // Get list of matching Products from single search query
    public ResponseEntity<List<Product>> searchAllProducts(@RequestParam (name = "Search all cars") String query){
        try{
            return ResponseEntity.ok().body(productService.searchForProducts(query));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping("/create_product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        try {
            return ResponseEntity.ok().body(productService.createProduct(product));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PutMapping("/change_product_attributes")
    public ResponseEntity<Product> changeProductAttributes(
            @RequestParam(name = "Product ID") Long id,
            @RequestParam(required = false, name = "Manufacturer") String manufacturer,
            @RequestParam(required = false, name = "Model") String model,
            @RequestParam(required = false, name = "Price") Double price
    ){
       try {
           return ResponseEntity.ok().body(productService.alterProduct(id, manufacturer, model, price));
       } catch (Exception e){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
       }
    }
}
