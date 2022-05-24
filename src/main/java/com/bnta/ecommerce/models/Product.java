package com.bnta.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "price")
    private Double price;
    @Column(name = "model")
    private String model;
    @Column(name = "manufacturer")
    private String manufacturer;

    @JsonIgnoreProperties({"product"}
    )
    @OneToOne(mappedBy = "product")
    private Stock stock;

    @OneToMany(mappedBy = "product")
    private List<Purchase> purchases;

    public Product() {}

    public Product(Long id, Double price, String model, String category, Stock stock, List<Purchase> purchases) {
        this.id = id;
        this.price = price;
        this.model = model;
        this.manufacturer = category;
        this.stock = stock;
        this.purchases = purchases;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCategory() {
        return manufacturer;
    }

    public void setCategory(String category) {
        this.manufacturer = category;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }
}
