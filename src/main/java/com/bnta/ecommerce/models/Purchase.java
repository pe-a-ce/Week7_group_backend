package com.bnta.ecommerce.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "purchased")
    private Boolean purchased;
    @Column(name = "purchase_quantity")
    private Integer purchaseQuantity;
    @Column(name = "purchased_date")
    private LocalDate purchasedDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Purchase() {}

    public Purchase(Long id,
                    Boolean purchased,
                    Integer purchaseQuantity,
                    LocalDate purchasedDate,
                    Customer customer,
                    Product product) {
        this.id = id;
        this.purchased = purchased;
        this.purchaseQuantity = purchaseQuantity;
        this.purchasedDate = purchasedDate;
        this.customer = customer;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPurchased() {
        return purchased;
    }

    public void setPurchased(Boolean purchased) {
        this.purchased = purchased;
    }

    public Integer getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(Integer purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public LocalDate getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(LocalDate purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
