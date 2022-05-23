package com.bnta.ecommerce.controllers;

import com.bnta.ecommerce.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    public CustomerController() {
    }

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
}
