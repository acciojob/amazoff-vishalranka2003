package com.amazoff.model;

public class DeliveryPartner {
    private String partnerId;

    public DeliveryPartner(String partnerId) {
        this.partnerId = partnerId;
    }

    // Getters and Setters
    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
}
