package io.bmeurant.microservices.spring.orderservice.repository;

import io.bmeurant.microservices.spring.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}