package com.bnta.ecommerce.services;

import com.bnta.ecommerce.models.Purchase;
import com.bnta.ecommerce.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
                                 Integer minQuantity,
                                 Integer maxQuantity,
                                 String fromDate,
                                 String toDate,
                                 String manufacturer) {

        System.out.println("minQuantity: " + minQuantity);
        System.out.println("maxQuantity: " + maxQuantity);
        System.out.println("fromDate: " + fromDate);
        System.out.println("toDate: " + toDate);
        System.out.println("category: " + manufacturer);


        if (minQuantity >= maxQuantity) {
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
                minQuantity,
                maxQuantity,
                fromDate,
                toDate,
                "%"+manufacturer.trim()+"%");
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
}


