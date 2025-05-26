package io.bmeurant.microservices.spring.orderservice.service;

import io.bmeurant.microservices.spring.orderservice.client.ProductClient;
import io.bmeurant.microservices.spring.orderservice.dto.OrderLineItemsDto;
import io.bmeurant.microservices.spring.orderservice.dto.OrderRequest;
import io.bmeurant.microservices.spring.orderservice.dto.ProductResponse;
import io.bmeurant.microservices.spring.orderservice.model.Order;
import io.bmeurant.microservices.spring.orderservice.model.OrderLineItems;
import io.bmeurant.microservices.spring.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.orderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<Long> productIdsInOrder = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getProductId)
                .toList();

         List<ProductResponse> availableProducts = productClient.getAllProducts();

        for (OrderLineItems item : orderLineItems) {
            ProductResponse productDetails = availableProducts.stream()
                    .filter(p -> p.id().equals(item.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Product with ID " + item.getProductId() + " not found."));

            item.setPrice(productDetails.price());
        }

        orderRepository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                .productId(orderLineItemsDto.productId())
                .price(orderLineItemsDto.price())
                .quantity(orderLineItemsDto.quantity())
                .build();
    }
}
