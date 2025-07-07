package io.bmeurant.bookordermanager.application.service;

import io.bmeurant.bookordermanager.application.dto.CreateOrderRequest;
import io.bmeurant.bookordermanager.application.dto.OrderResponse;

import java.util.Optional;

/**
 * Service interface for managing orders.
 */
public interface OrderService {

    /**
     * Creates a new order based on the provided request.
     *
     * @param createOrderRequest The request containing customer name and order items.
     * @return The created OrderResponse object.
     */
    OrderResponse createOrder(CreateOrderRequest createOrderRequest);

    /**
     * Finds an order by its unique identifier and returns it as an OrderResponse.
     *
     * @param orderId The unique identifier of the order.
     * @return An Optional containing the OrderResponse if found, otherwise empty.
     */
    Optional<OrderResponse> getOrderById(String orderId);


    /**
     * Confirms an existing order, transitioning its status to CONFIRMED.
     *
     * @param orderId The ID of the order to confirm.
     * @return The confirmed Order object.
     * @throws io.bmeurant.bookordermanager.order.domain.exception.OrderNotFoundException             if the order is not found.
     * @throws io.bmeurant.bookordermanager.domain.exception.ValidationException                      if the order cannot be confirmed (e.g., wrong status) or if stock deduction quantity is invalid.
     * @throws io.bmeurant.bookordermanager.inventory.domain.exception.InventoryItemNotFoundException if an inventory item for an order line is not found.
     * @throws io.bmeurant.bookordermanager.inventory.domain.exception.InsufficientStockException     if stock is insufficient for an order line.
     */
    OrderResponse confirmOrder(String orderId);

    /**
     * Cancels an existing order, transitioning its status to CANCELLED and releasing stock if necessary.
     *
     * @param orderId The ID of the order to cancel.
     * @return The cancelled Order object.
     * @throws io.bmeurant.bookordermanager.order.domain.exception.OrderNotFoundException if the order is not found.
     * @throws io.bmeurant.bookordermanager.domain.exception.ValidationException          if the order cannot be cancelled (e.g., wrong status).
     */
    OrderResponse cancelOrder(String orderId);
}
