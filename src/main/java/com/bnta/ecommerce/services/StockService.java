package com.bnta.ecommerce.services;

import com.bnta.ecommerce.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public StockService() {
    }

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }
}
