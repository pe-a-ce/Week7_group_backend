package com.bnta.ecommerce.services;

import com.bnta.ecommerce.models.Purchase;
import com.bnta.ecommerce.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List getAll() { return purchaseRepository.findAll();}

    public Optional<Purchase> findByPurchaseId(Long id) {
    return purchaseRepository.findById(id);
    }

    public List<Purchase> searchAll(
                                 Integer minQuantity,
                                 Integer maxQuantity,
                                 String fromDate,
                                 String toDate,
                                 String category) {

        System.out.println("minQuantity: " + minQuantity);
        System.out.println("maxQuantity: " + maxQuantity);
        System.out.println("fromDate: " + fromDate);
        System.out.println("toDate: " + toDate);
        System.out.println("category: " + category);





        return purchaseRepository.searchAll(
                minQuantity,
                maxQuantity,
                fromDate,
                toDate);
    }
}
