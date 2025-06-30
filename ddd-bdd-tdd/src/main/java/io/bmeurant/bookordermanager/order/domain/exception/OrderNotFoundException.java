package io.bmeurant.bookordermanager.order.domain.exception;

import io.bmeurant.bookordermanager.domain.exception.DomainException;

/**
 * Exception thrown when a requested order is not found.
 */
public class OrderNotFoundException extends DomainException {
    /**
     * Constructs a new {@code OrderNotFoundException} with a detail message including the order ID.
     *
     * @param orderId The ID of the order that was not found.
     */
    public OrderNotFoundException(String orderId) {
        super("Order with ID " + orderId + " not found.");
    }
}
