package io.bmeurant.bookordermanager.bootstrap;

import io.bmeurant.bookordermanager.application.dto.CreateOrderRequest;
import io.bmeurant.bookordermanager.application.dto.OrderItemRequest;
import io.bmeurant.bookordermanager.application.service.OrderService;
import io.bmeurant.bookordermanager.catalog.domain.model.Book;
import io.bmeurant.bookordermanager.catalog.domain.repository.BookRepository;
import io.bmeurant.bookordermanager.inventory.domain.model.InventoryItem;
import io.bmeurant.bookordermanager.inventory.domain.repository.InventoryItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final BookRepository bookRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final OrderService orderService;

    public DataInitializer(BookRepository bookRepository, InventoryItemRepository inventoryItemRepository, OrderService orderService) {
        this.bookRepository = bookRepository;
        this.inventoryItemRepository = inventoryItemRepository;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) {
        if (bookRepository.count() == 0 && inventoryItemRepository.count() == 0) {
            log.info("No data found. Initializing sample dataset...");

            createSampleBooks();
            createSampleInventoryItems();
            createSampleOrders();

            log.info("Sample dataset initialized successfully.");
        } else {
            log.info("Database already contains data. Skipping initialization.");
        }
    }

    private void createSampleBooks() {
        Book book1 = new Book("978-0321765723", "The Lord of the Rings", "J.R.R. Tolkien", new BigDecimal("25.00"));
        Book book2 = new Book("978-0132350884", "Clean Code", "Robert C. Martin", new BigDecimal("35.00"));
        Book book3 = new Book("978-0134786275", "Effective Java", "Joshua Bloch", new BigDecimal("40.00"));
        Book book4 = new Book("978-1491904244", "Designing Data-Intensive Applications", "Martin Kleppmann", new BigDecimal("60.00"));

        bookRepository.saveAll(List.of(book1, book2, book3, book4));
        log.info("Saved {} books.", bookRepository.count());
    }

    private void createSampleInventoryItems() {
        InventoryItem item1 = new InventoryItem("978-0321765723", 15);
        InventoryItem item2 = new InventoryItem("978-0132350884", 8);
        InventoryItem item3 = new InventoryItem("978-0134786275", 20);
        InventoryItem item4 = new InventoryItem("978-1491904244", 5);

        inventoryItemRepository.saveAll(List.of(item1, item2, item3, item4));
        log.info("Saved {} inventory items.", inventoryItemRepository.count());
    }

    private void createSampleOrders() {
        try {
            // First Order
            List<OrderItemRequest> order1Items = List.of(
                    new OrderItemRequest("978-0321765723", 1),
                    new OrderItemRequest("978-0132350884", 1)
            );
            CreateOrderRequest order1Request = new CreateOrderRequest("Aragorn", order1Items);
            orderService.createOrder(order1Request);
            log.info("Created order for Aragorn.");

            // Second Order
            List<OrderItemRequest> order2Items = List.of(
                    new OrderItemRequest("978-0134786275", 2)
            );
            CreateOrderRequest order2Request = new CreateOrderRequest("Gandalf", order2Items);
            orderService.createOrder(order2Request);
            log.info("Created order for Gandalf.");

        } catch (Exception e) {
            log.error("Error creating sample orders: {}", e.getMessage());
        }
    }
}
