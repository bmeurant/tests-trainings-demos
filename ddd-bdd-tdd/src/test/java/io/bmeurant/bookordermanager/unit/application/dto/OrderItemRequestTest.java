package io.bmeurant.bookordermanager.unit.application.dto;

import io.bmeurant.bookordermanager.application.dto.OrderItemRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderItemRequestTest {

    @Test
    void testRecord() {
        // Given
        String isbn = "1234567890";
        Integer quantity = 1;
        OrderItemRequest request = new OrderItemRequest(isbn, quantity);

        // When & Then
        assertEquals(isbn, request.isbn());
        assertEquals(quantity, request.quantity());
    }
}
