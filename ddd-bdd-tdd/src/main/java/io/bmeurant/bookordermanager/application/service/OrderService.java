package io.bmeurant.bookordermanager.application.service;

import io.bmeurant.bookordermanager.application.dto.CreateOrderRequest;
import io.bmeurant.bookordermanager.application.dto.OrderResponse;
import io.bmeurant.bookordermanager.catalog.domain.exception.BookNotFoundException;
import io.bmeurant.bookordermanager.domain.exception.ValidationException;
import io.bmeurant.bookordermanager.inventory.domain.exception.InsufficientStockException;
import io.bmeurant.bookordermanager.inventory.domain.exception.InventoryItemNotFoundException;
import io.bmeurant.bookordermanager.order.domain.exception.OrderNotFoundException;

import java.util.List;
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
     * @throws BookNotFoundException if a book specified in the order items is not found in the catalog.
     * @throws InventoryItemNotFoundException if an inventory item for an order line is not found.
     * @throws InsufficientStockException if stock is insufficient for one or more books.
     * @throws ValidationException if the order request is invalid (e.g., empty items, invalid quantities).
     */
    OrderResponse createOrder(CreateOrderRequest createOrderRequest);

    /**
     * Finds an order by its unique identifier and returns it as an OrderResponse.
     *
     * @param orderId The unique identifier of the order.
     * @return The OrderResponse if found.
     * @throws OrderNotFoundException if the order with the given ID is not found.
     */
    OrderResponse getOrderById(String orderId);


    /**
     * Confirms an existing order, transitioning its status to CONFIRMED.
     *
     * @param orderId The ID of the order to confirm.
     * @return The confirmed OrderResponse object.
     * @throws OrderNotFoundException if the order is not found.
     * @throws ValidationException if the order cannot be confirmed (e.g., wrong status) or if stock deduction quantity is invalid.
     * @throws InventoryItemNotFoundException if an inventory item for an order line is not found.
     * @throws InsufficientStockException if stock is insufficient for an order line.
     */
    OrderResponse confirmOrder(String orderId);

    /**
     * Cancels an existing order, transitioning its status to CANCELLED and releasing stock if necessary.
     *
     * @param orderId The ID of the order to cancel.
     * @return The cancelled OrderResponse object.
     * @throws OrderNotFoundException if the order is not found.
     * @throws ValidationException if the order cannot be cancelled (e.g., wrong status).
     * @throws InventoryItemNotFoundException if an inventory item for an order line is not found during stock release.
     */
    OrderResponse cancelOrder(String orderId);

    /**
     * Retrieves all orders.
     *
     * @return A list of all orders as OrderResponse objects.
     */
    List<OrderResponse> findAllOrders();
}