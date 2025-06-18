package com.example.observabilityspring.service;

import com.example.observabilityspring.dto.OrderRequestDTO;
import com.example.observabilityspring.dto.OrderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// For simulating external call in future if needed
// import org.springframework.web.client.RestTemplate;
// import org.springframework.beans.factory.annotation.Value;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final ProductService productService;
    // private final RestTemplate restTemplate; // For future HTTP calls

    // @Value("\${product.service.url}") // Example for future HTTP calls
    // private String productServiceUrl;

    @Autowired
    public OrderService(ProductService productService) { // Add RestTemplate if making HTTP calls
        this.productService = productService;
        // this.restTemplate = restTemplate;
    }

    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO) {
        String orderId = UUID.randomUUID().toString();
        logger.info("Processing order id: {} for product id: {} quantity: {}", orderId, orderRequestDTO.getProductId(), orderRequestDTO.getQuantity());

        // Simulate interaction with ProductService to check stock and update it.
        // In a real microservice, this would be an HTTP call.
        // boolean stockAvailable = productService.checkStock(orderRequestDTO.getProductId(), orderRequestDTO.getQuantity());
        // Example of how an HTTP call might look (if ProductService was external):
        // String url = productServiceUrl + "/products/" + orderRequestDTO.getProductId() + "/checkStock?quantity=" + orderRequestDTO.getQuantity();
        // ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        // boolean stockAvailable = response.getBody() != null && response.getBody();

        boolean stockUpdated = productService.checkAndUpdateStock(orderRequestDTO.getProductId(), orderRequestDTO.getQuantity());

        if (stockUpdated) {
            // Simulate order processing
            logger.info("Order {} placed successfully for product id: {}", orderId, orderRequestDTO.getProductId());
            // Here you might save the order to a database, notify other services, etc.
            return new OrderResponseDTO(orderId, orderRequestDTO.getProductId(), orderRequestDTO.getQuantity(), "SUCCESS", "Order placed and stock updated.");
        } else {
            logger.warn("Order {} failed for product id: {}. Insufficient stock or product not found.", orderId, orderRequestDTO.getProductId());
            return new OrderResponseDTO(orderId, orderRequestDTO.getProductId(), orderRequestDTO.getQuantity(), "FAILED", "Insufficient stock or product not found.");
        }
    }
}
