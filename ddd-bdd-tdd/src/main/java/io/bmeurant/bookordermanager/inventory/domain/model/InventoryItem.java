package io.bmeurant.bookordermanager.inventory.domain.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.*;

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
     * @param isbn The International Standard Book Number, identifying the book this inventory item is for. Must not be null or blank.
     * @param stock The current stock level of the book. Must be non-negative.
     * @throws IllegalArgumentException if any validation fails.
     */
    public InventoryItem(String isbn, int stock) {
        log.debug("Creating InventoryItem with ISBN: {}, Stock: {}", isbn, stock);
        Assert.hasText(isbn, "ISBN cannot be null or blank");
        Assert.isTrue(stock >= 0, "Stock cannot be negative");

        this.isbn = isbn;
        this.stock = stock;
        log.info("InventoryItem created: {}", this);
    }

    /**
     * Deducts the specified quantity from the current stock.
     * @param quantity The quantity to deduct. Must be positive and not exceed current stock.
     * @throws IllegalArgumentException if quantity is invalid or exceeds available stock.
     */
    public void deductStock(int quantity) {
        log.debug("Deducting {} from stock for InventoryItem {}. Current stock: {}", quantity, this.isbn, this.stock);
        Assert.isTrue(quantity > 0, "Quantity to deduct must be positive");
        Assert.isTrue(this.stock >= quantity, "Not enough stock to deduct " + quantity + ". Current stock: " + this.stock);
        this.stock -= quantity;
        log.info("Stock for InventoryItem {} updated to: {}", this.isbn, this.stock);
    }
}
