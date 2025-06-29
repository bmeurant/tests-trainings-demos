package io.bmeurant.bookordermanager.application.service.impl;

import io.bmeurant.bookordermanager.application.dto.OrderItemRequest;
import io.bmeurant.bookordermanager.application.service.OrderService;
import io.bmeurant.bookordermanager.catalog.domain.model.Book;
import io.bmeurant.bookordermanager.catalog.domain.repository.BookRepository;
import io.bmeurant.bookordermanager.inventory.domain.model.InventoryItem;
import io.bmeurant.bookordermanager.inventory.domain.service.InventoryService;
import io.bmeurant.bookordermanager.order.domain.model.Order;
import io.bmeurant.bookordermanager.order.domain.model.OrderLine;
import io.bmeurant.bookordermanager.order.domain.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final BookRepository bookRepository;
    private final InventoryService inventoryService;
    private final OrderRepository orderRepository;
    @Autowired
    public OrderServiceImpl(BookRepository bookRepository, InventoryService inventoryService, OrderRepository orderRepository) {
        this.bookRepository = bookRepository;
        this.inventoryService = inventoryService;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public Order createOrder(String customerName, List<OrderItemRequest> items) {
        log.debug("Creating order for customer: {} with {} items.", customerName, items.size());
        Order order = new Order(customerName, buildOrderLines(items));
        Order savedOrder = orderRepository.save(order);
        log.info("Order created and saved: {}", savedOrder);
        return savedOrder;
    }

    private List<OrderLine> buildOrderLines(List<OrderItemRequest> items) {
        List<OrderLine> orderLines = new ArrayList<>();
        for (OrderItemRequest itemRequest : items) {
            orderLines.add(createOrderLine(itemRequest));
        }
        return orderLines;
    }

    private OrderLine createOrderLine(OrderItemRequest itemRequest) {
        log.debug("Processing item request: {}", itemRequest);
        Book book = findBook(itemRequest.getIsbn());
        inventoryService.deductStock(itemRequest.getIsbn(), itemRequest.getQuantity());

        return new OrderLine(itemRequest.getIsbn(), itemRequest.getQuantity(), book.getPrice());
    }

    private Book findBook(String isbn) {
        return bookRepository.findById(isbn)
                .orElseThrow(() -> {
                    log.warn("Book with ISBN {} not found in catalog.", isbn);
                    return new IllegalArgumentException("Book with ISBN " + isbn + " not found in catalog.");
                });
    }
}

