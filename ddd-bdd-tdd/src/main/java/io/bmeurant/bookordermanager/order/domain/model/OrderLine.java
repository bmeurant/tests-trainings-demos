package io.bmeurant.bookordermanager.order.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;

/**
 * Represents a single line item within an order.
 * It captures the product details and the quantity ordered.
 */
@Embeddable
@Getter
@EqualsAndHashCode
@ToString
public class OrderLine {
    private static final Logger log = LoggerFactory.getLogger(OrderLine.class);

    private String isbn;
    private int quantity;
    private BigDecimal price;

    /**
     * Protected constructor for JPA.
     */
    protected OrderLine() {
    }

    /**
     * Constructs a new OrderLine instance.
     * All parameters are validated to ensure the order line is created in a valid state.
     *
     * @param isbn The International Standard Book Number, identifying the book. Must not be null or blank.
     * @param quantity The quantity of the book ordered. Must be positive.
     * @param price The price of the book at the time of order. Must not be null and must be non-negative.
     * @throws IllegalArgumentException if any validation fails.
     */
    public OrderLine(String isbn, int quantity, BigDecimal price) {
        log.debug("Creating OrderLine for ISBN: {}, Quantity: {}, Price: {}", isbn, quantity, price);
        Assert.hasText(isbn, "ISBN cannot be null or blank");
        Assert.isTrue(quantity > 0, "Quantity must be positive");
        Assert.notNull(price, "Price cannot be null");
        Assert.isTrue(price.compareTo(BigDecimal.ZERO) >= 0, "Price cannot be negative");

        this.isbn = isbn;
        this.quantity = quantity;
        this.price = price;
        log.info("OrderLine created: {}", this);
    }
}
