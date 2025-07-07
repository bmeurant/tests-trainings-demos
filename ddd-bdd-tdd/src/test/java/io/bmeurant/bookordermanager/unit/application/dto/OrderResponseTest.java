package io.bmeurant.bookordermanager.unit.application.dto;

import io.bmeurant.bookordermanager.application.dto.OrderLineResponse;
import io.bmeurant.bookordermanager.application.dto.OrderResponse;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderResponseTest {

    @Test
    void testRecord() {
        // Given
        String orderId = "123";
        String customerName = "John Doe";
        String status = "PENDING";
        OrderLineResponse line = new OrderLineResponse("1234567890", 1, new java.math.BigDecimal("10.00"));
        OrderResponse response = new OrderResponse(orderId, customerName, status, Collections.singletonList(line));

        // When & Then
        assertEquals(orderId, response.orderId());
        assertEquals(customerName, response.customerName());
        assertEquals(status, response.status());
        assertEquals(1, response.orderLines().size());
        assertEquals(line, response.orderLines().get(0));
    }
}
