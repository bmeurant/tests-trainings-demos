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
}
