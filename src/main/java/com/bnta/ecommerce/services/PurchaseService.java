package com.bnta.ecommerce.services;

import com.bnta.ecommerce.models.Customer;
import com.bnta.ecommerce.models.Product;
import com.bnta.ecommerce.models.Purchase;
import com.bnta.ecommerce.repositories.CustomerRepository;
import com.bnta.ecommerce.repositories.ProductRepository;
import com.bnta.ecommerce.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionService;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private StockService stockService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    public PurchaseService() {}

    public PurchaseService(PurchaseRepository purchaseRepository,
                           StockService stockService,
                           CustomerRepository customerRepository,
                           ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.stockService = stockService;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public Optional<Purchase> findByPurchaseId(Long id) {
        return purchaseRepository.findById(id);
    }

    public List<Purchase> searchAll(
                                 String minQuantity,
                                 String maxQuantity,
                                 String fromDate,
                                 String toDate,
                                 String manufacturer) {

        Integer minQuantityInt;
        Integer maxQuantityInt;

        try {
            minQuantityInt = Integer.parseInt(minQuantity);
            maxQuantityInt = Integer.parseInt(maxQuantity);
        }
        catch (NumberFormatException efe) {
            throw new RuntimeException("Quantity must be provided as a number.");
        }

        if (minQuantityInt < 1 || maxQuantityInt < 1) {
            throw new RuntimeException("Quantity must be at least 1.");
        }

        if (minQuantityInt >= maxQuantityInt) {
            throw new RuntimeException("Min Quantity must be less than Max Quantity.");
        }

        LocalDate toDateDate;
        LocalDate fromDateDate;

        try {
            toDateDate = LocalDate.parse(toDate);
            fromDateDate = LocalDate.parse(fromDate);
        }
        catch (DateTimeParseException dtpe) {
            throw new RuntimeException("Dates needs to be given in the following format: YYYY-MM-DD");
        }

        if (!fromDateDate.isBefore(toDateDate)) {
            throw new RuntimeException("fromDate needs to be before toDate.");
        }

        return purchaseRepository.searchAll(
                                            minQuantityInt,
                                            maxQuantityInt,
                                            fromDate,
                                            toDate,
                                            "%"+manufacturer.trim()+"%"
        );
    }


    public String addToBasket(String customerIdString, String productIdString, String purchaseQuantityString) {
        Long customerId;
        Long productId;
        Integer purchaseQuantity;

        try {
            customerId = Long.parseLong(customerIdString);
            productId = Long.parseLong(productIdString);
            purchaseQuantity = Integer.parseInt(purchaseQuantityString);
        }
        catch (NumberFormatException nfe) {
            throw new RuntimeException("Inputs must be numbers.");
        }

        if (customerId <= 0 || productId <= 0) {
            throw new RuntimeException("IDs must be greater than 0.");
        }

        if (purchaseQuantity <= 0) {
            throw new RuntimeException("Quantity must be at least 1.");
        }

        // check customer wallet
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isEmpty()) {
            throw new RuntimeException("Customer does not exist.");
        }

        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isEmpty()) {
            throw new RuntimeException("Product does not exist.");
        }



        if (customerOptional.get().getWallet() < (productOptional.get().getPrice() * (double)purchaseQuantity)) {
            return "Not enough credit to purchase item(s)!.";
        }

//        customerRepository.updateCustomerWallet()

        // alterStockQuantity
        try {
            stockService.alterStockQuantityService(productId, -purchaseQuantity);
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        Optional<Purchase> purchaseOptional = purchaseRepository.findByProductCustomerId(customerId, productId);

        if (purchaseOptional.isPresent()) {
            purchaseRepository.updatePurchaseQuantity(purchaseOptional.get().getId());
            return "Purchase quantity updated.";
        }
        purchaseRepository.makePurchase(customerId, productId);
        return "Purchase created";
    }



    public String makePurchase() {
        return null;
    }
}


