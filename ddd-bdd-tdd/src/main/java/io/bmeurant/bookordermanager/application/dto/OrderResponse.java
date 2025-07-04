package io.bmeurant.bookordermanager.application.dto;

import java.util.List;

/**
 * Represents the response object for an order.
 *
 * @param orderId      The unique identifier of the order.
 * @param customerName The name of the customer who placed the order.
 * @param status       The current status of the order (e.g., PENDING, CONFIRMED, CANCELLED).
 * @param orderLines   A list of line items in the order.
 */
public record OrderResponse(
        String orderId,
        String customerName,
        String status,
        List<OrderLineResponse> orderLines) {
}
