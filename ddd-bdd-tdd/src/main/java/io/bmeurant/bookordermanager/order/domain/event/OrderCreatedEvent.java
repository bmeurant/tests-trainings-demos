package io.bmeurant.bookordermanager.order.domain.event;

import io.bmeurant.bookordermanager.order.domain.model.Order;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event published when a new order is successfully created.
 * This event carries the details of the newly created order.
 */
@Getter
public class OrderCreatedEvent extends ApplicationEvent {
    private final Order order;

    /**
     * Constructs a new {@code OrderCreatedEvent}.
     *
     * @param order The {@link Order} object that was created. This object is also used as the source of the event.
     */
    public OrderCreatedEvent(Order order) {
        super(order);
        this.order = order;
    }
}
