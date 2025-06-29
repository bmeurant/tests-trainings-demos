package io.bmeurant.bookordermanager.unit.order.domain.model;

import io.bmeurant.bookordermanager.order.domain.model.OrderLine;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class OrderLineTest {

    @Test
    void shouldCreateOrderLineWithValidParameters() {
        String productId = "978-0321765723";
        int quantity = 2;
        BigDecimal price = new BigDecimal("25.00");

        OrderLine orderLine = new OrderLine(productId, quantity, price);

        assertNotNull(orderLine);
        assertEquals(productId, orderLine.getProductId());
        assertEquals(quantity, orderLine.getQuantity());
        assertEquals(price, orderLine.getPrice());
    }

    @Test
    void shouldThrowExceptionWhenProductIdIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderLine(null, 1, new BigDecimal("10.00"));
        });
        assertTrue(exception.getMessage().contains("Product ID cannot be null or blank"));
    }

    @Test
    void shouldThrowExceptionWhenProductIdIsBlank() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderLine("", 1, new BigDecimal("10.00"));
        });
        assertTrue(exception.getMessage().contains("Product ID cannot be null or blank"));
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderLine("978-0321765723", 0, new BigDecimal("10.00"));
        });
        assertTrue(exception.getMessage().contains("Quantity must be positive"));
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderLine("978-0321765723", -1, new BigDecimal("10.00"));
        });
        assertTrue(exception.getMessage().contains("Quantity must be positive"));
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderLine("978-0321765723", 1, null);
        });
        assertTrue(exception.getMessage().contains("Price cannot be null"));
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderLine("978-0321765723", 1, new BigDecimal("-0.01"));
        });
        assertTrue(exception.getMessage().contains("Price cannot be negative"));
    }

    @Test
    void shouldBeEqualWhenAllFieldsAreSame() {
        BigDecimal price = new BigDecimal("25.00");
        OrderLine line1 = new OrderLine("978-0321765723", 2, price);
        OrderLine line2 = new OrderLine("978-0321765723", 2, price);

        assertEquals(line1, line2);
        assertEquals(line1.hashCode(), line2.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenProductIdIsDifferent() {
        BigDecimal price = new BigDecimal("25.00");
        OrderLine line1 = new OrderLine("978-0321765723", 2, price);
        OrderLine line2 = new OrderLine("978-0132350884", 2, price);

        assertNotEquals(line1, line2);
        assertNotEquals(line1.hashCode(), line2.hashCode());
    }
}
