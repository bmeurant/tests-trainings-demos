package io.bmeurant.bookordermanager.application.service.impl;

import io.bmeurant.bookordermanager.application.dto.OrderItemRequest;
import io.bmeurant.bookordermanager.application.service.OrderService;
import io.bmeurant.bookordermanager.catalog.domain.model.Book;
import io.bmeurant.bookordermanager.catalog.domain.service.BookService;
import io.bmeurant.bookordermanager.inventory.domain.service.InventoryService;
import io.bmeurant.bookordermanager.order.domain.event.OrderCreatedEvent;
import io.bmeurant.bookordermanager.order.domain.exception.OrderNotFoundException;
import io.bmeurant.bookordermanager.order.domain.model.Order;
import io.bmeurant.bookordermanager.order.domain.model.OrderLine;
import io.bmeurant.bookordermanager.order.domain.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link OrderService} interface.
 * Handles the creation and management of orders, interacting with catalog and inventory services.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final BookService bookService;
    private final InventoryService inventoryService;
    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Constructs an {@code OrderServiceImpl} with the necessary dependencies.
     *
     * @param bookService The service for retrieving book information.
     * @param inventoryService The service for managing inventory stock.
     * @param orderRepository The repository for persisting and retrieving orders.
     * @param applicationEventPublisher The publisher for application events.
     */
    @Autowired
    public OrderServiceImpl(BookService bookService, InventoryService inventoryService, OrderRepository orderRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.bookService = bookService;
        this.inventoryService = inventoryService;
        this.orderRepository = orderRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Creates a new order for a given customer with the specified items.
     * This method is transactional and publishes an {@link OrderCreatedEvent} upon successful order creation.
     *
     * @param customerName The name of the customer.
     * @param items The list of items to include in the order.
     * @return The created and saved Order object.
     */
    @Override
    @Transactional
    public Order createOrder(String customerName, List<OrderItemRequest> items) {
        log.debug("Creating order for customer: {} with {} items.", customerName, items.size());

        // Check stock for each item before creating the order
        for (OrderItemRequest itemRequest : items) {
            inventoryService.checkStock(itemRequest.getIsbn(), itemRequest.getQuantity());
        }

        Order order = new Order(customerName, buildOrderLines(items));
        Order savedOrder = orderRepository.save(order);
        log.info("Order created and saved: {}", savedOrder);
        applicationEventPublisher.publishEvent(new OrderCreatedEvent(savedOrder));
        return savedOrder;
    }

    /**
     * Finds an order by its unique identifier.
     *
     * @param id The unique identifier of the order.
     * @return An Optional containing the Order if found, otherwise empty.
     */
    @Override
    public Optional<Order> findOrderById(String id) {
        return orderRepository.findById(id);
    }

    /**
     * Confirms an existing order, transitioning its status to CONFIRMED.
     * This method performs the final stock deduction for each order line.
     *
     * @param orderId The ID of the order to confirm.
     * @return The confirmed Order object.
     * @throws OrderNotFoundException if the order is not found.
     * @throws io.bmeurant.bookordermanager.domain.exception.ValidationException if the order cannot be confirmed (e.g., wrong status).
     */
    @Override
    @Transactional
    public Order confirmOrder(String orderId) {
        log.debug("Attempting to confirm order with ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        // Perform final stock deduction for each order line
        for (OrderLine orderLine : order.getOrderLines()) {
            inventoryService.deductStock(orderLine.getIsbn(), orderLine.getQuantity());
        }

        order.confirm();
        Order confirmedOrder = orderRepository.save(order);
        log.info("Order {} successfully confirmed.", orderId);
        return confirmedOrder;
    }

    /**
     * Builds a list of {@link OrderLine} objects from a list of {@link OrderItemRequest}s.
     *
     * @param items The list of order item requests.
     * @return A list of OrderLine objects.
     */
    private List<OrderLine> buildOrderLines(List<OrderItemRequest> items) {
        List<OrderLine> orderLines = new ArrayList<>();
        for (OrderItemRequest itemRequest : items) {
            orderLines.add(createOrderLine(itemRequest));
        }
        return orderLines;
    }

    /**
     * Creates a single {@link OrderLine} from an {@link OrderItemRequest}.
     * This involves looking up book details.
     *
     * @param itemRequest The order item request.
     * @return The created OrderLine object.
     * @throws io.bmeurant.bookordermanager.catalog.domain.exception.BookNotFoundException if the book for the item request is not found.
     */
    private OrderLine createOrderLine(OrderItemRequest itemRequest) {
        log.debug("Processing item request: {}", itemRequest);
        Book book = bookService.findBookByIsbn(itemRequest.getIsbn());

        return new OrderLine(itemRequest.getIsbn(), itemRequest.getQuantity(), book.getPrice());
    }
}