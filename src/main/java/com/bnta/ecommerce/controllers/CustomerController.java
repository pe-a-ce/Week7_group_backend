package com.bnta.ecommerce.controllers;

import com.bnta.ecommerce.dto.CustomerDto;
import com.bnta.ecommerce.models.Customer;
import com.bnta.ecommerce.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    @PostMapping("/customers/add_new")
    public ResponseEntity postCustomer(@RequestBody CustomerDto customerdto){
        try {
            Customer newCustomer = new Customer(
                    customerdto.getName(),
                    customerdto.getUsername(),
                    customerdto.getEmail(),
                    customerdto.getPassword(),
                    customerdto.getAddress()
                    );
            Customer addedCustomer = customerService.save(newCustomer);
            return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
        }
        catch (DataIntegrityViolationException dive) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(dive.getCause().getCause().getMessage());
        }
        catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
        }
    }


    // Security includes: username, email, password

    /*
        payload:
        {
            "email": "",
            "username": ""
        }

     */
    @PutMapping("/customers/security/username")
    public ResponseEntity updateCustomerUsername(@RequestBody Map<String, String> payload) {
        try {
            customerService.updateCustomerUsername(payload.get("username"), payload.get("email"));
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
        }
    }


    @PutMapping("/customers/security/email")
    public ResponseEntity updateCustomerEmail(@RequestBody Map<String, String> payload) {
        try {
            customerService.updateCustomerEmail(payload.get("username"), payload.get("email"));
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
        }
    }

    /*
        payload:
        {
            "email": "",
            "password": ""
        }

     */
    @PutMapping("/customers/security/password")
    public ResponseEntity updateCustomerPassword(@RequestBody Map<String, String> payload) {
        try {
            customerService.updateCustomerPassword(payload.get("email"), payload.get("password"));
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    // Information includes: name, address, number

    @PutMapping("/customers/info/name")
    public ResponseEntity updateCustomerName(@RequestBody Map<String, String> payload) {
        try {
            customerService.updateCustomerName(payload.get("email"), payload.get("name"));
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
        }
    }

    @PutMapping("/customers/info/address")
    public ResponseEntity updateCustomerAddress(@RequestBody Map<String, String> payload) {
        try {
            customerService.updateCustomerAddress(payload.get("email"), payload.get("address"));
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
        }
    }

    @PutMapping("/customers/info/mobile")
    public ResponseEntity updateCustomerMobile(@RequestBody Map<String, String> payload) {
        try {
            customerService.updateCustomerMobile(payload.get("email"), payload.get("mobile"));
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
        }
    }


    @DeleteMapping("/customer/{id}")
    public ResponseEntity customerSelfDelete(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id, false);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @DeleteMapping("/admin/customer/{id}")
    public ResponseEntity customerPermenantDelete(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id, true);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}


