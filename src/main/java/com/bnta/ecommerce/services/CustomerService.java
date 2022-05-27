package com.bnta.ecommerce.services;

import com.bnta.ecommerce.models.Customer;
import com.bnta.ecommerce.repositories.CustomerRepository;
import com.bnta.ecommerce.repositories.PurchaseRepository;
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
    @Autowired
    private PurchaseRepository purchaseRepository;

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

    public Optional<Customer> findByEmailAndPassword(String email, String password) {
        return customerRepository.findByEmailAndPassword(email, password);
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

    public String customerAddCredit(String creditString, String customerEmail) {

        Double credit;

        try {
            credit = Double.parseDouble(creditString);
        }
        catch (NumberFormatException nfe) {
            throw new RuntimeException("Credit must be numbers.");
        }

        if (credit <= 0.0) {
            throw new RuntimeException("Credit must be greater than 0.");
        }

        Optional<Customer> customerOptional = customerRepository.findByEmail(customerEmail);

        if (customerOptional.isEmpty()) {
            throw new RuntimeException("This account no longer exists or the email may be wrong, please try again.");
        }

        if(customerOptional.get().getDeleted()) {
            throw new RuntimeException("This account has been removed, please contact support.");
        }

        customerRepository.customerAddCredit(credit, customerEmail);

        return "You have successfully added " +
                credit +
                " to your account!";
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


    public Integer updateCustomerName(String email, String name) {

        Optional<Customer> customerOptional = customerRepository.findByEmail(email);

        if (customerOptional.isEmpty()) {
            throw new RuntimeException("User not found with this E-Mail.");
        }

        return customerRepository.updateCustomerName(name, customerOptional.get().getId());
    }


    public Integer updateCustomerAddress(String email, String address) {

        Optional<Customer> customerOptional = customerRepository.findByEmail(email);

        if (customerOptional.isEmpty()) {
            throw new RuntimeException("User not found with this E-Mail.");
        }

        return customerRepository.updateCustomerAddress(address, customerOptional.get().getId());
    }


    public Integer updateCustomerMobile(String email, String mobileString) {

        Optional<Customer> customerOptional = customerRepository.findByEmail(email);

        if (customerOptional.isEmpty()) {
            throw new RuntimeException("User not found with this E-Mail.");
        }

        if (!(mobileString.length() == 10)) {
            throw new RuntimeException("Mobile should be of length 10.");
        }

        Long mobile;

        try {
            mobile = Long.parseLong(mobileString);
        }
        catch (NumberFormatException nfe) {
            throw new RuntimeException("Mobile should only contains numbers.");
        }

        return customerRepository.updateCustomerMobile(mobile, customerOptional.get().getId());
    }



    public Integer deleteCustomer(Long id, Boolean permanent) {

        Optional<Customer> customerOptional = customerRepository.findById(id);

        if (customerOptional.isEmpty()) {
            throw new RuntimeException("Customer does not exist!");
        }

        if (permanent) {
            purchaseRepository.deletePurchaseByCustomerId(id);
            customerRepository.customerPermanentDelete(id);
            return null;
        }
        else {
            Boolean customerDeleted = customerOptional.get().getDeleted();

            if (customerDeleted) {
                throw new RuntimeException("Customer is already marked as deleted.");
            }

            return customerRepository.customerSelfDelete(id);
        }

    }



}
