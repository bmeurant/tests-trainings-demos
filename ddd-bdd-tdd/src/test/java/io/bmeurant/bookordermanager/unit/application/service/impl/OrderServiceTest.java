package io.bmeurant.bookordermanager.unit.application.service.impl;

import io.bmeurant.bookordermanager.application.dto.OrderItemRequest;
import io.bmeurant.bookordermanager.application.service.OrderService;
import io.bmeurant.bookordermanager.application.service.impl.OrderServiceImpl;
import io.bmeurant.bookordermanager.catalog.domain.model.Book;
import io.bmeurant.bookordermanager.catalog.domain.repository.BookRepository;
import io.bmeurant.bookordermanager.inventory.domain.model.InventoryItem;
import io.bmeurant.bookordermanager.inventory.domain.service.InventoryService;
import io.bmeurant.bookordermanager.order.domain.model.Order;
import io.bmeurant.bookordermanager.order.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private InventoryService inventoryService;
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_shouldCreateOrderAndDeductStockSuccessfully() {
        // Given
        String customerName = "Test Customer";
        String isbn1 = "978-0321765723";
        String isbn2 = "978-0132350884";
        int quantity1 = 2;
        int quantity2 = 1;
        BigDecimal price1 = new BigDecimal("25.00");
        BigDecimal price2 = new BigDecimal("35.00");

        OrderItemRequest itemRequest1 = new OrderItemRequest(isbn1, quantity1);
        OrderItemRequest itemRequest2 = new OrderItemRequest(isbn2, quantity2);
        List<OrderItemRequest> itemRequests = Arrays.asList(itemRequest1, itemRequest2);

        Book book1 = new Book(isbn1, "Book One", "Author One", price1);
        Book book2 = new Book(isbn2, "Book Two", "Author Two", price2);
        InventoryItem inventoryItem1 = new InventoryItem(isbn1, 10);
        InventoryItem inventoryItem2 = new InventoryItem(isbn2, 5);

        when(bookRepository.findById(isbn1)).thenReturn(Optional.of(book1));
        when(bookRepository.findById(isbn2)).thenReturn(Optional.of(book2));
        when(inventoryService.deductStock(isbn1, quantity1)).thenReturn(inventoryItem1);
        when(inventoryService.deductStock(isbn2, quantity2)).thenReturn(inventoryItem2);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Order createdOrder = orderService.createOrder(customerName, itemRequests);

        // Then
        assertNotNull(createdOrder, "Created order should not be null.");
        assertEquals(customerName, createdOrder.getCustomerName(), "Customer name should match.");
        assertEquals(Order.OrderStatus.PENDING, createdOrder.getStatus(), "Order status should be PENDING.");
        assertEquals(2, createdOrder.getOrderLines().size(), "Should have two order lines.");

        // Verify stock deduction
        verify(inventoryService, times(1)).deductStock(isbn1, quantity1);
        verify(inventoryService, times(1)).deductStock(isbn2, quantity2);

        // Verify repository interactions
        verify(bookRepository, times(1)).findById(isbn1);
        verify(bookRepository, times(1)).findById(isbn2);
        verify(orderRepository, times(1)).save(createdOrder);
    }

    @Test
    void createOrder_shouldThrowExceptionWhenBookNotFound() {
        // Given
        String customerName = "Test Customer";
        String isbn1 = "978-0321765723";
        int quantity1 = 2;

        OrderItemRequest itemRequest1 = new OrderItemRequest(isbn1, quantity1);
        List<OrderItemRequest> itemRequests = List.of(itemRequest1);

        when(bookRepository.findById(isbn1)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(customerName, itemRequests), "Should throw IllegalArgumentException when book is not found.");
        assertTrue(exception.getMessage().contains("Book with ISBN " + isbn1 + " not found in catalog."), "Exception message should indicate book not found.");
    }

    @Test
    void createOrder_shouldThrowExceptionWhenInventoryItemNotFound() {
        // Given
        String customerName = "Test Customer";
        String isbn1 = "978-0321765723";
        int quantity1 = 2;

        OrderItemRequest itemRequest1 = new OrderItemRequest(isbn1, quantity1);
        List<OrderItemRequest> itemRequests = List.of(itemRequest1);

        Book book1 = new Book(isbn1, "Book One", "Author One", new BigDecimal("25.00"));

        when(bookRepository.findById(isbn1)).thenReturn(Optional.of(book1));
        when(inventoryService.deductStock(isbn1, quantity1)).thenThrow(new IllegalArgumentException("Inventory item with ISBN " + isbn1 + " not found."));

        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(customerName, itemRequests), "Should throw IllegalArgumentException when inventory item is not found.");
        assertTrue(exception.getMessage().contains("Inventory item with ISBN " + isbn1 + " not found."), "Exception message should indicate inventory item not found.");
    }

    @Test
    void createOrder_shouldThrowExceptionWhenNotEnoughStock() {
        // Given
        String customerName = "Test Customer";
        String isbn1 = "978-0321765723";
        int quantity1 = 15; // More than available stock

        OrderItemRequest itemRequest1 = new OrderItemRequest(isbn1, quantity1);
        List<OrderItemRequest> itemRequests = List.of(itemRequest1);

        Book book1 = new Book(isbn1, "Book One", "Author One", new BigDecimal("25.00"));
        InventoryItem inventoryItem1 = new InventoryItem(isbn1, 10);

        when(bookRepository.findById(isbn1)).thenReturn(Optional.of(book1));
        when(inventoryService.deductStock(isbn1, quantity1)).thenThrow(new IllegalArgumentException("Not enough stock to deduct"));

        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(customerName, itemRequests), "Should throw IllegalArgumentException when not enough stock.");
        assertTrue(exception.getMessage().contains("Not enough stock to deduct"), "Exception message should indicate insufficient stock.");
    }
}

