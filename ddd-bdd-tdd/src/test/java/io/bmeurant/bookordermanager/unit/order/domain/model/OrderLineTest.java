package io.bmeurant.bookordermanager.unit.order.domain.model;

import io.bmeurant.bookordermanager.domain.exception.ValidationException;
import io.bmeurant.bookordermanager.order.domain.model.OrderLine;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderLineTest {

    @Test
    void shouldCreateOrderLineWithValidParameters() {
        String isbn = "978-0321765723";
        int quantity = 2;
        BigDecimal price = new BigDecimal("25.00");

        OrderLine orderLine = new OrderLine(isbn, quantity, price);

        assertNotNull(orderLine, "OrderLine should not be null.");
        assertEquals(isbn, orderLine.getIsbn(), "Product ID should match the provided value.");
        assertEquals(quantity, orderLine.getQuantity(), "Quantity should match the provided value.");
        assertEquals(price, orderLine.getPrice(), "Price should match the provided value.");
    }

    @Test
    void shouldThrowExceptionWhenProductIdIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () -> new OrderLine(null, 1, new BigDecimal("10.00")), "Should throw ValidationException when ISBN is null.");
        assertTrue(exception.getMessage().contains("ISBN cannot be null or blank"), "Exception message should indicate null ISBN.");
        assertEquals(OrderLine.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be OrderLine.");
    }

    @Test
    void shouldThrowExceptionWhenProductIdIsBlank() {
        ValidationException exception = assertThrows(ValidationException.class, () -> new OrderLine("", 1, new BigDecimal("10.00")), "Should throw ValidationException when ISBN is blank.");
        assertTrue(exception.getMessage().contains("ISBN cannot be null or blank"), "Exception message should indicate blank ISBN.");
        assertEquals(OrderLine.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be OrderLine.");
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsZero() {
        ValidationException exception = assertThrows(ValidationException.class, () -> new OrderLine("978-0321765723", 0, new BigDecimal("10.00")), "Should throw ValidationException when quantity is zero.");
        assertTrue(exception.getMessage().contains("Quantity must be positive"), "Exception message should indicate positive quantity.");
        assertEquals(OrderLine.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be OrderLine.");
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsNegative() {
        ValidationException exception = assertThrows(ValidationException.class, () -> new OrderLine("978-0321765723", -1, new BigDecimal("10.00")), "Should throw ValidationException when quantity is negative.");
        assertTrue(exception.getMessage().contains("Quantity must be positive"), "Exception message should indicate positive quantity.");
        assertEquals(OrderLine.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be OrderLine.");
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () -> new OrderLine("978-0321765723", 1, null), "Should throw ValidationException when price is null.");
        assertTrue(exception.getMessage().contains("Price cannot be null"), "Exception message should indicate null price.");
        assertEquals(OrderLine.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be OrderLine.");
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        ValidationException exception = assertThrows(ValidationException.class, () -> new OrderLine("978-0321765723", 1, new BigDecimal("-0.01")), "Should throw ValidationException when price is negative.");
        assertTrue(exception.getMessage().contains("Price cannot be negative"), "Exception message should indicate negative price.");
        assertEquals(OrderLine.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be OrderLine.");
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

        assertNotEquals(line1, line2, "Order lines with different ISBNs should not be equal.");
        // No need to assert hash codes are not equal, as it's not guaranteed by contract
    }
}
