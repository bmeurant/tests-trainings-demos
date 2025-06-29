package io.bmeurant.bookordermanager.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a request for an item within an order.
 * This is a Data Transfer Object (DTO) used for input to the OrderService.
 */
@AllArgsConstructor
@Getter
@ToString
public class OrderItemRequest {
    private String isbn;
    private int quantity;
}
