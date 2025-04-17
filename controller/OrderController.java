package com.amazoff.controller;

import com.amazoff.model.Order;
import com.amazoff.model.DeliveryPartner;
import com.amazoff.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/add-order")
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        orderService.addOrder(order);
        return ResponseEntity.ok("Order added successfully");
    }

    @PostMapping("/add-partner/{partnerId}")
    public ResponseEntity<String> addPartner(@PathVariable String partnerId) {
        orderService.addPartner(partnerId);
        return ResponseEntity.ok("Partner added successfully");
    }

    @PutMapping("/add-order-partner-pair")
    public ResponseEntity<String> assignOrderToPartner(@RequestParam String orderId, @RequestParam String partnerId) {
        orderService.assignOrderToPartner(orderId, partnerId);
        return ResponseEntity.ok("Order assigned to partner successfully");
    }

    @GetMapping("/get-order-by-id/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping("/get-partner-by-id/{partnerId}")
    public ResponseEntity<DeliveryPartner> getPartnerById(@PathVariable String partnerId) {
        return ResponseEntity.ok(orderService.getPartnerById(partnerId));
    }

    @GetMapping("/get-order-count-by-partner-id/{partnerId}")
    public ResponseEntity<Integer> getOrderCountByPartnerId(@PathVariable String partnerId) {
        return ResponseEntity.ok(orderService.getOrdersByPartnerId(partnerId).size());
    }

    @GetMapping("/get-orders-by-partner-id/{partnerId}")
    public ResponseEntity<List<String>> getOrdersByPartnerId(@PathVariable String partnerId) {
        return ResponseEntity.ok(orderService.getOrdersByPartnerId(partnerId));
    }

    @GetMapping("/get-all-orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/get-count-of-unassigned-orders")
    public ResponseEntity<Integer> getUnassignedOrderCount() {
        return ResponseEntity.ok(orderService.getUnassignedOrderCount());
    }

    @GetMapping("/get-count-of-orders-left-after-given-time/{time}/{partnerId}")
    public ResponseEntity<Integer> getOrdersLeftAfterTime(@PathVariable String time, @PathVariable String partnerId) {
        return ResponseEntity.ok(orderService.getOrdersLeftAfterTime(time, partnerId));
    }

    @GetMapping("/get-last-delivery-time/{partnerId}")
    public ResponseEntity<String> getLastDeliveryTime(@PathVariable String partnerId) {
        return ResponseEntity.ok(orderService.getLastDeliveryTime(partnerId));
    }

    @DeleteMapping("/delete-partner-by-id/{partnerId}")
    public ResponseEntity<String> deletePartnerById(@PathVariable String partnerId) {
        orderService.deletePartnerById(partnerId);
        return ResponseEntity.ok("Partner deleted successfully");
    }

    @DeleteMapping("/delete-order-by-id/{orderId}")
    public ResponseEntity<String> deleteOrderById(@PathVariable String orderId) {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.ok("Order deleted successfully");
    }
}
