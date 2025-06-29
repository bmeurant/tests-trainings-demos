package io.bmeurant.bookordermanager.inventory.domain.exception;

import io.bmeurant.bookordermanager.domain.exception.DomainException;

/**
 * Exception thrown when there is insufficient stock to fulfill a request.
 */
public class InsufficientStockException extends DomainException {
    /**
     * Constructs a new {@code InsufficientStockException} with a detail message indicating the ISBN,
     * requested quantity, and available stock.
     *
     * @param isbn The ISBN of the product with insufficient stock.
     * @param requestedQuantity The quantity that was requested.
     * @param currentStock The current available stock.
     */
    public InsufficientStockException(String isbn, int requestedQuantity, int currentStock) {
        super(String.format("Not enough stock for ISBN %s. Requested: %d, Available: %d.", isbn, requestedQuantity, currentStock));
    }
}
