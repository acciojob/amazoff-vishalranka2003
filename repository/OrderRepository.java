package com.amazoff.repository;

import com.amazoff.model.Order;
import com.amazoff.model.DeliveryPartner;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {
    private Map<String, Order> orderMap = new HashMap<>();
    private Map<String, DeliveryPartner> partnerMap = new HashMap<>();
    private Map<String, String> orderPartnerMap = new HashMap<>();
    private Map<String, List<String>> partnerOrdersMap = new HashMap<>();

    // CRUD methods for orders and partners
    public void addOrder(Order order) {
        orderMap.put(order.getOrderId(), order);
    }

    public void addPartner(String partnerId) {
        partnerMap.put(partnerId, new DeliveryPartner(partnerId));
    }

    public void assignOrderToPartner(String orderId, String partnerId) {
        orderPartnerMap.put(orderId, partnerId);
        partnerOrdersMap.computeIfAbsent(partnerId, k -> new ArrayList<>()).add(orderId);
    }

    public Order getOrderById(String orderId) {
        return orderMap.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partnerMap.get(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return partnerOrdersMap.getOrDefault(partnerId, new ArrayList<>());
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orderMap.values());
    }

    public int getUnassignedOrderCount() {
        return (int) orderMap.keySet().stream().filter(orderId -> !orderPartnerMap.containsKey(orderId)).count();
    }

    public int getOrdersLeftAfterTime(String time, String partnerId) {
        List<String> orders = partnerOrdersMap.getOrDefault(partnerId, new ArrayList<>());
        return (int) orders.stream()
                .map(orderMap::get)
                .filter(order -> order.getDeliveryTime().compareTo(time) > 0)
                .count();
    }

    public String getLastDeliveryTime(String partnerId) {
        List<String> orders = partnerOrdersMap.getOrDefault(partnerId, new ArrayList<>());
        return orders.stream()
                .map(orderMap::get)
                .map(Order::getDeliveryTime)
                .max(String::compareTo)
                .orElse(null);
    }

    public void deletePartnerById(String partnerId) {
        List<String> orders = partnerOrdersMap.remove(partnerId);
        if (orders != null) {
            orders.forEach(orderPartnerMap::remove);
        }
        partnerMap.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {
        String partnerId = orderPartnerMap.remove(orderId);
        if (partnerId != null) {
            partnerOrdersMap.getOrDefault(partnerId, new ArrayList<>()).remove(orderId);
        }
        orderMap.remove(orderId);
    }

    // Method to get all partners
    public List<DeliveryPartner> getAllPartners() {
        return new ArrayList<>(partnerMap.values());
    }

    // Method to get the count of orders assigned to a partner
    public int getOrderCountByPartnerId(String partnerId) {
        return partnerOrdersMap.getOrDefault(partnerId, new ArrayList<>()).size();
    }

    // Method to check if an order is assigned to a partner
    public boolean isOrderAssigned(String orderId) {
        return orderPartnerMap.containsKey(orderId);
    }

    // Method to get the partner assigned to a specific order
    public String getPartnerAssignedToOrder(String orderId) {
        return orderPartnerMap.get(orderId);
    }

    // Method to clear all data (for testing or reset purposes)
    public void clearAllData() {
        orderMap.clear();
        partnerMap.clear();
        orderPartnerMap.clear();
        partnerOrdersMap.clear();
    }
}
