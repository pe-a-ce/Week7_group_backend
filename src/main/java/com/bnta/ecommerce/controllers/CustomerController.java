package com.bnta.ecommerce.controllers;

import com.bnta.ecommerce.models.Customer;
import com.bnta.ecommerce.models.Stock;
import com.bnta.ecommerce.repositories.CustomerRepository;
import com.bnta.ecommerce.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerController() {
    }

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers") // Get All Customers
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return ResponseEntity.ok().body(customerService.getAll());
}
    @GetMapping("/customers/{id}") // Get All Customers by id
    public ResponseEntity<Customer> findByID(@PathVariable Long id){
        Optional<Customer> customerOptional = customerService.findById(id);
        if (customerOptional.isPresent()){
            return ResponseEntity.ok().body(customerOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/customers") // Add new customer
    public ResponseEntity<Customer> postCustomer(@RequestBody Customer customer){
       Customer customer1 = customerService.save(customer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

}


