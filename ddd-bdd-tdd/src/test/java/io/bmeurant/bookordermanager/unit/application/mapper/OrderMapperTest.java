package io.bmeurant.bookordermanager.unit.application.mapper;

import io.bmeurant.bookordermanager.application.dto.OrderLineResponse;
import io.bmeurant.bookordermanager.application.dto.OrderResponse;
import io.bmeurant.bookordermanager.application.mapper.OrderMapper;
import io.bmeurant.bookordermanager.order.domain.model.Order;
import io.bmeurant.bookordermanager.order.domain.model.OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderMapperTest {

    private OrderMapper orderMapper;

    @BeforeEach
    void setUp() {
        orderMapper = new OrderMapper();
    }

    @Test
    void mapOrderToResponse_shouldConvertOrderToOrderResponse() {
        // Given
        String orderId = UUID.randomUUID().toString();
        String customerName = "Test Customer";
        String isbn = "978-0321765723";
        int quantity = 2;
        BigDecimal price = new BigDecimal("25.00");

        OrderLine orderLine = new OrderLine(isbn, quantity, price);
        Order order = new Order(customerName, List.of(orderLine));
        org.springframework.test.util.ReflectionTestUtils.setField(order, "orderId", orderId);
        org.springframework.test.util.ReflectionTestUtils.setField(order, "status", Order.OrderStatus.CONFIRMED);

        // When
        OrderResponse orderResponse = orderMapper.mapOrderToResponse(order);

        // Then
        assertNotNull(orderResponse, "OrderResponse should not be null.");
        assertEquals(orderId, orderResponse.orderId(), "Order ID should match.");
        assertEquals(customerName, orderResponse.customerName(), "Customer name should match.");
        assertEquals(Order.OrderStatus.CONFIRMED.name(), orderResponse.status(), "Order status should match.");
        assertNotNull(orderResponse.orderLines(), "Order lines should not be null.");
        assertEquals(1, orderResponse.orderLines().size(), "Should have one order line.");

        OrderLineResponse lineResponse = orderResponse.orderLines().get(0);
        assertEquals(isbn, lineResponse.isbn(), "Order line ISBN should match.");
        assertEquals(quantity, lineResponse.quantity(), "Order line quantity should match.");
        assertEquals(price, lineResponse.price(), "Order line price should match.");
    }

    @Test
    void mapOrderLineToResponse_shouldConvertOrderLineToOrderLineResponse() {
        // Given
        String isbn = "978-0321765723";
        int quantity = 5;
        BigDecimal price = new BigDecimal("49.99");
        OrderLine orderLine = new OrderLine(isbn, quantity, price);

        // When
        OrderLineResponse orderLineResponse = orderMapper.mapOrderLineToResponse(orderLine);

        // Then
        assertNotNull(orderLineResponse, "OrderLineResponse should not be null.");
        assertEquals(isbn, orderLineResponse.isbn(), "ISBN should match.");
        assertEquals(quantity, orderLineResponse.quantity(), "Quantity should match.");
        assertEquals(price, orderLineResponse.price(), "Price should match.");
    }
}
