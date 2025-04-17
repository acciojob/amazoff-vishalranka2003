package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        // your code here
        orderMap.put(order.getId(),order);
    }

    public void savePartner(String partnerId){
        // your code here
        // create a new partner with given partnerId and save it
        System.out.println("Adding partner: " + partnerId);
        partnerMap.put(partnerId,new DeliveryPartner(partnerId));
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            // your code here
            //add order to given partner's order list
            System.out.println("Before adding order " + orderId + " to partner " + partnerId + ":");
            System.out.println("Partner's current number of orders: " + partnerMap.get(partnerId).getNumberOfOrders());
            partnerToOrderMap.putIfAbsent(partnerId,new HashSet<>());
            //increase order count of partner
            partnerToOrderMap.get(partnerId).add(orderId);

            //assign partner to this order
            orderToPartnerMap.put(orderId,partnerId);
            partnerMap.get(partnerId).setNumberOfOrders(partnerToOrderMap.get(partnerId).size());
            System.out.println("After adding order " + orderId + " to partner " + partnerId + ":");
            System.out.println("Partner's updated number of orders: " + partnerMap.get(partnerId).getNumberOfOrders());
        }
    }

    public Order findOrderById(String orderId){
        // your code here
        System.out.println("orderMap"+orderMap);
        return orderMap.get(orderId);
    }

    public DeliveryPartner findPartnerById(String partnerId){
        // your code here
        partnerId = partnerId.trim();
        System.out.println("Repository layer - Looking for partner: '" + partnerId + "'");
        System.out.println("Current partners: " + partnerMap.keySet());
        DeliveryPartner partner = partnerMap.get(partnerId);
        if (partner != null) {
            System.out.println("Found partner: " + partner);
        } else {
            System.out.println("Partner not found: '" + partnerId + "'");
        }
        return partner;
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        // your code here
        return partnerToOrderMap.get(partnerId).size();
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        // your code here
        return new ArrayList<>(partnerToOrderMap.get(partnerId));
    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
        return new ArrayList<>(orderMap.keySet());
    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID
        if (partnerMap.containsKey(partnerId)){
            if (partnerToOrderMap.containsKey(partnerId)){
                for(String orderId:partnerToOrderMap.get(partnerId)){
                    orderToPartnerMap.remove(orderId);
                }
                partnerToOrderMap.remove(partnerId);
            }
            partnerMap.remove(partnerId);
        }
    }

    public void deleteOrder(String orderId){
        // your code here
        // delete order by ID
        if (orderMap.containsKey(orderId)){
            if (orderToPartnerMap.containsKey(orderId)){
                String partnerId=orderToPartnerMap.get(orderId);
                partnerToOrderMap.get(partnerId).remove(orderId);
                partnerMap.get(partnerId).setNumberOfOrders(partnerToOrderMap.get(partnerId).size());
                orderToPartnerMap.remove(orderId);
            }
            orderMap.remove(orderId);
        }
    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
        return orderMap.size()-orderToPartnerMap.size();
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId) {

        if (!partnerToOrderMap.containsKey(partnerId)) {
            System.out.println("Partner " + partnerId + " not found in partnerToOrderMap.");
            return 0;
        }

        int givenMinutes = convertTimeToMinutes(timeString);
        System.out.println("Given time string: " + timeString);
        System.out.println("Given time in minutes: " + givenMinutes);

        int count = 0;
        for (String orderId : partnerToOrderMap.get(partnerId)) {
            Order order = orderMap.get(orderId);
            if (order != null && order.getDeliveryTime() > givenMinutes) {
                count++;
            }
        }

        System.out.println("Orders left after " + timeString + " for partner " + partnerId + ": " + count);
        return count;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId) {
        if (!partnerToOrderMap.containsKey(partnerId)) return "00:00";

        int latestTime = 0;

        for (String orderId : partnerToOrderMap.get(partnerId)) {
            Order order = orderMap.get(orderId);
            if (order != null) {
                latestTime = Math.max(latestTime, order.getDeliveryTime());
            }
        }

        return String.format("%02d:%02d", latestTime / 60, latestTime % 60);
    }

    private int convertTimeToMinutes(String timeString) {
        String[] parts = timeString.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }}