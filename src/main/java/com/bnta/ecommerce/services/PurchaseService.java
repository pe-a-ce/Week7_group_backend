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

    public PurchaseService() {
    }

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public List getAll() { return purchaseRepository.findAll();}


    public Optional<Purchase> findByPurchaseId(Long id) {
    return purchaseRepository.findById(id);
    }
}
