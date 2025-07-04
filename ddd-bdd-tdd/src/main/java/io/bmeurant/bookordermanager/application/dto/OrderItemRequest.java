package io.bmeurant.bookordermanager.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a request item within an order creation request.
 *
 * @param isbn     The ISBN of the product.
 * @param quantity The quantity of the product.
 */
public record OrderItemRequest(
        @NotBlank(message = "Product ISBN cannot be blank")
        String isbn,
        @NotNull(message = "Quantity cannot be null")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity) {
}