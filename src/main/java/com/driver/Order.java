package com.driver;

public class Order {

    private final String id;
    private final int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id=id;
        String[] timeParts = deliveryTime.split(":");
        this.deliveryTime = Integer.parseInt(timeParts[0])*60 + Integer.parseInt(timeParts[1]);
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
