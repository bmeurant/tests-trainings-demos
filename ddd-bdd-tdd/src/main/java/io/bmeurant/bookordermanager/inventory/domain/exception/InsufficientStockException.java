package io.bmeurant.bookordermanager.inventory.domain.exception;

import io.bmeurant.bookordermanager.domain.exception.DomainException;

public class InsufficientStockException extends DomainException {
    public InsufficientStockException(String isbn, int requestedQuantity, int currentStock) {
        super(String.format("Not enough stock for ISBN %s. Requested: %d, Available: %d.", isbn, requestedQuantity, currentStock));
    }
}
