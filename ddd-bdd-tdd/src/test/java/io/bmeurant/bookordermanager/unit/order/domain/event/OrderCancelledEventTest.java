package io.bmeurant.bookordermanager.unit.order.domain.event;

import io.bmeurant.bookordermanager.order.domain.event.OrderCancelledEvent;
import io.bmeurant.bookordermanager.order.domain.model.Order;
import io.bmeurant.bookordermanager.order.domain.model.OrderLine;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderCancelledEventTest {

    @Test
    void orderCancelledEvent_shouldBeCreatedWithOrder() {
        // Given
        String customerName = "Test Customer";
        List<OrderLine> orderLines = List.of(new OrderLine("isbn1", 1, BigDecimal.TEN));
        Order order = new Order(customerName, orderLines);

        // When
        OrderCancelledEvent event = new OrderCancelledEvent(order);

        // Then
        assertNotNull(event, "Event should not be null");
        assertNotNull(event.getOrder(), "Order in event should not be null");
        assertEquals(order.getOrderId(), event.getOrder().getOrderId(), "Order ID should match");
        assertEquals(order.getCustomerName(), event.getOrder().getCustomerName(), "Customer name should match");
    }
}
