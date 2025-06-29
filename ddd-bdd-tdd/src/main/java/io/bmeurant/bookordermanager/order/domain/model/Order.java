package io.bmeurant.bookordermanager.order.domain.model;

import io.bmeurant.bookordermanager.domain.exception.ValidationException;
import jakarta.persistence.*;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents an order in the order domain. An order is an aggregate root.
 * It encapsulates a collection of order lines and manages the order's lifecycle.
 */
@Entity
@Table(name = "orders")
@Getter
@EqualsAndHashCode(of = "orderId")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    private static final Logger log = LoggerFactory.getLogger(Order.class);

    // Identity
    @Id
    private String orderId;

    // Attributes
    private String customerName;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // Associations
    @ElementCollection
    @CollectionTable(name = "order_lines", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderLine> orderLines;

    // Concurrency control
    @Version
    private Long version;

    /**
     * Constructs a new Order instance.
     * All parameters are validated to ensure the order is created in a valid state.
     *
     * @param customerName The name of the customer placing the order. Must not be null or blank.
     * @param orderLines   A list of order lines for this order. Must not be null or empty.
     * @throws ValidationException if any validation fails.
     */
    public Order(String customerName, List<OrderLine> orderLines) {
        log.debug("Creating Order for customer: {}", customerName);
        if (customerName == null || customerName.isBlank()) {
            throw new ValidationException("Customer name cannot be null or blank", Order.class);
        }
        if (orderLines == null || orderLines.isEmpty()) {
            throw new ValidationException("Order lines cannot be null or empty", Order.class);
        }

        this.orderId = UUID.randomUUID().toString(); // Generate a unique ID for the order
        this.customerName = customerName;
        this.status = OrderStatus.PENDING; // Initial status
        this.orderLines = new ArrayList<>(orderLines);
        log.info("Order created: {}", this);
    }

    /**
     * Confirms the order, changing its status from PENDING to CONFIRMED.
     *
     * @throws ValidationException if the order is not in PENDING status.
     */
    public void confirm() {
        if (this.status != OrderStatus.PENDING) {
            throw new ValidationException("Order can only be confirmed if its status is PENDING.", Order.class);
        }
        this.status = OrderStatus.CONFIRMED;
        log.info("Order {} confirmed.", this.orderId);
    }

    /**
     * Enum representing the possible statuses of an order.
     */
    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        CANCELLED,
        DELIVERED
    }
}
