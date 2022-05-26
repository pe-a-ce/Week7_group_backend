package com.bnta.ecommerce.dto;

public class CusInfoChangeDto {

    private String email;
    private String changeTo;

    public CusInfoChangeDto(String email, String changeTo) {
        this.email = email;
        this.changeTo = changeTo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChangeTo() {
        return changeTo;
    }

    public void setChangeTo(String changeTo) {
        this.changeTo = changeTo;
    }
}
