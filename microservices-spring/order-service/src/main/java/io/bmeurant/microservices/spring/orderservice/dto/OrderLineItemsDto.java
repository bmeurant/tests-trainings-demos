package io.bmeurant.microservices.spring.orderservice.dto;

import java.math.BigDecimal;

public record OrderLineItemsDto(Long productId, BigDecimal price, Integer quantity) {
}
