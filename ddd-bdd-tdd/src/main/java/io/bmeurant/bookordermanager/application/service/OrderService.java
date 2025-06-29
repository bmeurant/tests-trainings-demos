package io.bmeurant.bookordermanager.application.service;

import io.bmeurant.bookordermanager.order.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public interface OrderService {

    Order createOrder(String customerName, List<OrderItemRequest> items);

    @AllArgsConstructor
    @Getter
    class OrderItemRequest {
        private String isbn;
        private int quantity;
    }
}
