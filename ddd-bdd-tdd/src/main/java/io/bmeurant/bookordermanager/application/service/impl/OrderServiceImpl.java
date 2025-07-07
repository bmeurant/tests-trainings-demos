package io.bmeurant.bookordermanager.application.service.impl;

import io.bmeurant.bookordermanager.application.dto.CreateOrderRequest;
import io.bmeurant.bookordermanager.application.dto.OrderItemRequest;
import io.bmeurant.bookordermanager.application.dto.OrderResponse;
import io.bmeurant.bookordermanager.application.mapper.OrderMapper;
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
    private final OrderMapper orderMapper;

    /**
     * Constructs an {@code OrderServiceImpl} with the necessary dependencies.
     *
     * @param bookService               The service for retrieving book information.
     * @param inventoryService          The service for managing inventory stock.
     * @param orderRepository           The repository for persisting and retrieving orders.
     * @param applicationEventPublisher The publisher for application events.
     * @param orderMapper               The mapper for converting Order domain objects to DTOs.
     */
    @Autowired
    public OrderServiceImpl(BookService bookService, InventoryService inventoryService, OrderRepository orderRepository, ApplicationEventPublisher applicationEventPublisher, OrderMapper orderMapper) {
        this.bookService = bookService;
        this.inventoryService = inventoryService;
        this.orderRepository = orderRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        log.debug("Creating order for customer: {} with {} items.", createOrderRequest.customerName(), createOrderRequest.items().size());

        // Check stock for each item before creating the order
        for (OrderItemRequest itemRequest : createOrderRequest.items()) {
            inventoryService.checkStock(itemRequest.isbn(), itemRequest.quantity());
        }

        Order order = new Order(createOrderRequest.customerName(), buildOrderLines(createOrderRequest.items()));
        Order savedOrder = orderRepository.save(order);
        log.info("Order created and saved: {}", savedOrder);
        applicationEventPublisher.publishEvent(new OrderCreatedEvent(savedOrder));
        return orderMapper.mapOrderToResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderResponse> getOrderById(String orderId) {
        log.debug("Finding order by ID: {}", orderId);
        return orderRepository.findById(orderId)
                .map(orderMapper::mapOrderToResponse);
    }

    @Override
    @Transactional
    public OrderResponse confirmOrder(String orderId) {
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
        return orderMapper.mapOrderToResponse(confirmedOrder);
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(String orderId) {
        log.debug("Attempting to cancel order with ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        releaseStocks(order);

        Order cancelledOrder = orderRepository.save(order);
        log.info("Order {} successfully cancelled.", orderId);
        applicationEventPublisher.publishEvent(new io.bmeurant.bookordermanager.order.domain.event.OrderCancelledEvent(cancelledOrder));
        return orderMapper.mapOrderToResponse(cancelledOrder);
    }

    /**
     * Releases stock for items in a cancelled order.
     *
     * @param order The order for which to release stock.
     */
    private void releaseStocks(Order order) {
        List<OrderLine> itemsToRelease = order.cancel();

        if (!itemsToRelease.isEmpty()) {
            for (OrderLine orderLine : itemsToRelease) {
                inventoryService.releaseStock(orderLine.getIsbn(), orderLine.getQuantity());
            }
        }
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
        Book book = bookService.findBookByIsbn(itemRequest.isbn());

        return new OrderLine(itemRequest.isbn(), itemRequest.quantity(), book.getPrice());
    }
}