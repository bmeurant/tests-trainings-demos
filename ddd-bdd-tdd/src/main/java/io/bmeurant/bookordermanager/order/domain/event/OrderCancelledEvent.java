package io.bmeurant.bookordermanager.order.domain.event;

import io.bmeurant.bookordermanager.order.domain.model.Order;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event published when an order is successfully cancelled.
 * This event carries the details of the cancelled order.
 */
@Getter
public class OrderCancelledEvent extends ApplicationEvent {
    private final transient Order order;

    /**
     * Constructs a new {@code OrderCancelledEvent}.
     *
     * @param order The {@link Order} object that was cancelled. This object is also used as the source of the event.
     */
    public OrderCancelledEvent(Order order) {
        super(order);
        this.order = order;
    }
}
