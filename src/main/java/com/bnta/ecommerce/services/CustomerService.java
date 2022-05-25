package com.bnta.ecommerce.services;

import com.bnta.ecommerce.models.Customer;
import com.bnta.ecommerce.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerService() {}

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAll(){
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }


    public Customer save(Customer customer) throws DataIntegrityViolationException {

        String customerUsername = customer.getUsername().trim();

        if (customerUsername.length() == 0) {
            throw new RuntimeException("Username cannot be empty.");
        }
        customer.setUsername(customerUsername);

        String customerEmail = customer.getEmail().trim();

        if (customerEmail.length() == 0) {
            throw new RuntimeException("E-Mail cannot be empty.");
        }
        customer.setEmail(customerEmail);

        return customerRepository.save(customer);
    }


    public Integer updateCustomerUsername(String username, String email) {

        Optional<Customer> customerOptional = customerRepository.findByEmail(email);

        if (customerOptional.isEmpty()) {
            throw new RuntimeException("User not found with this E-Mail");
        }

        try {
            return customerRepository.updateCustomerUsername(username, customerOptional.get().getId());
        }
        catch (DataIntegrityViolationException dive) {
            throw new RuntimeException("This username is already in use, please try another.");
        }
    }


    public Integer updateCustomerEmail(String username, String email) {

        Optional<Customer> customerOptional = customerRepository.findByUsername(username);

        if (customerOptional.isEmpty()) {
            throw new RuntimeException("User not found with this username.");
        }

        try {
            return customerRepository.updateCustomerEmail(email, customerOptional.get().getId());
        }
        catch (DataIntegrityViolationException dive) {
            throw new RuntimeException("This email is already in use, please try another.");
        }
    }


    public Integer updateCustomerPassword(String email, String password) {

        Optional<Customer> customerOptional = customerRepository.findByEmail(email);

        if (customerOptional.isEmpty()) {
            throw new RuntimeException("User not found with this E-Mail.");
        }

        return customerRepository.updateCustomerPassword(password, customerOptional.get().getId());
    }


}
