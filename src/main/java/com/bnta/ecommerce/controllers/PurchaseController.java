package com.bnta.ecommerce.controllers;

import com.bnta.ecommerce.models.Purchase;
import com.bnta.ecommerce.models.Stock;
import com.bnta.ecommerce.services.PurchaseService;
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

    public PurchaseController() {}

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
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
            @RequestParam(required = false, defaultValue = "0") Integer minQuantity,
            @RequestParam(required = false, defaultValue = "2000") Integer maxQuantity,
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

//    Put - updating to bought
//    Put - updating purchase quantity

    @PostMapping("/purchase") //Add new purchase
    public ResponseEntity<Purchase> makePurchase(@RequestBody (required = true)Map<String, String> payload){
        String customerId = payload.get("customerId");
        String productId = payload.get("productId");

        List<Purchase> purchaseList = purchaseService.findByProductCustomerId(Long.parseLong(customerId), Long.parseLong(productId));

        if (purchaseList.isEmpty()) {
            purchaseService.makePurchase(Long.parseLong(customerId), Long.parseLong(productId));
            return null;
        }
        System.out.println("anything");
        return null;
    }


}
