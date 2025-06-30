package io.bmeurant.bookordermanager.unit.order.domain.handler;

import io.bmeurant.bookordermanager.order.domain.event.OrderCreatedEvent;
import io.bmeurant.bookordermanager.order.domain.handler.OrderEventHandler;
import io.bmeurant.bookordermanager.order.domain.model.Order;
import io.bmeurant.bookordermanager.order.domain.model.OrderLine;
import io.bmeurant.bookordermanager.order.domain.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.PlatformTransactionManager;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderEventHandlerTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PlatformTransactionManager transactionManager;

    @InjectMocks
    private OrderEventHandler orderEventHandler;

    @Test
    void handleOrderCreatedEvent_shouldConfirmOrder() {
        // Given
        OrderLine orderLine = new OrderLine("978-0321765723", 1, BigDecimal.valueOf(25.00));
        Order order = new Order("customer", List.of(orderLine));
        OrderCreatedEvent event = new OrderCreatedEvent(order);

        // When
        orderEventHandler.handleOrderCreatedEvent(event);

        // Then
        verify(orderRepository, never()).save(any(Order.class));
    }
}
