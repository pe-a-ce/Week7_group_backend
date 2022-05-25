package com.bnta.ecommerce.controllers;

import com.bnta.ecommerce.models.Purchase;
import com.bnta.ecommerce.models.Stock;
import com.bnta.ecommerce.services.PurchaseService;
import com.bnta.ecommerce.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private StockService stockService;

    public PurchaseController() {}

    public PurchaseController(PurchaseService purchaseService, StockService stockService) {
        this.purchaseService = purchaseService;
        this.stockService = stockService;
    }

    @GetMapping("/purchase/{id}") // Get Purchase by ID
    public ResponseEntity<Purchase> findByPurchaseId(@PathVariable Long id){
        Optional<Purchase> purchaseIdOptional = purchaseService.findByPurchaseId(id);
        if (purchaseIdOptional.isPresent()){
            return ResponseEntity.ok().body(purchaseIdOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/purchases")
    public ResponseEntity searchPurchases(
            @RequestParam(required = false, defaultValue = "1") String minQuantity,
            @RequestParam(required = false, defaultValue = "2000") String maxQuantity,
            @RequestParam(required = false, defaultValue = "1000-01-01") String fromDate,
            @RequestParam(required = false, defaultValue = "4000-01-01") String toDate,
            @RequestParam(required = false, defaultValue = "") String category
    ) {
        try {
            List<Purchase> purchases = purchaseService.searchAll(minQuantity, maxQuantity, fromDate, toDate, category);
            return new ResponseEntity<>(purchases, HttpStatus.OK);
        }
        catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
        }
    }


    //Add new purchase
    /*
            payload
            {
                "customerId": "",
                "productID": "",
                "purchaseQuantity": ""
            }
     */
    @PostMapping("/purchases")
    public ResponseEntity addToBasket(
            @RequestBody(required = true) Map<String, String> payload){

        try {
            String status = purchaseService.addToBasket(
                    payload.get("customerId"),
                    payload.get("productId"),
                    payload.get("purchaseQuantity")
            );
            return ResponseEntity.status(HttpStatus.OK).body(status);
        }
        catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
        }
    }


}
