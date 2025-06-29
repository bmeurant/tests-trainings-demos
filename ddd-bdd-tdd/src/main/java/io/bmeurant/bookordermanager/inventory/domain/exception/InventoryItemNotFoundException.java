package io.bmeurant.bookordermanager.inventory.domain.exception;

import io.bmeurant.bookordermanager.domain.exception.DomainException;

/**
 * Exception thrown when a requested inventory item is not found.
 */
public class InventoryItemNotFoundException extends DomainException {
    /**
     * Constructs a new {@code InventoryItemNotFoundException} with a detail message including the ISBN.
     *
     * @param isbn The ISBN of the inventory item that was not found.
     */
    public InventoryItemNotFoundException(String isbn) {
        super("Inventory item with ISBN " + isbn + " not found.");
    }
}
