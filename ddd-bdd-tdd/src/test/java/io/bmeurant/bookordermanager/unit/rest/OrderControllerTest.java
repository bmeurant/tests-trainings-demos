package io.bmeurant.bookordermanager.unit.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bmeurant.bookordermanager.application.dto.CreateOrderRequest;
import io.bmeurant.bookordermanager.application.dto.OrderItemRequest;
import io.bmeurant.bookordermanager.application.dto.OrderResponse;
import io.bmeurant.bookordermanager.application.service.OrderService;
import io.bmeurant.bookordermanager.catalog.domain.exception.BookNotFoundException;
import io.bmeurant.bookordermanager.domain.exception.ValidationException;
import io.bmeurant.bookordermanager.interfaces.rest.OrderController;
import io.bmeurant.bookordermanager.inventory.domain.exception.InsufficientStockException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
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

    @Test
    void createOrder_whenBookNotFound_shouldReturn404NotFound() throws Exception {
        // Given
        OrderItemRequest itemRequest = new OrderItemRequest("nonExistentIsbn", 1);
        CreateOrderRequest createOrderRequest = new CreateOrderRequest("Customer Name", Collections.singletonList(itemRequest));

        when(orderService.createOrder(any(CreateOrderRequest.class)))
                .thenThrow(new BookNotFoundException("nonExistentIsbn"));

        // When & Then
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void createOrder_whenInsufficientStock_shouldReturn409Conflict() throws Exception {
        // Given
        OrderItemRequest itemRequest = new OrderItemRequest("978-0134786275", 5); // Request 5, but assume only 2 in stock
        CreateOrderRequest createOrderRequest = new CreateOrderRequest("Customer Name", Collections.singletonList(itemRequest));

        when(orderService.createOrder(any(CreateOrderRequest.class)))
                .thenThrow(new InsufficientStockException("978-0134786275", 5, 2));

        // When & Then
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    void getOrderById_whenOrderExists_shouldReturn200Ok() throws Exception {
        // Given
        String orderId = UUID.randomUUID().toString();
        OrderResponse orderResponse = new OrderResponse(orderId, "Test Customer", "PENDING", Collections.emptyList());

        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(orderResponse));

        // When & Then
        mockMvc.perform(get("/api/orders/{orderId}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(orderId))
                .andExpect(jsonPath("$.customerName").value("Test Customer"));
    }

    @Test
    void getOrderById_whenOrderDoesNotExist_shouldReturn404NotFound() throws Exception {
        // Given
        String orderId = UUID.randomUUID().toString();
        when(orderService.getOrderById(orderId)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/orders/{orderId}", orderId))
                .andExpect(status().isNotFound());
    }
}