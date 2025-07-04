package io.bmeurant.bookordermanager.integration.steps;

import io.bmeurant.bookordermanager.application.dto.OrderItemRequest;
import io.bmeurant.bookordermanager.application.service.OrderService;
import io.bmeurant.bookordermanager.catalog.domain.model.Book;
import io.bmeurant.bookordermanager.catalog.domain.repository.BookRepository;
import io.bmeurant.bookordermanager.integration.events.TestEventListener;
import io.bmeurant.bookordermanager.inventory.domain.event.ProductStockLowEvent;
import io.bmeurant.bookordermanager.inventory.domain.model.InventoryItem;
import io.bmeurant.bookordermanager.inventory.domain.repository.InventoryItemRepository;
import io.bmeurant.bookordermanager.inventory.domain.service.InventoryService;
import io.bmeurant.bookordermanager.order.domain.event.OrderCreatedEvent;
import io.bmeurant.bookordermanager.order.domain.event.OrderCancelledEvent;
import io.bmeurant.bookordermanager.order.domain.model.Order;
import io.bmeurant.bookordermanager.order.domain.model.OrderLine;
import io.bmeurant.bookordermanager.order.domain.repository.OrderRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEvent;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@CucumberContextConfiguration
@SpringBootTest(classes = io.bmeurant.bookordermanager.integration.TestApplication.class)
public class OrderManagementSteps {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private InventoryItemRepository inventoryItemRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @MockitoSpyBean
    private InventoryService inventoryService;
    @Autowired
    private TestEventListener testEventListener;

    private Order currentOrder;
    private Exception caughtException;

    @Before
    public void setup() {
        currentOrder = null;
        caughtException = null;
        testEventListener.clearEvents();
    }

    @Given("a book with ISBN {string}, title {string}, author {string}, price {bigdecimal}")
    public void a_book_with_isbn_title_author_price(String isbn, String title, String author, BigDecimal price) {
        Book book = new Book(isbn, title, author, price);
        bookRepository.save(book);
    }

    @Given("an inventory item {string} with initial stock of {int}")
    public void an_inventory_item_with_initial_stock_of(String isbn, Integer stock) {
        InventoryItem item = new InventoryItem(isbn, stock);
        inventoryItemRepository.save(item);
    }

    @When("I try to create an order for {string} with the following items:")
    public void i_try_to_create_an_order_for_with_the_following_items(String customerName, DataTable dataTable) {
        List<OrderItemRequest> itemRequests = new ArrayList<>();
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            String isbn = row.get("productId");
            int quantity = Integer.parseInt(row.get("quantity"));
            itemRequests.add(new OrderItemRequest(isbn, quantity));
        }
        try {
            currentOrder = orderService.createOrder(customerName, itemRequests);
        } catch (Exception e) {
            caughtException = e;
        }
    }

    @Then("the order creation should fail with message {string}")
    public void the_order_creation_should_fail_with_message(String expectedMessage) {
        assertNotNull(caughtException, "An exception should have been caught.");
        assertTrue(caughtException.getMessage().contains(expectedMessage), "Exception message should contain: " + expectedMessage + ". Actual: " + caughtException.getMessage());
        assertNull(currentOrder, "Order should not be created on failure.");
    }

    @Then("the order should be created successfully with status {string}")
    public void the_order_should_be_created_successfully_with_status(String expectedStatus) {
        assertNotNull(currentOrder, "Current order should not be null after creation.");
        assertEquals(Order.OrderStatus.valueOf(expectedStatus), currentOrder.getStatus(), "Order status should match the expected status.");
    }

    @Then("an {string} event should have been published for the order of {string}")
    public void an_event_should_have_been_published_for_the_order_of(String eventType, String customerName) {
        boolean eventFound = false;
        for (ApplicationEvent event : testEventListener.getCapturedEvents()) {
            if (event instanceof OrderCreatedEvent orderCreatedEvent && orderCreatedEvent.getOrder().getCustomerName().equals(customerName)) {
                eventFound = true;
                break;
            }
        }
        assertTrue(eventFound, String.format("Expected %s event for customer %s not found.", eventType, customerName));
    }

    @Given("an existing order for {string} with status {string} and items:")
    public void an_existing_order_for_with_status_and_items(String customerName, String status, DataTable dataTable) {
        List<OrderItemRequest> itemRequests = new ArrayList<>();
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            String isbn = row.get("productId");
            int quantity = Integer.parseInt(row.get("quantity"));
            itemRequests.add(new OrderItemRequest(isbn, quantity));
        }

        // Create books and inventory items if they don't exist
        for (OrderItemRequest itemRequest : itemRequests) {
            if (bookRepository.findById(itemRequest.isbn()).isEmpty()) {
                bookRepository.save(new Book(itemRequest.isbn(), "Test Book", "Test Author", BigDecimal.TEN));
            }
            if (inventoryItemRepository.findById(itemRequest.isbn()).isEmpty()) {
                inventoryItemRepository.save(new InventoryItem(itemRequest.isbn(), 100)); // Default large stock
            }
        }

        // Create the order directly in the desired state
        currentOrder = new Order(customerName, itemRequests.stream()
                .map(req -> new OrderLine(req.isbn(), req.quantity(), BigDecimal.TEN))
                .collect(java.util.ArrayList::new, java.util.ArrayList::add, java.util.ArrayList::addAll));
        orderRepository.save(currentOrder);

        // Set initial status if not PENDING
        if (!"PENDING".equals(status)) {
            if ("CONFIRMED".equals(status)) {
                // Simulate stock deduction for confirmed orders
                for (OrderItemRequest itemRequest : itemRequests) {
                    inventoryService.deductStock(itemRequest.isbn(), itemRequest.quantity());
                }
                currentOrder.confirm();
            }
            // Add other status transitions here if needed
            orderRepository.save(currentOrder);
        }
    }

    @Given("an order for {string} with the following items:")
    public void an_order_for_with_the_following_items(String customerName, DataTable dataTable) {
        // This step implies the order is created and confirmed, so stock is deducted.
        // Reusing the existing step with status "CONFIRMED"
        an_existing_order_for_with_status_and_items(customerName, "CONFIRMED", dataTable);
    }

    @Then("the order should transition to status {string}")
    public void the_order_should_transition_to_status(String expectedStatus) {
        assertNotNull(currentOrder, "Current order should not be null for status transition verification.");
        await().atMost(5, SECONDS).untilAsserted(() -> {
            Order updatedOrder = orderService.findOrderById(currentOrder.getOrderId())
                    .orElseThrow(() -> new AssertionError("Order not found"));
            assertEquals(Order.OrderStatus.valueOf(expectedStatus), updatedOrder.getStatus(), "Order status should be updated as expected.");
        });
    }

    @Given("the stock for product {string} is {int}")
    public void the_stock_for_product_is(String isbn, Integer stock) {
        inventoryItemRepository.findById(isbn).ifPresent(inventoryItemRepository::delete);
        InventoryItem newItem = new InventoryItem(isbn, stock);
        inventoryItemRepository.save(newItem);
    }

    @When("the external system confirms the order")
    public void the_external_system_confirms_the_order() {
        assertNotNull(currentOrder, "Current order should not be null for confirmation.");
        orderService.confirmOrder(currentOrder.getOrderId());
    }

    @When("I cancel the order")
    public void i_cancel_the_order() {
        assertNotNull(currentOrder, "Current order should not be null for cancellation.");
        orderService.cancelOrder(currentOrder.getOrderId());
    }

    @Then("the order should have status {string}")
    public void the_order_should_have_status(String expectedStatus) {
        assertNotNull(currentOrder, "Current order should not be null for status verification.");
        await().atMost(5, SECONDS).untilAsserted(() -> {
            Order updatedOrder = orderRepository.findById(currentOrder.getOrderId())
                    .orElseThrow(() -> new AssertionError("Order not found"));
            assertEquals(Order.OrderStatus.valueOf(expectedStatus), updatedOrder.getStatus(), "Order status should be updated as expected.");
        });
    }

    @Then("an {string} event should have been published for the order")
    public void an_event_should_have_been_published_for_the_order(String eventType) {
        await().atMost(5, SECONDS).untilAsserted(() -> {
            boolean eventFound = testEventListener.getCapturedEvents().stream()
                    .anyMatch(event -> event instanceof OrderCancelledEvent
                            && ((OrderCancelledEvent) event).getOrder().getOrderId().equals(currentOrder.getOrderId()));
            assertTrue(eventFound, String.format("Expected %s event for order %s not found.", eventType, currentOrder.getOrderId()));
        });
    }

    @Then("the inventory service receives the stock release request for the order")
    public void the_inventory_service_receives_the_stock_release_request_for_the_order() {
        // Verify that releaseStock was called for each item in the order
        for (OrderLine orderLine : currentOrder.getOrderLines()) {
            verify(inventoryService, times(1)).releaseStock(orderLine.getIsbn(), orderLine.getQuantity());
        }
    }

    @Then("the stock for product {string} should be {int}")
    public void the_stock_for_product_should_be(String isbn, Integer expectedStock) {
        await().atMost(5, SECONDS).untilAsserted(() -> {
            InventoryItem inventoryItem = inventoryItemRepository.findById(isbn)
                    .orElseThrow(() -> new AssertionError("Inventory item not found"));
            assertEquals(expectedStock, inventoryItem.getStock(), "Stock level should be updated as expected.");
        });
    }

    @Then("a {string} event should have been published for product {string} with stock {int}")
    public void a_event_should_have_been_published_for_product_with_stock(String eventType, String isbn, Integer stock) {
        await().atMost(5, SECONDS).untilAsserted(() -> {
            boolean eventFound = testEventListener.getCapturedEvents().stream()
                    .anyMatch(event -> event instanceof ProductStockLowEvent
                            && ((ProductStockLowEvent) event).getIsbn().equals(isbn)
                            && ((ProductStockLowEvent) event).getCurrentStock() == stock);
            assertTrue(eventFound, String.format("Expected %s event for product %s with stock %d not found.", eventType, isbn, stock));
        });
    }
}


