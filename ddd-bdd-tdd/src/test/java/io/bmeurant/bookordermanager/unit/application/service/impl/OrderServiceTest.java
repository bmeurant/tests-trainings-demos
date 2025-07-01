package io.bmeurant.bookordermanager.unit.application.service.impl;

import io.bmeurant.bookordermanager.application.dto.OrderItemRequest;
import io.bmeurant.bookordermanager.application.service.impl.OrderServiceImpl;
import io.bmeurant.bookordermanager.catalog.domain.exception.BookNotFoundException;
import io.bmeurant.bookordermanager.catalog.domain.model.Book;
import io.bmeurant.bookordermanager.catalog.domain.service.BookService;
import io.bmeurant.bookordermanager.inventory.domain.exception.InsufficientStockException;
import io.bmeurant.bookordermanager.inventory.domain.model.InventoryItem;
import io.bmeurant.bookordermanager.inventory.domain.service.InventoryService;
import io.bmeurant.bookordermanager.order.domain.event.OrderCreatedEvent;
import io.bmeurant.bookordermanager.order.domain.exception.OrderNotFoundException;
import io.bmeurant.bookordermanager.order.domain.model.Order;
import io.bmeurant.bookordermanager.order.domain.model.OrderLine;
import io.bmeurant.bookordermanager.order.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private BookService bookService;
    @Mock
    private InventoryService inventoryService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_shouldCreateOrderAndCheckStockSuccessfully() {
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

        when(bookService.findBookByIsbn(isbn1)).thenReturn(book1);
        when(bookService.findBookByIsbn(isbn2)).thenReturn(book2);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Mock checkStock calls
        doNothing().when(inventoryService).checkStock(isbn1, quantity1);
        doNothing().when(inventoryService).checkStock(isbn2, quantity2);

        // When
        Order createdOrder = orderService.createOrder(customerName, itemRequests);

        // Then
        assertNotNull(createdOrder, "Created order should not be null.");
        assertEquals(customerName, createdOrder.getCustomerName(), "Customer name should match.");
        assertEquals(Order.OrderStatus.PENDING, createdOrder.getStatus(), "Order status should be PENDING.");
        assertEquals(2, createdOrder.getOrderLines().size(), "Should have two order lines.");

        // Verify service interactions
        verify(bookService, times(1)).findBookByIsbn(isbn1);
        verify(bookService, times(1)).findBookByIsbn(isbn2);
        verify(orderRepository, times(1)).save(createdOrder);
        verify(applicationEventPublisher, times(1)).publishEvent(any(OrderCreatedEvent.class));
        verify(inventoryService, times(1)).checkStock(isbn1, quantity1);
        verify(inventoryService, times(1)).checkStock(isbn2, quantity2);
        verify(inventoryService, never()).deductStock(anyString(), anyInt());
    }

    @Test
    void createOrder_shouldThrowExceptionWhenBookNotFound() {
        // Given
        String customerName = "Test Customer";
        String isbn1 = "978-0321765723";
        int quantity1 = 2;

        OrderItemRequest itemRequest1 = new OrderItemRequest(isbn1, quantity1);
        List<OrderItemRequest> itemRequests = List.of(itemRequest1);

        when(bookService.findBookByIsbn(isbn1)).thenThrow(new BookNotFoundException(isbn1));

        // When & Then
        assertThrows(BookNotFoundException.class, () -> orderService.createOrder(customerName, itemRequests), "Should throw BookNotFoundException when book is not found.");
    }

    @Test
    void findOrderById_shouldReturnOrderWhenFound() {
        // Given
        String orderId = UUID.randomUUID().toString();
        Order order = new Order("Test Customer", List.of(new OrderLine("123", 1, BigDecimal.ONE)));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // When
        Optional<Order> foundOrder = orderService.findOrderById(orderId);

        // Then
        assertTrue(foundOrder.isPresent(), "Order should be found.");
        assertEquals(order, foundOrder.get(), "Found order should match the expected order.");
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void findOrderById_shouldReturnEmptyWhenNotFound() {
        // Given
        String orderId = UUID.randomUUID().toString();
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // When
        Optional<Order> foundOrder = orderService.findOrderById(orderId);

        // Then
        assertFalse(foundOrder.isPresent(), "Order should not be found.");
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void confirmOrder_shouldConfirmOrderAndDeductStockSuccessfully() {
        // Given
        String orderId = UUID.randomUUID().toString();
        String isbn1 = "978-0321765723";
        int quantity1 = 2;
        BigDecimal price1 = new BigDecimal("25.00");

        OrderLine orderLine = new OrderLine(isbn1, quantity1, price1);
        Order order = new Order("Test Customer", List.of(orderLine));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(inventoryService.deductStock(isbn1, quantity1)).thenReturn(new InventoryItem(isbn1, 10 - quantity1));

        // When
        Order confirmedOrder = orderService.confirmOrder(orderId);

        // Then
        assertNotNull(confirmedOrder, "Confirmed order should not be null.");
        assertEquals(Order.OrderStatus.CONFIRMED, confirmedOrder.getStatus(), "Order status should be CONFIRMED.");
        verify(inventoryService, times(1)).deductStock(isbn1, quantity1);
        verify(orderRepository, times(1)).save(confirmedOrder);
    }

    @Test
    void confirmOrder_shouldThrowExceptionWhenOrderNotFound() {
        // Given
        String orderId = UUID.randomUUID().toString();
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(OrderNotFoundException.class, () -> orderService.confirmOrder(orderId), "Should throw OrderNotFoundException when order is not found.");
        verify(inventoryService, never()).deductStock(anyString(), anyInt());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void confirmOrder_shouldThrowExceptionWhenStockInsufficient() {
        // Given
        String orderId = UUID.randomUUID().toString();
        String isbn1 = "978-0321765723";
        int quantity1 = 15;
        BigDecimal price1 = new BigDecimal("25.00");

        OrderLine orderLine = new OrderLine(isbn1, quantity1, price1);
        Order order = new Order("Test Customer", List.of(orderLine));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(inventoryService.deductStock(isbn1, quantity1)).thenThrow(new InsufficientStockException(isbn1, quantity1, 10));

        // When & Then
        assertThrows(InsufficientStockException.class, () -> orderService.confirmOrder(orderId), "Should throw InsufficientStockException when stock is insufficient.");
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void cancelOrder_shouldCancelConfirmedOrderAndReleaseStock() {
        // Given
        String orderId = UUID.randomUUID().toString();
        String isbn1 = "978-0321765723";
        int quantity1 = 2;
        BigDecimal price1 = new BigDecimal("25.00");

        OrderLine orderLine = new OrderLine(isbn1, quantity1, price1);
        Order order = new Order("Test Customer", List.of(orderLine));
        order.confirm(); // Simulate a confirmed order

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Order cancelledOrder = orderService.cancelOrder(orderId);

        // Then
        assertNotNull(cancelledOrder, "Cancelled order should not be null.");
        assertEquals(Order.OrderStatus.CANCELLED, cancelledOrder.getStatus(), "Order status should be CANCELLED.");
        verify(inventoryService, times(1)).releaseStock(isbn1, quantity1);
        verify(orderRepository, times(1)).save(cancelledOrder);
        verify(applicationEventPublisher, times(1)).publishEvent(any(io.bmeurant.bookordermanager.order.domain.event.OrderCancelledEvent.class));
    }

    @Test
    void cancelOrder_shouldCancelPendingOrderWithoutReleasingStock() {
        // Given
        String orderId = UUID.randomUUID().toString();
        String isbn1 = "978-0321765723";
        int quantity1 = 2;
        BigDecimal price1 = new BigDecimal("25.00");

        OrderLine orderLine = new OrderLine(isbn1, quantity1, price1);
        Order order = new Order("Test Customer", List.of(orderLine));
        // Order is PENDING by default

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Order cancelledOrder = orderService.cancelOrder(orderId);

        // Then
        assertNotNull(cancelledOrder, "Cancelled order should not be null.");
        assertEquals(Order.OrderStatus.CANCELLED, cancelledOrder.getStatus(), "Order status should be CANCELLED.");
        verify(inventoryService, never()).releaseStock(anyString(), anyInt()); // No stock release for PENDING order
        verify(orderRepository, times(1)).save(cancelledOrder);
        verify(applicationEventPublisher, times(1)).publishEvent(any(io.bmeurant.bookordermanager.order.domain.event.OrderCancelledEvent.class));
    }

    @Test
    void cancelOrder_shouldThrowOrderNotFoundExceptionWhenOrderDoesNotExist() {
        // Given
        String orderId = UUID.randomUUID().toString();
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(OrderNotFoundException.class, () -> orderService.cancelOrder(orderId), "Should throw OrderNotFoundException when order is not found.");
        verify(inventoryService, never()).releaseStock(anyString(), anyInt());
        verify(orderRepository, never()).save(any(Order.class));
        verify(applicationEventPublisher, never()).publishEvent(any());
    }

    @Test
    void cancelOrder_shouldThrowValidationExceptionWhenOrderIsAlreadyCancelled() {
        // Given
        String orderId = UUID.randomUUID().toString();
        String isbn1 = "978-0321765723";
        int quantity1 = 2;
        BigDecimal price1 = new BigDecimal("25.00");

        OrderLine orderLine = new OrderLine(isbn1, quantity1, price1);
        Order order = new Order("Test Customer", List.of(orderLine));
        order.cancel(); // Simulate an already cancelled order

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // When & Then
        assertThrows(io.bmeurant.bookordermanager.domain.exception.ValidationException.class, () -> orderService.cancelOrder(orderId), "Should throw ValidationException when order is already cancelled.");
        verify(inventoryService, never()).releaseStock(anyString(), anyInt());
        verify(orderRepository, never()).save(any(Order.class));
        verify(applicationEventPublisher, never()).publishEvent(any());
    }

    @Test
    void cancelOrder_shouldThrowValidationExceptionWhenOrderIsDelivered() {
        // Given
        String orderId = UUID.randomUUID().toString();
        String isbn1 = "978-0321765723";
        int quantity1 = 2;
        BigDecimal price1 = new BigDecimal("25.00");

        OrderLine orderLine = new OrderLine(isbn1, quantity1, price1);
        Order order = new Order("Test Customer", List.of(orderLine));
        order.confirm(); // Simulate a confirmed order
        // Manually set status to DELIVERED for testing purposes
        org.springframework.test.util.ReflectionTestUtils.setField(order, "status", Order.OrderStatus.DELIVERED);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // When & Then
        assertThrows(io.bmeurant.bookordermanager.domain.exception.ValidationException.class, () -> orderService.cancelOrder(orderId), "Should throw ValidationException when order is delivered.");
        verify(inventoryService, never()).releaseStock(anyString(), anyInt());
        verify(orderRepository, never()).save(any(Order.class));
        verify(applicationEventPublisher, never()).publishEvent(any());
    }
}
