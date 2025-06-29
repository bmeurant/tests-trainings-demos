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

        assertNotNull(order);
        assertNotNull(order.getOrderId());
        assertEquals(customerName, order.getCustomerName());
        assertEquals(Order.OrderStatus.PENDING, order.getStatus());
        assertEquals(orderLines.size(), order.getOrderLines().size());
        assertTrue(order.getOrderLines().containsAll(orderLines));
    }

    @Test
    void shouldThrowExceptionWhenCustomerNameIsNull() {
        List<OrderLine> orderLines = Collections.singletonList(
                new OrderLine("978-0321765723", 1, new BigDecimal("10.00"))
        );
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Order(null, orderLines);
        });
        assertTrue(exception.getMessage().contains("Customer name cannot be null or blank"));
    }

    @Test
    void shouldThrowExceptionWhenCustomerNameIsBlank() {
        List<OrderLine> orderLines = Collections.singletonList(
                new OrderLine("978-0321765723", 1, new BigDecimal("10.00"))
        );
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Order("", orderLines);
        });
        assertTrue(exception.getMessage().contains("Customer name cannot be null or blank"));
    }

    @Test
    void shouldThrowExceptionWhenOrderLinesIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Order("Alice", null);
        });
        assertTrue(exception.getMessage().contains("Order lines cannot be null or empty"));
    }

    @Test
    void shouldThrowExceptionWhenOrderLinesIsEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Order("Alice", Collections.emptyList());
        });
        assertTrue(exception.getMessage().contains("Order lines cannot be null or empty"));
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

        assertEquals(order1, order2);
        assertEquals(order1.hashCode(), order2.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenOrderIdIsDifferent() {
        List<OrderLine> orderLines = Collections.singletonList(
                new OrderLine("978-0321765723", 1, new BigDecimal("10.00"))
        );
        Order order1 = new Order("Alice", orderLines);
        Order order2 = new Order("Bob", orderLines);

        assertNotEquals(order1, order2);
        assertNotEquals(order1.hashCode(), order2.hashCode());
    }
}
