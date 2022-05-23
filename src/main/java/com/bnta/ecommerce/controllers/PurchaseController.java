package com.bnta.ecommerce.controllers;

import com.bnta.ecommerce.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    public PurchaseController() {
    }

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }
}
