package io.bmeurant.bookordermanager.unit.order.domain.model;

import io.bmeurant.bookordermanager.domain.exception.ValidationException;
import io.bmeurant.bookordermanager.order.domain.model.Order;
import io.bmeurant.bookordermanager.order.domain.model.OrderLine;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

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
        // Since OrderLine is now an @Embeddable, its equality is based on its content, not identity.
        // No need to check OrderLine IDs here.
    }

    @Test
    void shouldThrowExceptionWhenCustomerNameIsNull() {
        List<OrderLine> orderLines = Collections.singletonList(
                new OrderLine("978-0321765723", 1, new BigDecimal("10.00"))
        );
        ValidationException exception = assertThrows(ValidationException.class, () -> new Order(null, orderLines), "Should throw ValidationException when customer name is null.");
        assertTrue(exception.getMessage().contains("Customer name cannot be null or blank"), "Exception message should indicate null customer name.");
        assertEquals(Order.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be Order.");
    }

    @Test
    void shouldThrowExceptionWhenCustomerNameIsBlank() {
        List<OrderLine> orderLines = Collections.singletonList(
                new OrderLine("978-0321765723", 1, new BigDecimal("10.00"))
        );
        ValidationException exception = assertThrows(ValidationException.class, () -> new Order("", orderLines), "Should throw ValidationException when customer name is blank.");
        assertTrue(exception.getMessage().contains("Customer name cannot be null or blank"), "Exception message should indicate blank customer name.");
        assertEquals(Order.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be Order.");
    }

    @Test
    void shouldThrowExceptionWhenOrderLinesIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () -> new Order("Alice", null), "Should throw ValidationException when order lines are null.");
        assertTrue(exception.getMessage().contains("Order lines cannot be null or empty"), "Exception message should indicate null order lines.");
        assertEquals(Order.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be Order.");
    }

    @Test
    void shouldThrowExceptionWhenOrderLinesIsEmpty() {
        ValidationException exception = assertThrows(ValidationException.class, () -> new Order("Alice", Collections.emptyList()), "Should throw ValidationException when order lines are empty.");
        assertTrue(exception.getMessage().contains("Order lines cannot be null or empty"), "Exception message should indicate empty order lines.");
        assertEquals(Order.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be Order.");
    }

    @Test
    void shouldBeEqualWhenOrderIdIsSame() {
        List<OrderLine> orderLines = List.of(
                new OrderLine("978-0321765723", 1, new BigDecimal("10.00"))
        );
        Order order1 = new Order("Alice", orderLines);
        // Create a second order with the same orderId (simulating retrieval from persistence)
        Order order2 = new Order("Bob", List.of(new OrderLine("dummy-isbn", 1, new BigDecimal("1.00")))); // Use a valid, non-empty list
        // Manually set orderId for testing equality based on ID
        try {
            java.lang.reflect.Field orderIdField = Order.class.getDeclaredField("orderId");
            orderIdField.setAccessible(true);
            orderIdField.set(order2, order1.getOrderId());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set orderId for testing equality: " + e.getMessage());
        }

        assertEquals(order1, order2, "Orders with the same Order ID should be equal.");
        assertEquals(order1.hashCode(), order2.hashCode(), "Hash codes should be equal for items with the same Order ID.");
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

    @Test
    void shouldConfirmOrderSuccessfully() {
        List<OrderLine> orderLines = Collections.singletonList(
                new OrderLine("978-0321765723", 1, new BigDecimal("10.00"))
        );
        Order order = new Order("Alice", orderLines);
        order.confirm();
        assertEquals(Order.OrderStatus.CONFIRMED, order.getStatus(), "Order status should be CONFIRMED after confirmation.");
    }

    @Test
    void shouldThrowExceptionWhenConfirmingNonPendingOrder() {
        List<OrderLine> orderLines = Collections.singletonList(
                new OrderLine("978-0321765723", 1, new BigDecimal("10.00"))
        );
        Order order = new Order("Alice", orderLines);
        // Manually set status to something other than PENDING for testing
        try {
            java.lang.reflect.Field statusField = Order.class.getDeclaredField("status");
            statusField.setAccessible(true);
            statusField.set(order, Order.OrderStatus.CONFIRMED);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set order status for testing: " + e.getMessage());
        }

        ValidationException exception = assertThrows(ValidationException.class, order::confirm, "Should throw ValidationException when confirming a non-PENDING order.");
        assertTrue(exception.getMessage().contains("Order can only be confirmed if its status is PENDING."), "Exception message should indicate invalid status for confirmation.");
        assertEquals(Order.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be Order.");
    }

    @Test
    void cancel_shouldChangeStatusToCancelledAndReturnItemsForConfirmedOrder() {
        // Given
        List<OrderLine> orderLines = Collections.singletonList(
                new OrderLine("978-0321765723", 2, new BigDecimal("25.00"))
        );
        Order order = new Order("Alice", orderLines);
        order.confirm(); // Confirm the order so stock would have been deducted

        // When
        List<OrderLine> itemsToRelease = order.cancel();

        // Then
        assertEquals(Order.OrderStatus.CANCELLED, order.getStatus(), "Order status should be CANCELLED.");
        assertFalse(itemsToRelease.isEmpty(), "Should return items to release for a confirmed order.");
        assertEquals(orderLines.size(), itemsToRelease.size(), "Number of items to release should match order lines.");
        assertTrue(itemsToRelease.containsAll(orderLines), "Items to release should contain all order lines.");
    }

    @Test
    void cancel_shouldChangeStatusToCancelledAndReturnEmptyListForPendingOrder() {
        // Given
        List<OrderLine> orderLines = Collections.singletonList(
                new OrderLine("978-0321765723", 2, new BigDecimal("25.00"))
        );
        Order order = new Order("Alice", orderLines);
        // Order is PENDING by default

        // When
        List<OrderLine> itemsToRelease = order.cancel();

        // Then
        assertEquals(Order.OrderStatus.CANCELLED, order.getStatus(), "Order status should be CANCELLED.");
        assertTrue(itemsToRelease.isEmpty(), "Should return an empty list for a pending order.");
    }

    @Test
    void cancel_shouldThrowValidationExceptionWhenOrderIsAlreadyCancelled() {
        // Given
        List<OrderLine> orderLines = Collections.singletonList(
                new OrderLine("978-0321765723", 2, new BigDecimal("25.00"))
        );
        Order order = new Order("Alice", orderLines);
        order.cancel(); // Cancel the order first

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class, order::cancel, "Should throw ValidationException when cancelling an already cancelled order.");
        assertTrue(exception.getMessage().contains("Order can only be cancelled if its status is PENDING or CONFIRMED."), "Exception message should indicate invalid status for cancellation.");
        assertEquals(Order.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be Order.");
    }

    @Test
    void cancel_shouldThrowValidationExceptionWhenOrderIsDelivered() {
        // Given
        List<OrderLine> orderLines = Collections.singletonList(
                new OrderLine("978-0321765723", 2, new BigDecimal("25.00"))
        );
        Order order = new Order("Alice", orderLines);
        // Manually set status to DELIVERED for testing purposes
        try {
            java.lang.reflect.Field statusField = Order.class.getDeclaredField("status");
            statusField.setAccessible(true);
            statusField.set(order, Order.OrderStatus.DELIVERED);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set order status for testing: " + e.getMessage());
        }

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class, order::cancel, "Should throw ValidationException when cancelling a delivered order.");
        assertTrue(exception.getMessage().contains("Order can only be cancelled if its status is PENDING or CONFIRMED."), "Exception message should indicate invalid status for cancellation.");
        assertEquals(Order.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be Order.");
    }
}
