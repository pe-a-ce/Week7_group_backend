package com.bnta.ecommerce.services;

import com.bnta.ecommerce.models.Purchase;
import com.bnta.ecommerce.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    public PurchaseService() {}

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }


    public Optional<Purchase> findByPurchaseId(Long id) {
        return purchaseRepository.findById(id);
    }

    public List<Purchase> searchAll(
                                 String minQuantity,
                                 String maxQuantity,
                                 String fromDate,
                                 String toDate,
                                 String manufacturer) {

        Integer minQuantityInt;
        Integer maxQuantityInt;

        try {
            minQuantityInt = Integer.parseInt(minQuantity);
            maxQuantityInt = Integer.parseInt(maxQuantity);
        }
        catch (NumberFormatException efe) {
            throw new RuntimeException("Quantity must be provided as a number.");
        }

        if (minQuantityInt < 1 || maxQuantityInt < 1) {
            throw new RuntimeException("Quantity must be at least 1.");
        }

        if (minQuantityInt >= maxQuantityInt) {
            throw new RuntimeException("Min Quantity must be less than Max Quantity.");
        }

        LocalDate toDateDate;
        LocalDate fromDateDate;

        try {
            toDateDate = LocalDate.parse(toDate);
            fromDateDate = LocalDate.parse(fromDate);
        }
        catch (DateTimeParseException dtpe) {
            throw new RuntimeException("Dates needs to be given in the following format: YYYY-MM-DD");
        }

        if (!fromDateDate.isBefore(toDateDate)) {
            throw new RuntimeException("fromDate needs to be before toDate.");
        }

        return purchaseRepository.searchAll(
                                            minQuantityInt,
                                            maxQuantityInt,
                                            fromDate,
                                            toDate,
                                            "%"+manufacturer.trim()+"%"
        );
    }

    public Optional<Purchase> findByProductCustomerId(Long CustomerId, Long ProductId){
        return purchaseRepository.findByProductCustomerId(CustomerId, ProductId);
    }

    public void makePurchase(Long CustomerId, Long ProductId){
        purchaseRepository.makePurchase(CustomerId, ProductId);
    }

    public void updatePurchaseQuantity(Long purchaseId){
        purchaseRepository.updatePurchaseQuantity(purchaseId);
    }

    public String addToBasket(String customerIdString, String productIdString, String purchaseQuantityString) {
        Long customerId;
        Long productId;
        Integer purchaseQuantity;

        try {
            customerId = Long.parseLong(customerIdString);
            productId = Long.parseLong(productIdString);
            purchaseQuantity = Integer.parseInt(purchaseQuantityString);
        }
        catch (NumberFormatException nfe) {
            throw new RuntimeException("IDs must be numbers");
        }

        if (customerId <= 0 || productId <= 0) {
            throw new RuntimeException("IDs must be greater than 0.");
        }

        // check customer wallet

        // alterStockQuantity

        Optional<Purchase> purchaseOptional = purchaseRepository.findByProductCustomerId(customerId, productId);

        if (purchaseOptional.isPresent()) {
            purchaseRepository.updatePurchaseQuantity(purchaseOptional.get().getId());
            return "Purchase quantity updated.";
        }
        purchaseRepository.makePurchase(customerId, productId);
        return "Purchase created";
    }
}


