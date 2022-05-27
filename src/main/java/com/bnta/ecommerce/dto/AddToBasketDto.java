package com.bnta.ecommerce.dto;

public class AddToBasketDto {

    private String customerId;
    private String productId;
    private String purchaseQuantity;

    public AddToBasketDto(String customerId,
                          String productId,
                          String purchaseQuantity) {
        this.customerId = customerId;
        this.productId = productId;
        this.purchaseQuantity = purchaseQuantity;
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

    public String getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(String purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }
}
