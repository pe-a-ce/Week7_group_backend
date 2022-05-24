package com.bnta.ecommerce.controllers;

import com.bnta.ecommerce.models.Customer;
import com.bnta.ecommerce.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    public CustomerController() {}

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Get all customers
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return ResponseEntity.ok().body(customerService.getAll());
    }

    // Get all customers by ID
    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> findByID(@PathVariable Long id){
        Optional<Customer> customerOptional = customerService.findById(id);
        if (customerOptional.isPresent()){
            return ResponseEntity.ok().body(customerOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Add a new customer
    @PostMapping("/customers")
    public ResponseEntity postCustomer(@RequestBody Customer customer){
        try {
            Customer addedCustomer = customerService.save(customer);
            return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
        }
        catch (DataIntegrityViolationException dive) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(dive.getCause().getCause().getMessage());
        }
        catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
        }
    }

}


