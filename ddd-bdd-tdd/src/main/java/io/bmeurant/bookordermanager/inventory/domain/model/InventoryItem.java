package io.bmeurant.bookordermanager.inventory.domain.model;

import io.bmeurant.bookordermanager.domain.exception.ValidationException;
import io.bmeurant.bookordermanager.inventory.domain.exception.InsufficientStockException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static io.bmeurant.bookordermanager.domain.util.Assertions.*;

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
        assertInventoryItemIsValid(isbn, stock);

        this.isbn = isbn;
        this.stock = stock;
        log.info("InventoryItem created: {}", this);
    }

    private static void assertInventoryItemIsValid(String isbn, int stock) {
        assertHasText(isbn, "ISBN", InventoryItem.class);
        assertIsNonNegative(BigDecimal.valueOf(stock), "Stock", InventoryItem.class);
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
        assertDeductionIsValid(quantity);
        this.stock -= quantity;
        log.info("Stock for InventoryItem {} updated to: {}", this.isbn, this.stock);
    }

    private void assertDeductionIsValid(int quantity) {
        assertIsPositive(quantity, "Quantity to deduct", InventoryItem.class);
        if (this.stock < quantity) {
            throw new InsufficientStockException(this.isbn, quantity, this.stock);
        }
    }
}
