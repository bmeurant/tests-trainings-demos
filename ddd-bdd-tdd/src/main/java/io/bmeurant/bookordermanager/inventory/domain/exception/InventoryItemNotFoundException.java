package io.bmeurant.bookordermanager.inventory.domain.exception;

import io.bmeurant.bookordermanager.domain.exception.DomainException;

public class InventoryItemNotFoundException extends DomainException {
    public InventoryItemNotFoundException(String isbn) {
        super("Inventory item with ISBN " + isbn + " not found.");
    }
}
