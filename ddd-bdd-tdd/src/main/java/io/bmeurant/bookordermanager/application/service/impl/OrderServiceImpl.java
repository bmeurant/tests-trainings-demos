package io.bmeurant.bookordermanager.application.service.impl;

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

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

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
        List<OrderLine> orderLines = new ArrayList<>();
        for (OrderItemRequest itemRequest : items) {
            Book book = bookRepository.findById(itemRequest.getIsbn())
                    .orElseThrow(() -> new IllegalArgumentException("Book with ISBN " + itemRequest.getIsbn() + " not found in catalog."));
            InventoryItem inventoryItem = inventoryItemRepository.findById(itemRequest.getIsbn())
                    .orElseThrow(() -> new IllegalArgumentException("Inventory item with ISBN " + itemRequest.getIsbn() + " not found."));

            // Deduct stock
            inventoryItem.deductStock(itemRequest.getQuantity());
            inventoryItemRepository.save(inventoryItem);

            orderLines.add(new OrderLine(itemRequest.getIsbn(), itemRequest.getQuantity(), book.getPrice()));
        }

        Order order = new Order(customerName, orderLines);
        return orderRepository.save(order);
    }
}
