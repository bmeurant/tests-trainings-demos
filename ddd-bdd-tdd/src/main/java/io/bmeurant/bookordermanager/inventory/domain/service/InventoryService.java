package io.bmeurant.bookordermanager.inventory.domain.service;

import io.bmeurant.bookordermanager.application.dto.InventoryResponse;
import io.bmeurant.bookordermanager.inventory.domain.exception.InsufficientStockException;
import io.bmeurant.bookordermanager.inventory.domain.exception.InventoryItemNotFoundException;
import io.bmeurant.bookordermanager.inventory.domain.model.InventoryItem;
import io.bmeurant.bookordermanager.domain.exception.ValidationException;

/**
 * Domain service for managing inventory-related operations.
 */
public interface InventoryService {

    /**
     * Deducts a specified quantity from the stock of an inventory item.
     * @param isbn The ISBN of the inventory item.
     * @param quantity The quantity to deduct.
     * @return The updated InventoryItem.
     * @throws InventoryItemNotFoundException if the item is not found.
     * @throws InsufficientStockException if stock is insufficient.
     * @throws ValidationException if quantity is not positive.
     */
    InventoryItem deductStock(String isbn, int quantity);

    /**
     * Checks if a specified quantity of an inventory item is available in stock.
     * This method does not modify the stock level.
     * @param isbn The ISBN of the inventory item.
     * @param quantity The quantity to check.
     * @throws InventoryItemNotFoundException if the item is not found.
     * @throws InsufficientStockException if stock is insufficient for the requested quantity.
     * @throws ValidationException if quantity is not positive.
     */
    void checkStock(String isbn, int quantity);

    /**
     * Releases a specified quantity back into the stock of an inventory item.
     * This is typically used when an order is cancelled.
     * @param isbn The ISBN of the inventory item.
     * @param quantity The quantity to release.
     * @throws InventoryItemNotFoundException if the item is not found.
     * @throws ValidationException if quantity is not positive.
     */
    void releaseStock(String isbn, int quantity);

    /**
     * Retrieves the stock level for a specific inventory item by its ISBN.
     *
     * @param isbn The ISBN of the inventory item to retrieve stock for.
     * @return The InventoryResponse containing the stock level.
     * @throws InventoryItemNotFoundException if the item is not found.
     */
    InventoryResponse getStockByIsbn(String isbn);
}
