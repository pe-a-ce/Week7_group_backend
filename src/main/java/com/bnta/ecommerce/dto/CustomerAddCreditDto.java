package com.bnta.ecommerce.dto;

public class CustomerAddCreditDto {

    private String email;
    private String credit;

    public CustomerAddCreditDto(String email, String credit) {
        this.email = email;
        this.credit = credit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
}
