package io.bmeurant.bookordermanager.order.domain.handler;

import io.bmeurant.bookordermanager.order.domain.event.OrderCreatedEvent;
import io.bmeurant.bookordermanager.order.domain.model.Order;
import io.bmeurant.bookordermanager.order.domain.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Handles events related to the Order domain.
 */
@Component
public class OrderEventHandler {

    private static final Logger log = LoggerFactory.getLogger(OrderEventHandler.class);

    private final OrderRepository orderRepository;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public OrderEventHandler(OrderRepository orderRepository, PlatformTransactionManager transactionManager) {
        this.orderRepository = orderRepository;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    /**
     * Handles the {@link OrderCreatedEvent} to confirm the order.
     * This method is executed asynchronously in a new transaction after the original transaction commits.
     *
     * @param event the {@link OrderCreatedEvent}
     */
    @Async
    @TransactionalEventListener
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Handling order created event: {}", event);
        transactionTemplate.execute(status -> {
            Order order = orderRepository.findById(event.getOrder().getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            order.confirm();
            orderRepository.save(order);
            log.info("Order {} confirmed and saved.", order.getOrderId());
            return null;
        });
    }
}
