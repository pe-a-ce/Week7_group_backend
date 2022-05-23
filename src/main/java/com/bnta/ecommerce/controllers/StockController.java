package com.bnta.ecommerce.controllers;

import com.bnta.ecommerce.models.Stock;
import com.bnta.ecommerce.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class StockController {

    @Autowired
    private StockService stockService;

    public StockController() {
    }

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }


    @GetMapping("/stock")
    public ResponseEntity<List<Stock>> getAll(){
    return ResponseEntity.ok().body(stockService.getAll());}

    @GetMapping("/stock/{id}")
        public ResponseEntity<Stock> findByID(@PathVariable Long id){
        Optional<Stock> stockOptional = stockService.findById(id);
        if (stockOptional.isPresent()){
        return ResponseEntity.ok().body(stockOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
}




