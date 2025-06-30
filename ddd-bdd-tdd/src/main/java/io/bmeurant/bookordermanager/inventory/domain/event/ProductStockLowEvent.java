package io.bmeurant.bookordermanager.inventory.domain.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event published when a product's stock level falls below a predefined low threshold.
 * This event indicates that the product might need to be reordered or restocked.
 */
@Getter
public class ProductStockLowEvent extends ApplicationEvent {
    private final String isbn;
    private final int currentStock;

    /**
     * Constructs a new {@code ProductStockLowEvent}.
     *
     * @param source The object on which the event initially occurred (e.g., the InventoryItem's ISBN).
     * @param isbn The ISBN of the product whose stock is low.
     * @param currentStock The current stock level of the product.
     */
    public ProductStockLowEvent(Object source, String isbn, int currentStock) {
        super(source);
        this.isbn = isbn;
        this.currentStock = currentStock;
    }
}
