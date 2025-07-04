package io.bmeurant.bookordermanager.application.dto;

import java.math.BigDecimal;

/**
 * Represents a single line item in an order response.
 *
 * @param isbn     The ISBN of the product.
 * @param quantity The quantity of the product.
 * @param price    The price of the product at the time of order.
 */
public record OrderLineResponse(
        String isbn,
        Integer quantity,
        BigDecimal price) {
}
