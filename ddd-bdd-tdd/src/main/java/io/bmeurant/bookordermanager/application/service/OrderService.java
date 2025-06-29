package io.bmeurant.bookordermanager.application.service;

import io.bmeurant.bookordermanager.application.dto.OrderItemRequest;
import io.bmeurant.bookordermanager.order.domain.model.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(String customerName, List<OrderItemRequest> items);
}
