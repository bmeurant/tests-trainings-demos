package io.bmeurant.bookordermanager.order.domain.handler;

import io.bmeurant.bookordermanager.order.domain.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Handles events related to the Order domain.
 */
@Component
public class OrderEventHandler {

    private static final Logger log = LoggerFactory.getLogger(OrderEventHandler.class);

    /**
     * Handles the {@link OrderCreatedEvent}.
     * This method is executed asynchronously in a new transaction after the original transaction commits.
     * Note: The order confirmation logic has been moved to an explicit `confirmOrder` method in `OrderService`.
     *
     * @param event the {@link OrderCreatedEvent}
     */
    @Async
    @TransactionalEventListener
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Handling order created event: {}", event);
        // No longer confirms the order here. Confirmation is now an explicit step.
    }
}
