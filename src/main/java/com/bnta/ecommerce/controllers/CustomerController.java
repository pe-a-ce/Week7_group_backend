package com.bnta.ecommerce.controllers;

import com.bnta.ecommerce.models.Customer;
import com.bnta.ecommerce.models.Stock;
import com.bnta.ecommerce.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    public CustomerController() {
    }

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return ResponseEntity.ok().body(customerService.getAll());
}
    @GetMapping("customers/{id}")
    public ResponseEntity<Customer> findByID(@PathVariable Long id){
        Optional<Customer> customerOptional = customerService.findById(id);
        if (customerOptional.isPresent()){
            return ResponseEntity.ok().body(customerOptional.get());
        }
        return ResponseEntity.notFound().build();
    }


}


