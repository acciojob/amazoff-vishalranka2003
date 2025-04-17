package com.amazoff.service;

import com.amazoff.model.Order;
import com.amazoff.model.DeliveryPartner;
import com.amazoff.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        orderRepository.addPartner(partnerId);
    }

    public void assignOrderToPartner(String orderId, String partnerId) {
        orderRepository.assignOrderToPartner(orderId, partnerId);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return orderRepository.getPartnerById(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public int getUnassignedOrderCount() {
        return orderRepository.getUnassignedOrderCount();
    }

    public int getOrdersLeftAfterTime(String time, String partnerId) {
        return orderRepository.getOrdersLeftAfterTime(time, partnerId);
    }

    public String getLastDeliveryTime(String partnerId) {
        return orderRepository.getLastDeliveryTime(partnerId);
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }
}
