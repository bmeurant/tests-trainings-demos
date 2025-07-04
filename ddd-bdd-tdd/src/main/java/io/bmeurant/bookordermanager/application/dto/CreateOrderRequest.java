package io.bmeurant.bookordermanager.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * Represents a request to create a new order.
 *
 * @param customerName The name of the customer placing the order.
 * @param items        A list of items included in the order.
 */
public record CreateOrderRequest(
        @NotBlank(message = "Customer name cannot be blank")
        String customerName,
        @NotEmpty(message = "Order must contain at least one item")
        @Valid
        List<OrderItemRequest> items) {
}
