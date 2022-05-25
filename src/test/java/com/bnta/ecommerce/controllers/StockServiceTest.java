package com.bnta.ecommerce.controllers;

import com.bnta.ecommerce.models.Stock;
import com.bnta.ecommerce.services.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockServiceTest {

    @Autowired
    StockService stockService;

    @Test
    void GetAllStock() {
        assertEquals(1000, stockService.getAll().size());
    }

    @Test
    void GetStockQuantityById() {

        Stock stock1 = stockService.findById(9L).get();
        assertEquals(7, stock1.getQuantity());
    }


}

