package io.bmeurant.bookordermanager.inventory.domain.service;

import io.bmeurant.bookordermanager.inventory.domain.model.InventoryItem;

/**
 * Domain service for managing inventory-related operations.
 */
public interface InventoryService {

    /**
     * Deducts a specified quantity from the stock of an inventory item.
     * @param isbn The ISBN of the inventory item.
     * @param quantity The quantity to deduct.
     * @return The updated InventoryItem.
     * @throws io.bmeurant.bookordermanager.inventory.domain.exception.InventoryItemNotFoundException if the item is not found.
     * @throws io.bmeurant.bookordermanager.inventory.domain.exception.InsufficientStockException if stock is insufficient.
     * @throws io.bmeurant.bookordermanager.domain.exception.ValidationException if quantity is not positive.
     */
    InventoryItem deductStock(String isbn, int quantity);
}
