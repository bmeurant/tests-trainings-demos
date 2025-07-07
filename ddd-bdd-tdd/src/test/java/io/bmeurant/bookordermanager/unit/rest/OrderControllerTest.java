package io.bmeurant.bookordermanager.unit.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bmeurant.bookordermanager.application.dto.CreateOrderRequest;
import io.bmeurant.bookordermanager.application.dto.OrderItemRequest;
import io.bmeurant.bookordermanager.application.dto.OrderResponse;
import io.bmeurant.bookordermanager.application.service.OrderService;
import io.bmeurant.bookordermanager.domain.exception.ValidationException;
import io.bmeurant.bookordermanager.interfaces.rest.OrderController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    void createOrder_whenValidRequest_shouldReturn201Created() throws Exception {
        // Given
        OrderItemRequest itemRequest = new OrderItemRequest("1234567890", 1);
        CreateOrderRequest createOrderRequest = new CreateOrderRequest("Customer Name", Collections.singletonList(itemRequest));

        OrderResponse orderResponse = new OrderResponse(UUID.randomUUID().toString(), "Customer Name", "PENDING", Collections.emptyList());

        when(orderService.createOrder(any(CreateOrderRequest.class))).thenReturn(orderResponse);

        // When & Then
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.orderId").value(orderResponse.orderId()))
                .andExpect(jsonPath("$.customerName").value("Customer Name"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void createOrder_whenInvalidRequest_shouldReturn400BadRequest() throws Exception {
        // Given
        // Customer name is blank, which should trigger validation failure
        CreateOrderRequest invalidRequest = new CreateOrderRequest("", Collections.singletonList(new OrderItemRequest("1234567890", 1)));

        when(orderService.createOrder(any(CreateOrderRequest.class)))
                .thenThrow(new ValidationException("Customer name cannot be blank", CreateOrderRequest.class));

        // When & Then
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
