package io.bmeurant.bookordermanager.order.domain.model;

import io.bmeurant.bookordermanager.domain.exception.ValidationException;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * @param isbn     The International Standard Book Number, identifying the book. Must not be null or blank.
     * @param quantity The quantity of the book ordered. Must be positive.
     * @param price    The price of the book at the time of order. Must not be null and must be non-negative.
     * @throws ValidationException if any validation fails.
     */
    public OrderLine(String isbn, int quantity, BigDecimal price) {
        log.debug("Creating OrderLine for ISBN: {}, Quantity: {}, Price: {}", isbn, quantity, price);
        if (isbn == null || isbn.isBlank()) {
            throw new ValidationException("ISBN cannot be null or blank", OrderLine.class);
        }
        if (quantity <= 0) {
            throw new ValidationException("Quantity must be positive", OrderLine.class);
        }
        if (price == null) {
            throw new ValidationException("Price cannot be null", OrderLine.class);
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Price cannot be negative", OrderLine.class);
        }

        this.isbn = isbn;
        this.quantity = quantity;
        this.price = price;
        log.info("OrderLine created: {}", this);
    }
}
