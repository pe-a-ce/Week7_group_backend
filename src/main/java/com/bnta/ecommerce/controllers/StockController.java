package com.bnta.ecommerce.controllers;

import com.bnta.ecommerce.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    @Autowired
    private StockService stockService;

    public StockController() {
    }

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }
}
