package io.bmeurant.microservices.spring.orderservice.dto;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, String description, BigDecimal price) {
}
