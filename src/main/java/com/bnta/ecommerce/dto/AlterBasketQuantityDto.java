package com.bnta.ecommerce.dto;

public class AlterBasketQuantityDto {

    private String customerId;
    private String productId;
    private String newQuantity;


    public AlterBasketQuantityDto(String customerId,
                                  String productId,
                                  String newQuantity) {
        this.customerId = customerId;
        this.productId = productId;
        this.newQuantity = newQuantity;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(String newQuantity) {
        this.newQuantity = newQuantity;
    }
}
