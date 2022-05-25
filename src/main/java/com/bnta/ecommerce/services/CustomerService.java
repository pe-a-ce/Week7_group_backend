package com.bnta.ecommerce.services;

import com.bnta.ecommerce.models.Customer;
import com.bnta.ecommerce.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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

    public void deleteCustomer(Long customerId) {
        this.customerRepository.deleteById(customerId);
    }
}

