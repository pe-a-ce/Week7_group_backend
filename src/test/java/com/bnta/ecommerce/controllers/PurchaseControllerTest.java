package com.bnta.ecommerce.controllers;

import com.bnta.ecommerce.models.Purchase;
import com.bnta.ecommerce.services.PurchaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PurchaseControllerTest {

    @Autowired
    PurchaseService purchaseService;

    @Test
    @Transactional
    void findOrderandCustomerByPurchaseID() {
        Purchase purchase1 = purchaseService.findByPurchaseId(6L).get();

        assertAll("Purchase with ID 6 should show a Lexus IS was purchased by customer Galina Spary",
                () -> assertEquals("Lexus", purchase1.getProduct().getManufacturer()),
                () -> assertEquals("IS", purchase1.getProduct().getModel()),
                () -> assertEquals("Galina Spary", purchase1.getCustomer().getName()));
    }

    @Test
    void findPurchaseByProductId() {
//will only show what is in the 'basket' so those with false for isPurchased question
        Purchase purchase2 = purchaseService.findByProductCustomerId(1l, 1l).get();

       assertAll("With Product id 1, should return a Lexus IS F purchased by Benita Pipworth",
               ()-> assertEquals("Lexus", purchase2.getProduct().getManufacturer()),
                ()-> assertEquals("IS F", purchase2.getProduct().getModel()),
                ()-> assertEquals("Benita Pipworth", purchase2.getCustomer().getName()));


    }}

