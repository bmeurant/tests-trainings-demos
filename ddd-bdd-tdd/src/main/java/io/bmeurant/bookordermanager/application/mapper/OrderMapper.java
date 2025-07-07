package io.bmeurant.bookordermanager.application.mapper;

import io.bmeurant.bookordermanager.application.dto.OrderLineResponse;
import io.bmeurant.bookordermanager.application.dto.OrderResponse;
import io.bmeurant.bookordermanager.order.domain.model.Order;
import io.bmeurant.bookordermanager.order.domain.model.OrderLine;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper class responsible for converting domain objects to DTOs for the Order context.
 */
@Component
public class OrderMapper {

    /**
     * Converts an {@link Order} domain object to an {@link OrderResponse} DTO.
     *
     * @param order The Order domain object to convert.
     * @return The corresponding OrderResponse DTO.
     */
    public OrderResponse mapOrderToResponse(Order order) {
        List<OrderLineResponse> orderLineResponses = order.getOrderLines().stream()
                .map(this::mapOrderLineToResponse)
                .toList();
        return new OrderResponse(
                order.getOrderId(),
                order.getCustomerName(),
                order.getStatus().name(), // Convert enum to String
                orderLineResponses
        );
    }

    /**
     * Converts an {@link OrderLine} domain object to an {@link OrderLineResponse} DTO.
     *
     * @param orderLine The OrderLine domain object to convert.
     * @return The corresponding OrderLineResponse DTO.
     */
    public OrderLineResponse mapOrderLineToResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getIsbn(),
                orderLine.getQuantity(),
                orderLine.getPrice()
        );
    }
}
