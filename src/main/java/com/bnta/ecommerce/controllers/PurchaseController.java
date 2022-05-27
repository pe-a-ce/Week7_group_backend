package com.bnta.ecommerce.controllers;

import com.bnta.ecommerce.dto.AddToBasketDto;
import com.bnta.ecommerce.dto.AlterBasketQuantityDto;
import com.bnta.ecommerce.dto.RemoveFromBasketDto;
import com.bnta.ecommerce.models.Purchase;
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


    // Get Purchase by ID
    @GetMapping("/purchase/{id}")
    public ResponseEntity<Purchase> findByPurchasceId(@PathVariable Long id){
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


    // Add new item to basket
    /*
            payload
            {
                "customerId": "",
                "productId": "",
                "purchaseQuantity": ""
            }
     */
    @PostMapping("/customer_basket/add_item")
    public ResponseEntity addToBasket(
            @RequestBody(required = true) AddToBasketDto addToBasketDto
    ){
        try {
            String status = purchaseService.addToBasket(
                    addToBasketDto.getCustomerId(),
                    addToBasketDto.getProductId(),
                    addToBasketDto.getPurchaseQuantity()
            );
            return ResponseEntity.status(HttpStatus.OK).body(status);
        }
        catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
        }
    }


    @DeleteMapping("/customer_basket/remove_item")
    public ResponseEntity removeFromBasket(
            @RequestBody(required = true)RemoveFromBasketDto removeFromBasketDto
            ) {
        try {
            String status = purchaseService.removeFromBasket(
                    removeFromBasketDto.getCustomerId(),
                    removeFromBasketDto.getProductId()
            );
            return ResponseEntity.status(HttpStatus.OK).body(status);
        }
        catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
        }
    }



    // Purchase all items in basket for a particular customer
    @PutMapping("/customer_basket/purchase_all")
    public ResponseEntity makePurchase(
            @RequestParam(required = true) String customerId
    ) {
        try {
            String status = purchaseService.makePurchase(customerId);
            return ResponseEntity.status(HttpStatus.OK).body(status);
        }
        catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
        }
    }


    @PutMapping("/customer_basket/alter_item_quantity")
    public ResponseEntity alterBasketItemQuantity(
            @RequestBody(required = true) AlterBasketQuantityDto alterBasketQuantityDto
            ) {
        try {
            String status = purchaseService.setItemBasketQuantity(
                    alterBasketQuantityDto.getNewQuantity(),
                    alterBasketQuantityDto.getCustomerId(),
                    alterBasketQuantityDto.getProductId()
            );
            return ResponseEntity.status(HttpStatus.OK).body(status);
        }
        catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
        }
    }
}
