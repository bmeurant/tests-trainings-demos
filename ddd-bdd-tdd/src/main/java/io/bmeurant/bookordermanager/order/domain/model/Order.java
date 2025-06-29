package io.bmeurant.bookordermanager.order.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;

/**
 * Represents an order in the order domain. An order is an aggregate root.
 * It encapsulates a collection of order lines and manages the order's lifecycle.
 */
@Getter
@EqualsAndHashCode(of = "orderId")
@ToString
public class Order {
    private String orderId;
    private String customerName;
    private OrderStatus status;
    private List<OrderLine> orderLines;

    /**
     * Constructs a new Order instance.
     * All parameters are validated to ensure the order is created in a valid state.
     *
     * @param customerName The name of the customer placing the order. Must not be null or blank.
     * @param orderLines A list of order lines for this order. Must not be null or empty.
     * @throws IllegalArgumentException if any validation fails.
     */
    public Order(String customerName, List<OrderLine> orderLines) {
        Assert.hasText(customerName, "Customer name cannot be null or blank");
        Assert.notEmpty(orderLines, "Order lines cannot be null or empty");

        this.orderId = UUID.randomUUID().toString(); // Generate a unique ID for the order
        this.customerName = customerName;
        this.status = OrderStatus.PENDING; // Initial status
        this.orderLines = orderLines;
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
