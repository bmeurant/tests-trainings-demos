package io.bmeurant.bookordermanager.unit.application.dto;

import io.bmeurant.bookordermanager.application.dto.CreateOrderRequest;
import io.bmeurant.bookordermanager.application.dto.OrderItemRequest;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateOrderRequestTest {

    @Test
    void testRecord() {
        // Given
        String customerName = "John Doe";
        OrderItemRequest item = new OrderItemRequest("1234567890", 1);
        CreateOrderRequest request = new CreateOrderRequest(customerName, Collections.singletonList(item));

        // When & Then
        assertEquals(customerName, request.customerName());
        assertEquals(1, request.items().size());
        assertEquals(item, request.items().get(0));
    }
}
