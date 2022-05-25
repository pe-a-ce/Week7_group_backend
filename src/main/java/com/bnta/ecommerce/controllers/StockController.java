package com.bnta.ecommerce.controllers;

import com.bnta.ecommerce.models.Stock;
import com.bnta.ecommerce.services.StockService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class StockController {

    @Autowired
    private StockService stockService;

    public StockController() {
    }

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }


    @GetMapping("/stock") // Get All Stock
    public ResponseEntity<List<Stock>> getAll(){
    return ResponseEntity.ok().body(stockService.getAll());}

    @GetMapping("/stock/{id}") // Get All Stock by id
        public ResponseEntity<Stock> findByID(@PathVariable Long id){
        Optional<Stock> stockOptional = stockService.findById(id);
        if (stockOptional.isPresent()){
        return ResponseEntity.ok().body(stockOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/stock/{id}")
    public ResponseEntity<Boolean> deleteStock(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(stockService.deleteStock(id));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping("/stock")
    public ResponseEntity<Stock> createStock(
            @RequestParam(name = "Product ID") Long id,
            @RequestParam(required = false, name = "Quantity to add", defaultValue = "0") Integer quantity
    ){
        try{
            return ResponseEntity.ok().body(stockService.recreateDeletedStock(id, quantity));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}

