package com.cba.core.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class OrderController {

    @GetMapping("/{orderId}")
    public ResponseEntity<String> getOrder(@PathVariable Long orderId) {
        // Business logic for fetching an order
        return ResponseEntity.ok("Order details for ID: " + orderId);
    }
}
