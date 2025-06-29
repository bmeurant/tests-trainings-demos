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

        assertNotNull(orderLine, "OrderLine should not be null.");
        assertEquals(productId, orderLine.getProductId(), "Product ID should match the provided value.");
        assertEquals(quantity, orderLine.getQuantity(), "Quantity should match the provided value.");
        assertEquals(price, orderLine.getPrice(), "Price should match the provided value.");
    }

    @Test
    void shouldThrowExceptionWhenProductIdIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderLine(null, 1, new BigDecimal("10.00"));
        }, "Should throw IllegalArgumentException when product ID is null.");
        assertTrue(exception.getMessage().contains("Product ID cannot be null or blank"), "Exception message should indicate null product ID.");
    }

    @Test
    void shouldThrowExceptionWhenProductIdIsBlank() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderLine("", 1, new BigDecimal("10.00"));
        }, "Should throw IllegalArgumentException when product ID is blank.");
        assertTrue(exception.getMessage().contains("Product ID cannot be null or blank"), "Exception message should indicate blank product ID.");
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderLine("978-0321765723", 0, new BigDecimal("10.00"));
        }, "Should throw IllegalArgumentException when quantity is zero.");
        assertTrue(exception.getMessage().contains("Quantity must be positive"), "Exception message should indicate positive quantity.");
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderLine("978-0321765723", -1, new BigDecimal("10.00"));
        }, "Should throw IllegalArgumentException when quantity is negative.");
        assertTrue(exception.getMessage().contains("Quantity must be positive"), "Exception message should indicate positive quantity.");
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderLine("978-0321765723", 1, null);
        }, "Should throw IllegalArgumentException when price is null.");
        assertTrue(exception.getMessage().contains("Price cannot be null"), "Exception message should indicate null price.");
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new OrderLine("978-0321765723", 1, new BigDecimal("-0.01"));
        }, "Should throw IllegalArgumentException when price is negative.");
        assertTrue(exception.getMessage().contains("Price cannot be negative"), "Exception message should indicate negative price.");
    }

    @Test
    void shouldBeEqualWhenAllFieldsAreSame() {
        BigDecimal price = new BigDecimal("25.00");
        OrderLine line1 = new OrderLine("978-0321765723", 2, price);
        OrderLine line2 = new OrderLine("978-0321765723", 2, price);

        assertEquals(line1, line2, "Order lines with same fields should be equal.");
        assertEquals(line1.hashCode(), line2.hashCode(), "Hash codes should be equal for order lines with same fields.");
    }

    @Test
    void shouldNotBeEqualWhenProductIdIsDifferent() {
        BigDecimal price = new BigDecimal("25.00");
        OrderLine line1 = new OrderLine("978-0321765723", 2, price);
        OrderLine line2 = new OrderLine("978-0132350884", 2, price);

        assertNotEquals(line1, line2, "Order lines with different product IDs should not be equal.");
        assertNotEquals(line1.hashCode(), line2.hashCode(), "Hash codes should not be equal for order lines with different product IDs.");
    }
}
