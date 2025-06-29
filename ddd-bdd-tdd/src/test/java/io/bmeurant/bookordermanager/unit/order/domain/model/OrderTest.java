package io.bmeurant.bookordermanager.unit.order.domain.model;

import io.bmeurant.bookordermanager.order.domain.model.Order;
import io.bmeurant.bookordermanager.order.domain.model.OrderLine;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    void shouldCreateOrderWithValidParameters() {
        String customerName = "Alice Wonderland";
        List<OrderLine> orderLines = Arrays.asList(
                new OrderLine("978-0321765723", 2, new BigDecimal("25.00")),
                new OrderLine("978-0132350884", 1, new BigDecimal("35.00"))
        );

        Order order = new Order(customerName, orderLines);

        assertNotNull(order, "Order should not be null.");
        assertNotNull(order.getOrderId(), "Order ID should not be null.");
        assertEquals(customerName, order.getCustomerName(), "Customer name should match the provided value.");
        assertEquals(Order.OrderStatus.PENDING, order.getStatus(), "Initial order status should be PENDING.");
        assertEquals(orderLines.size(), order.getOrderLines().size(), "Number of order lines should match.");
        assertTrue(order.getOrderLines().containsAll(orderLines), "Order should contain all provided order lines.");
    }

    @Test
    void shouldThrowExceptionWhenCustomerNameIsNull() {
        List<OrderLine> orderLines = Collections.singletonList(
                new OrderLine("978-0321765723", 1, new BigDecimal("10.00"))
        );
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Order(null, orderLines);
        }, "Should throw IllegalArgumentException when customer name is null.");
        assertTrue(exception.getMessage().contains("Customer name cannot be null or blank"), "Exception message should indicate null customer name.");
    }

    @Test
    void shouldThrowExceptionWhenCustomerNameIsBlank() {
        List<OrderLine> orderLines = Collections.singletonList(
                new OrderLine("978-0321765723", 1, new BigDecimal("10.00"))
        );
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Order("", orderLines);
        }, "Should throw IllegalArgumentException when customer name is blank.");
        assertTrue(exception.getMessage().contains("Customer name cannot be null or blank"), "Exception message should indicate blank customer name.");
    }

    @Test
    void shouldThrowExceptionWhenOrderLinesIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Order("Alice", null);
        }, "Should throw IllegalArgumentException when order lines are null.");
        assertTrue(exception.getMessage().contains("Order lines cannot be null or empty"), "Exception message should indicate null order lines.");
    }

    @Test
    void shouldThrowExceptionWhenOrderLinesIsEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Order("Alice", Collections.emptyList());
        }, "Should throw IllegalArgumentException when order lines are empty.");
        assertTrue(exception.getMessage().contains("Order lines cannot be null or empty"), "Exception message should indicate empty order lines.");
    }

    @Test
    void shouldBeEqualWhenOrderIdIsSame() {
        List<OrderLine> orderLines = Collections.singletonList(
                new OrderLine("978-0321765723", 1, new BigDecimal("10.00"))
        );
        Order order1 = new Order("Alice", orderLines);
        // Create a second order with the same orderId (simulating retrieval from persistence)
        Order order2 = new Order("Bob", Collections.singletonList(new OrderLine("dummy-isbn", 1, new BigDecimal("1.00")))); // Use a valid, non-empty list
        // Manually set orderId for testing equality based on ID
        try {
            java.lang.reflect.Field orderIdField = Order.class.getDeclaredField("orderId");
            orderIdField.setAccessible(true);
            orderIdField.set(order2, order1.getOrderId());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set orderId for testing equality: " + e.getMessage());
        }

        assertEquals(order1, order2, "Orders with the same Order ID should be equal.");
        assertEquals(order1.hashCode(), order2.hashCode(), "Hash codes should be equal for orders with the same Order ID.");
    }

    @Test
    void shouldNotBeEqualWhenOrderIdIsDifferent() {
        List<OrderLine> orderLines = Collections.singletonList(
                new OrderLine("978-0321765723", 1, new BigDecimal("10.00"))
        );
        Order order1 = new Order("Alice", orderLines);
        Order order2 = new Order("Bob", orderLines);

        assertNotEquals(order1, order2, "Orders with different Order IDs should not be equal.");
        assertNotEquals(order1.hashCode(), order2.hashCode(), "Hash codes should not be equal for orders with different Order IDs.");
    }
}
