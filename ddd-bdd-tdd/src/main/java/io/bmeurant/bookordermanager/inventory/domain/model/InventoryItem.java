package io.bmeurant.bookordermanager.inventory.domain.model;

import io.bmeurant.bookordermanager.domain.exception.ValidationException;
import io.bmeurant.bookordermanager.inventory.domain.exception.InsufficientStockException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents an inventory item in the inventory domain. An inventory item is identified by its ISBN.
 * It holds the current stock level for a given book.
 */
@Entity
@Getter
@EqualsAndHashCode(of = "isbn")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryItem {
    private static final Logger log = LoggerFactory.getLogger(InventoryItem.class);

    @Id
    private String isbn;
    private int stock;
    @Version
    private Long version;

    /**
     * Constructs a new InventoryItem instance.
     * All parameters are validated to ensure the inventory item is created in a valid state.
     *
     * @param isbn  The International Standard Book Number, identifying the book this inventory item is for. Must not be null or blank.
     * @param stock The current stock level of the book. Must be non-negative.
     * @throws ValidationException if any validation fails.
     */
    public InventoryItem(String isbn, int stock) {
        log.debug("Creating InventoryItem with ISBN: {}, Stock: {}", isbn, stock);
        if (isbn == null || isbn.isBlank()) {
            throw new ValidationException("ISBN cannot be null or blank", InventoryItem.class);
        }
        if (stock < 0) {
            throw new ValidationException("Stock cannot be negative", InventoryItem.class);
        }

        this.isbn = isbn;
        this.stock = stock;
        log.info("InventoryItem created: {}", this);
    }

    /**
     * Deducts the specified quantity from the current stock.
     *
     * @param quantity The quantity to deduct. Must be positive and not exceed current stock.
     * @throws InsufficientStockException if quantity is invalid or exceeds available stock.
     * @throws ValidationException        if quantity is not positive.
     */
    public void deductStock(int quantity) {
        log.debug("Deducting {} from stock for InventoryItem {}. Current stock: {}", quantity, this.isbn, this.stock);
        if (quantity <= 0) {
            throw new ValidationException("Quantity to deduct must be positive", InventoryItem.class);
        }
        if (this.stock < quantity) {
            throw new InsufficientStockException(this.isbn, quantity, this.stock);
        }
        this.stock -= quantity;
        log.info("Stock for InventoryItem {} updated to: {}", this.isbn, this.stock);
    }
}
