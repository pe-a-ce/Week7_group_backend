package com.bnta.ecommerce.controllers;

import com.bnta.ecommerce.models.Purchase;
import com.bnta.ecommerce.models.Stock;
import com.bnta.ecommerce.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    public PurchaseController() {
    }

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/purchase")  // Get All purchases
    public ResponseEntity<List<Purchase>> getAll(){
        return ResponseEntity.ok().body(purchaseService.getAll());}

    @GetMapping("/purchase/{id}") // Get Purchase by ID
    public ResponseEntity<Purchase> findByPurchaseId(@PathVariable Long id){
        Optional<Purchase> purchaseIdOptional = purchaseService.findByPurchaseId(id);
        if (purchaseIdOptional.isPresent()){
            return ResponseEntity.ok().body(purchaseIdOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/purchases")
    public ResponseEntity<List<Purchase>> searchPurchases(
            @RequestParam(required = false) Optional<Long> customerId,
            @RequestParam(required = false, defaultValue = "0") Integer minQuantity,
            @RequestParam(required = false, defaultValue = "2000") Integer maxQuantity,
            @RequestParam(required = false, defaultValue = "1000-01-01") String fromDate,
            @RequestParam(required = false, defaultValue = "4000-01-01") String toDate,
            @RequestParam(required = false) Optional<String> category
    ) {

        System.out.println(customerId.isPresent());

        return ResponseEntity
                .ok()
                .body(
                        purchaseService.searchAll(1L, minQuantity, maxQuantity, fromDate, toDate, null)
                );
    }

    // Get Purchase by customer id


    // Get purchase by category name
}
