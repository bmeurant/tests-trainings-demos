package com.example.observabilityspring.controller;

import com.example.observabilityspring.dto.OrderRequestDTO;
import com.example.observabilityspring.dto.OrderResponseDTO;
import com.example.observabilityspring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        logger.info("Received request to place an order for product id: {}", orderRequestDTO.getProductId());
        OrderResponseDTO orderResponse = orderService.placeOrder(orderRequestDTO);
        if ("SUCCESS".equals(orderResponse.getStatus())) {
            return ResponseEntity.ok(orderResponse);
        } else {
            return ResponseEntity.status(400).body(orderResponse); // Bad request if order failed (e.g. out of stock)
        }
    }
}
