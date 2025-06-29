package io.bmeurant.bookordermanager.application.service.impl;

import io.bmeurant.bookordermanager.application.dto.OrderItemRequest;
import io.bmeurant.bookordermanager.application.service.OrderService;
import io.bmeurant.bookordermanager.catalog.domain.model.Book;
import io.bmeurant.bookordermanager.catalog.domain.repository.BookRepository;
import io.bmeurant.bookordermanager.inventory.domain.model.InventoryItem;
import io.bmeurant.bookordermanager.inventory.domain.repository.InventoryItemRepository;
import io.bmeurant.bookordermanager.order.domain.model.Order;
import io.bmeurant.bookordermanager.order.domain.model.OrderLine;
import io.bmeurant.bookordermanager.order.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final BookRepository bookRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(BookRepository bookRepository, InventoryItemRepository inventoryItemRepository, OrderRepository orderRepository) {
        this.bookRepository = bookRepository;
        this.inventoryItemRepository = inventoryItemRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public Order createOrder(String customerName, List<OrderItemRequest> items) {
        log.debug("Creating order for customer: {} with {} items.", customerName, items.size());
        List<OrderLine> orderLines = new ArrayList<>();
        for (OrderItemRequest itemRequest : items) {
            log.debug("Processing item request: {}", itemRequest);
            Book book = bookRepository.findById(itemRequest.getIsbn())
                    .orElseThrow(() -> {
                        log.warn("Book with ISBN {} not found in catalog.", itemRequest.getIsbn());
                        return new IllegalArgumentException("Book with ISBN " + itemRequest.getIsbn() + " not found in catalog.");
                    });
            InventoryItem inventoryItem = inventoryItemRepository.findById(itemRequest.getIsbn())
                    .orElseThrow(() -> {
                        log.warn("Inventory item with ISBN {} not found.", itemRequest.getIsbn());
                        return new IllegalArgumentException("Inventory item with ISBN " + itemRequest.getIsbn() + " not found.");
                    });

            // Deduct stock
            log.debug("Deducting {} from stock for ISBN {}. Current stock: {}", itemRequest.getQuantity(), itemRequest.getIsbn(), inventoryItem.getStock());
            inventoryItem.deductStock(itemRequest.getQuantity());
            inventoryItemRepository.save(inventoryItem);
            log.info("Stock for ISBN {} updated to: {}", itemRequest.getIsbn(), inventoryItem.getStock());

            orderLines.add(new OrderLine(itemRequest.getIsbn(), itemRequest.getQuantity(), book.getPrice()));
        }

        Order order = new Order(customerName, orderLines);
        Order savedOrder = orderRepository.save(order);
        log.info("Order created and saved: {}", savedOrder);
        return savedOrder;
    }
}
