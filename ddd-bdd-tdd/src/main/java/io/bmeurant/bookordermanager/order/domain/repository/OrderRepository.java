package io.bmeurant.bookordermanager.order.domain.repository;

import io.bmeurant.bookordermanager.order.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Order entities.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
