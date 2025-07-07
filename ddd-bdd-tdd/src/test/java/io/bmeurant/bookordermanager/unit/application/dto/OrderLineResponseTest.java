package io.bmeurant.bookordermanager.unit.application.dto;

import io.bmeurant.bookordermanager.application.dto.OrderLineResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderLineResponseTest {

    @Test
    void testRecord() {
        // Given
        String isbn = "1234567890";
        Integer quantity = 1;
        BigDecimal price = new BigDecimal("10.00");
        OrderLineResponse response = new OrderLineResponse(isbn, quantity, price);

        // When & Then
        assertEquals(isbn, response.isbn());
        assertEquals(quantity, response.quantity());
        assertEquals(price, response.price());
    }
}
