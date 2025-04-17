package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {
        this.id=id;
        this.deliveryTime=convertTimeToMinutes(deliveryTime);
    }
    int convertTimeToMinutes(String time) {
        String[] parts = time.split(":");
        // ng HH:MM
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }
    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}