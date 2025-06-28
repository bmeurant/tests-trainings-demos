package io.bmeurant.bookordermanager.inventory.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

/**
 * Represents an inventory item in the inventory domain. An inventory item is identified by its ISBN.
 * It holds the current stock level for a given book.
 */
@Getter
@EqualsAndHashCode(of = "isbn")
@ToString
public class InventoryItem {
    private String isbn;
    private int stock;

    /**
     * Constructs a new InventoryItem instance.
     * All parameters are validated to ensure the inventory item is created in a valid state.
     *
     * @param isbn The International Standard Book Number, identifying the book this inventory item is for. Must not be null or blank.
     * @param stock The current stock level of the book. Must be non-negative.
     * @throws IllegalArgumentException if any validation fails.
     */
    public InventoryItem(String isbn, int stock) {
        Assert.hasText(isbn, "ISBN cannot be null or blank");
        Assert.isTrue(stock >= 0, "Stock cannot be negative");

        this.isbn = isbn;
        this.stock = stock;
    }
}
