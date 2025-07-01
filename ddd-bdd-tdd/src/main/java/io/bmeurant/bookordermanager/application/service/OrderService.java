package io.bmeurant.bookordermanager.application.service;

import io.bmeurant.bookordermanager.application.dto.OrderItemRequest;
import io.bmeurant.bookordermanager.order.domain.model.Order;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing orders.
 */
public interface OrderService {

    /**
     * Creates a new order for a given customer with the specified items.
     *
     * @param customerName The name of the customer.
     * @param items        The list of items to include in the order.
     * @return The created Order object.
     */
    Order createOrder(String customerName, List<OrderItemRequest> items);

    /**
     * Finds an order by its unique identifier.
     *
     * @param id The unique identifier of the order.
     * @return An Optional containing the Order if found, otherwise empty.
     */
    Optional<Order> findOrderById(String id);

    /**
     * Confirms an existing order, transitioning its status to CONFIRMED.
     *
     * @param orderId The ID of the order to confirm.
     * @return The confirmed Order object.
     * @throws io.bmeurant.bookordermanager.order.domain.exception.OrderNotFoundException if the order is not found.
     * @throws io.bmeurant.bookordermanager.domain.exception.ValidationException if the order cannot be confirmed (e.g., wrong status) or if stock deduction quantity is invalid.
     * @throws io.bmeurant.bookordermanager.inventory.domain.exception.InventoryItemNotFoundException if an inventory item for an order line is not found.
     * @throws io.bmeurant.bookordermanager.inventory.domain.exception.InsufficientStockException if stock is insufficient for an order line.
     */
    Order confirmOrder(String orderId);

    /**
     * Cancels an existing order, transitioning its status to CANCELLED and releasing stock if necessary.
     *
     * @param orderId The ID of the order to cancel.
     * @return The cancelled Order object.
     * @throws io.bmeurant.bookordermanager.order.domain.exception.OrderNotFoundException if the order is not found.
     * @throws io.bmeurant.bookordermanager.domain.exception.ValidationException if the order cannot be cancelled (e.g., wrong status).
     */
    Order cancelOrder(String orderId);
}
