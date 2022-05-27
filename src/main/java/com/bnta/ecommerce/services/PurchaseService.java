package com.bnta.ecommerce.services;

import com.bnta.ecommerce.models.Customer;
import com.bnta.ecommerce.models.Purchase;
import com.bnta.ecommerce.models.Stock;
import com.bnta.ecommerce.repositories.CustomerRepository;
import com.bnta.ecommerce.repositories.ProductRepository;
import com.bnta.ecommerce.repositories.PurchaseRepository;
import com.bnta.ecommerce.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private StockService stockService;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    public PurchaseService() {}

    public PurchaseService(PurchaseRepository purchaseRepository,
                           StockService stockService,
                           StockRepository stockRepository,
                           CustomerRepository customerRepository,
                           ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.stockService = stockService;
        this.stockRepository = stockRepository;
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


    public List<Purchase> getAllPurchasesForCustomer(Long customerId) {

        if (customerId < 1) {
            throw new RuntimeException("ID must be greater than 1.");
        }

        return purchaseRepository.getAllPurchasesForCustomer(customerId);
    }

    public List<Purchase> getBasketForCustomer(Long customerId) {

        if (customerId < 1) {
            throw new RuntimeException("ID must be greater than 1.");
        }

        return purchaseRepository.getBasketForCustomer(customerId);
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

        Optional<Stock> stockOptional = stockRepository.findByProductId(productId);

        if (stockOptional.isEmpty()) {
            throw new RuntimeException("Product not stocked.");
        }

        if (stockOptional.get().getQuantity() < purchaseQuantity) {
            throw new RuntimeException("Not enough in items in stock.");
        }

        Optional<Purchase> purchaseOptional = purchaseRepository.findByProductCustomerId(customerId, productId);

        if (purchaseOptional.isPresent()) {
            purchaseRepository.updateBasketQuantity(purchaseQuantity, purchaseOptional.get().getId());
            return "Purchase quantity updated.";
        }
        purchaseRepository.addToBasket(purchaseQuantity, customerId, productId);
        return "Item added to basket!";
    }


    public String setItemBasketQuantity(String newQuantityString, String customerIdString, String productIDString) {

        Integer newQuantity;
        Long customerId;
        Long productId;

        try {
            newQuantity = Integer.parseInt(newQuantityString);
            productId = Long.parseLong(productIDString);
            customerId = Long.parseLong(customerIdString);
        }
        catch (NumberFormatException nfe) {
            throw new RuntimeException("Inputs must be numbers.");
        }

        if (newQuantity <= 0) {
            throw new RuntimeException("Quantity cannot be less than 1.");
        }

        if (customerId <= 0 || productId <= 0) {
            throw new RuntimeException("IDs must be greater than 0.");
        }

        Optional<Purchase> purchaseOptional = purchaseRepository.findByProductCustomerId(customerId, productId);

        if (purchaseOptional.isEmpty()) {
            throw new RuntimeException("Item no longer in the basket.");
        }

        Long purchaseId = purchaseOptional.get().getId();

        purchaseRepository.setItemBasketQuantity(newQuantity, purchaseId);
        return "Item quantity in basket updated!";
    }


    public String removeFromBasket(String customerIdString, String productIdString) {

        Long customerId;
        Long productId;

        try {
            customerId = Long.parseLong(customerIdString);
            productId = Long.parseLong(productIdString);
        }
        catch (NumberFormatException nfe) {
            throw new RuntimeException("IDs must be numbers.");
        }

        if (customerId <= 0 || productId <= 0) {
            throw new RuntimeException("IDs must be greater than 0.");
        }

        Optional<Purchase> purchaseOptional = purchaseRepository.findByProductCustomerId(customerId, productId);

        if (purchaseOptional.isEmpty()) {
            throw new RuntimeException("Item no longer in the basket.");
        }

        purchaseRepository.removeFromBasket(customerId, productId);
        return "Item removed from basket.";
    }


    public String makePurchase(String customerIdString) {

        Long customerId;

        try {
            customerId = Long.parseLong(customerIdString);
        }
        catch (NumberFormatException nfe) {
            throw new RuntimeException("ID must be a number!");
        }

        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if(customerOptional.isEmpty()) {
            throw new RuntimeException("Customer does not exist.");
        }

        List<Purchase> customerBasket = purchaseRepository.getBasketByCustomerId(customerId);

        if (customerBasket.isEmpty()) {
            throw new RuntimeException("Basket empty!");
        }

        Double basketTotalPrice = 0.00;

        for(Purchase purchase: customerBasket) {
            Optional<Stock> stockOptional = stockRepository.findByProductId(purchase.getProduct().getId());

            if (stockOptional.isEmpty()) {
                throw new RuntimeException("Product " +
                        purchase.getProduct().getManufacturer() +
                        " " +
                        purchase.getProduct().getModel() +
                        " is no longer stocked.");
            }

            Integer currentStockQuantity = stockOptional.get().getQuantity();

            if (currentStockQuantity < purchase.getPurchaseQuantity()) {
                throw new RuntimeException(
                        "Please reduce your quantity for " +
                                purchase.getProduct().getManufacturer() +
                                " " +
                                purchase.getProduct().getModel() +
                                ", this item is low on stock."
                );
            }

            basketTotalPrice += purchase.getPurchaseQuantity() * purchase.getProduct().getPrice();
        }

        Customer customer = customerOptional.get();

        if (basketTotalPrice > customer.getWallet()) {
            throw new RuntimeException(
                    "You are low on credit, the total is " + basketTotalPrice + " , you have " + customer.getWallet() + "."
            );
        }

        customerRepository.updateCustomerWallet(-basketTotalPrice, customer.getId());

        // alter stock quantity and set purchases to purchased with purchased date
        for (Purchase purchase: customerBasket) {
            Stock stock = stockRepository.findByProductId(purchase.getProduct().getId()).get();
            stockRepository.alterStockQuantity(-purchase.getPurchaseQuantity(), stock.getId());
            purchaseRepository.makePurchase(LocalDate.now().toString(), customerId);
        }

        return "Purchase successful!";
    }
}


