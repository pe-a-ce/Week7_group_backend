package com.bnta.ecommerce.controllers;

import com.bnta.ecommerce.models.Customer;
import com.bnta.ecommerce.models.Purchase;
import com.bnta.ecommerce.repositories.CustomerRepository;
import com.bnta.ecommerce.services.CustomerService;
import com.bnta.ecommerce.services.ProductService;
import com.bnta.ecommerce.services.PurchaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    PurchaseService purchaseService;

    @Test
    void getAllCustomers() {
//does it correctly get all 100 of our customers
        assertEquals(100, customerService.getAll().size());
    }

    @Test
    @Transactional
    /*
        We need @Transactional to allow Hibernate to load the orders
        our CUSTOMER table does not have the id to product
     */
    void findPurchasesByCustomerID() {
//        GIVEN
        Customer customer3 = customerService.findById(3L).get();

//        THEN
        assertAll("Customer 3 should have 1 order of a toyota corolla",
        ()-> assertEquals(1, customer3.getPurchases().size()),
                ()-> assertEquals("Toyota", customer3.getPurchases().get(0).getProduct().getManufacturer()));

    }


}
