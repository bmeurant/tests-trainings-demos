package io.bmeurant.bookordermanager.order.domain.event;

import io.bmeurant.bookordermanager.order.domain.model.Order;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderCreatedEvent extends ApplicationEvent {
    private final Order order;

    public OrderCreatedEvent(Order order) {
        super(order);
        this.order = order;
    }
}
