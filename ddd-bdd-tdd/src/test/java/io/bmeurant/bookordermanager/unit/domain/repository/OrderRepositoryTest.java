package io.bmeurant.bookordermanager.unit.domain.repository;

import io.bmeurant.bookordermanager.order.domain.model.Order;
import io.bmeurant.bookordermanager.order.domain.model.OrderLine;
import io.bmeurant.bookordermanager.order.domain.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldSaveAndFindOrder() {
        OrderLine line1 = new OrderLine("978-0321765723", 2, new BigDecimal("25.00"));
        OrderLine line2 = new OrderLine("978-0132350884", 1, new BigDecimal("35.00"));
        List<OrderLine> orderLines = Arrays.asList(line1, line2);
        Order order = new Order("Alice Wonderland", orderLines);

        Order savedOrder = orderRepository.save(order);

        assertNotNull(savedOrder, "Saved order should not be null.");
        assertNotNull(savedOrder.getOrderId(), "Saved order ID should not be null.");
        assertNotNull(savedOrder.getVersion(), "Saved order version should not be null.");
        assertEquals(0L, savedOrder.getVersion(), "Initial version should be 0.");

        Optional<Order> foundOrder = orderRepository.findById(savedOrder.getOrderId());

        assertTrue(foundOrder.isPresent(), "Expected order to be present after saving.");
        assertEquals(savedOrder, foundOrder.get(), "Saved and found order should be equal.");
        assertEquals(savedOrder.getOrderLines().size(), foundOrder.get().getOrderLines().size(), "Number of order lines should match.");
        assertEquals(savedOrder.getVersion(), foundOrder.get().getVersion(), "Versions should match after initial save.");
    }

    @Test
    void shouldIncrementVersionOnUpdate() {
        OrderLine line1 = new OrderLine("978-0321765723", 2, new BigDecimal("25.00"));
        List<OrderLine> orderLines = List.of(line1);
        Order order = new Order("Alice Wonderland", orderLines);

        Order savedOrder = orderRepository.save(order);
        assertEquals(0L, savedOrder.getVersion(), "Initial version should be 0.");

        // Modify the saved order and save it again
        savedOrder.confirm();
        orderRepository.save(savedOrder);

        // Flush and clear the persistence context to ensure the next fetch comes from the DB
        entityManager.flush();
        entityManager.clear();

        // Fetch the order again to verify the updated version
        Optional<Order> foundOrder = orderRepository.findById(savedOrder.getOrderId());
        assertTrue(foundOrder.isPresent(), "Expected order to be present after update.");
        Order verifiedOrder = foundOrder.get();

        assertEquals(1L, verifiedOrder.getVersion(), "Version should be incremented to 1 after update.");
    }
}
