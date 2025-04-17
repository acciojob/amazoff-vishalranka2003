package com.driver;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import static java.lang.Math.max;

@Repository
public class OrderRepository {

    private static final Logger log = LoggerFactory.getLogger(OrderRepository.class);
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
         orderMap.put(order.getId(), order);
    }

    public void savePartner(String partnerId){
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);

        partnerMap.put(partnerId,deliveryPartner );
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            partnerToOrderMap.putIfAbsent(partnerId,new HashSet<>());
            partnerToOrderMap.get(partnerId).add(orderId);
            DeliveryPartner partner = partnerMap.get(partnerId);
            partner.setNumberOfOrders(partnerToOrderMap.get(partnerId).size());
            orderToPartnerMap.put(orderId,partnerId);

        }
    }

    public Order findOrderById(String orderId){
        if(!orderMap.containsKey(orderId)) {
            return null;
        }
        return orderMap.get(orderId);
    }

    public DeliveryPartner findPartnerById(String partnerId){

        if(!partnerMap.containsKey(partnerId)) {
            return null;
        }
        return partnerMap.get(partnerId);
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        if(partnerMap.containsKey(partnerId)){
            return partnerMap.get(partnerId).getNumberOfOrders();
        }
        return 0;
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        List<String> orderOfPartner = new ArrayList<>();
        if(partnerToOrderMap.containsKey(partnerId)){
            orderOfPartner.addAll(partnerToOrderMap.get(partnerId));
        }
        return orderOfPartner;
    }

    public List<String> findAllOrders(){
        List<String> allOrders = new ArrayList<>();
        for(Map.Entry<String, Order> entry : orderMap.entrySet()){
            allOrders.add(entry.getKey());
        }
        return allOrders;


    }

    public void deletePartner(String partnerId){
        partnerMap.remove(partnerId);
        HashSet<String> orderIdsToUnassign = partnerToOrderMap.remove(partnerId);

        if(orderIdsToUnassign != null && !orderIdsToUnassign.isEmpty()){
            for(String orderId : orderIdsToUnassign){
                orderToPartnerMap.remove(orderId);
            }
        }
    }

    public void deleteOrder(String orderId){
        orderMap.remove(orderId);
        String partnerId = orderToPartnerMap.remove(orderId);

        if (partnerId != null && partnerToOrderMap.containsKey(partnerId)) {
            partnerToOrderMap.get(partnerId).remove(orderId);
            if (partnerToOrderMap.get(partnerId).isEmpty()) {
                partnerToOrderMap.remove(partnerId);
            }
        }

    }

    public Integer findCountOfUnassignedOrders(){
        int countOfUnassignedOrders = 0;

        for(String orderId : orderMap.keySet()){
            if(!orderToPartnerMap.containsKey(orderId)){
                countOfUnassignedOrders++;
            }
        }

        return countOfUnassignedOrders;

    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        int count = 0;
        try {
            int hh = Integer.parseInt(timeString.substring(0, 2));
            int mm = Integer.parseInt(timeString.substring(3));
            int givenTime = hh * 60 + mm;

            HashSet<String> orderIdList = partnerToOrderMap.get(partnerId);
            if (orderIdList == null || orderIdList.isEmpty()) {
                return 0;  // If no orders for the partner
            }

            for (String orderId : orderIdList) {
                Order order = orderMap.get(orderId);
                if (order != null && givenTime > order.getDeliveryTime()) {
                    count++;
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Unexpected error", e);
        }

        return count;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        if(!partnerMap.containsKey(partnerId)){
            return "No Partner exist with this partnerId";
        }

        if (!partnerToOrderMap.containsKey(partnerId) || partnerToOrderMap.get(partnerId).isEmpty()) {
            return "No orders assigned";
        }
        int lastDeliveryTime = 0;
        HashSet<String> orderIdList = partnerToOrderMap.get(partnerId);

        for(String x : orderIdList){
            int deliveryTime = orderMap.get(x).getDeliveryTime();
            lastDeliveryTime = max(lastDeliveryTime,deliveryTime);
        }
        return String.format("%02d:%02d",lastDeliveryTime/60,lastDeliveryTime%60);
    }

}