package com.bnta.ecommerce.dto;

public class RemoveFromBasketDto {

    private String customerId;
    private String productId;

    public RemoveFromBasketDto(String customerId,
                               String productId) {
        this.customerId = customerId;
        this.productId = productId;
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
}
