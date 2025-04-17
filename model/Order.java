package com.amazoff.model;

public class Order {
    private String orderId;
    private String deliveryTime; // Format: HH:MM

    public Order(String orderId, String deliveryTime) {
        this.orderId = orderId;
        this.deliveryTime = deliveryTime;
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
