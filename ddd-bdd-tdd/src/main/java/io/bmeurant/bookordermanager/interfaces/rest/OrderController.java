package io.bmeurant.bookordermanager.interfaces.rest;

import io.bmeurant.bookordermanager.application.dto.CreateOrderRequest;
import io.bmeurant.bookordermanager.application.dto.OrderResponse;
import io.bmeurant.bookordermanager.application.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

/**
 * REST controller for managing book orders.
 * Provides API endpoints for creating and retrieving orders.
 */
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Operations pertaining to orders in Book Order Manager")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Creates a new order based on the provided request.
     * This endpoint handles the creation of a customer order, including validation
     * and interaction with the order service.
     *
     * @param createOrderRequest The request body containing details for the new order.
     * @return A {@link ResponseEntity} with the created {@link OrderResponse} and HTTP status 201 Created.
     */
    @PostMapping
    @Operation(summary = "Create a new order", description = "Creates a new customer order from a list of books and quantities.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided", content = @Content),
            @ApiResponse(responseCode = "404", description = "One or more books not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Insufficient stock for one or more books", content = @Content)
    })
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        OrderResponse orderResponse = orderService.createOrder(createOrderRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(orderResponse.orderId())
                .toUri();
        return ResponseEntity.created(location).body(orderResponse);
    }

    /**
     * Retrieves the details of a specific order by its unique identifier.
     *
     * @param orderId The unique identifier of the order to retrieve.
     * @return A {@link ResponseEntity} with the {@link OrderResponse} if found (HTTP status 200 OK),
     *         or HTTP status 404 Not Found if the order does not exist.
     */
    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by ID", description = "Retrieves the details of a specific order by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found and returned",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String orderId) {
        Optional<OrderResponse> orderResponse = orderService.getOrderById(orderId);
        return orderResponse.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}