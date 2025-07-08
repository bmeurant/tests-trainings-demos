package io.bmeurant.bookordermanager.integration.steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bmeurant.bookordermanager.application.dto.CreateOrderRequest;
import io.bmeurant.bookordermanager.application.dto.OrderItemRequest;
import io.bmeurant.bookordermanager.application.dto.OrderResponse;
import io.bmeurant.bookordermanager.application.service.OrderService;
import io.bmeurant.bookordermanager.catalog.domain.model.Book;
import io.bmeurant.bookordermanager.catalog.domain.repository.BookRepository;
import io.bmeurant.bookordermanager.integration.events.TestEventListener;
import io.bmeurant.bookordermanager.inventory.domain.event.ProductStockLowEvent;
import io.bmeurant.bookordermanager.inventory.domain.model.InventoryItem;
import io.bmeurant.bookordermanager.inventory.domain.repository.InventoryItemRepository;
import io.bmeurant.bookordermanager.inventory.domain.service.InventoryService;
import io.bmeurant.bookordermanager.order.domain.event.OrderCancelledEvent;
import io.bmeurant.bookordermanager.order.domain.event.OrderCreatedEvent;
import io.bmeurant.bookordermanager.order.domain.model.Order;
import io.bmeurant.bookordermanager.order.domain.model.OrderLine;
import io.bmeurant.bookordermanager.order.domain.repository.OrderRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class OrderManagementSteps {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private InventoryItemRepository inventoryItemRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InventoryService mockedInventoryService;
    @Autowired
    private TestEventListener testEventListener;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private Order currentOrder; // Keep for steps not related to API
    private ResponseEntity<String> lastResponse;
    private OrderResponse lastSuccessfulOrder;
    private OrderResponse retrievedOrder;
    private List<OrderResponse> retrievedOrderList;
    private String errorMessage;


    @Before
    public void setup() {
        currentOrder = null;
        lastResponse = null;
        lastSuccessfulOrder = null;
        retrievedOrder = null;
        retrievedOrderList = null;
        errorMessage = null;
        testEventListener.clearEvents();
    }


    @Given("an inventory item {string} with initial stock of {int}")
    public void an_inventory_item_with_initial_stock_of(String isbn, Integer stock) {
        InventoryItem item = new InventoryItem(isbn, stock);
        inventoryItemRepository.save(item);
    }

    @When("I try to create an order for {string} with the following items:")
    public void i_try_to_create_an_order_for_with_the_following_items(String customerName, DataTable dataTable) throws IOException {
        List<OrderItemRequest> itemRequests = new ArrayList<>();
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            String isbn = row.get("productId");
            int quantity = Integer.parseInt(row.get("quantity"));
            itemRequests.add(new OrderItemRequest(isbn, quantity));
        }
        CreateOrderRequest createOrderRequest = new CreateOrderRequest(customerName, itemRequests);

        lastResponse = testRestTemplate.postForEntity("/api/orders", createOrderRequest, String.class);

        if (lastResponse.getStatusCode() == HttpStatus.CREATED) {
            lastSuccessfulOrder = objectMapper.readValue(lastResponse.getBody(), OrderResponse.class);
        }
    }

    @Then("the order creation should fail with message {string}")
    public void the_order_creation_should_fail_with_message(String expectedMessage) {
        assertNotNull(lastResponse, "No response was received from the API.");
        assertTrue(lastResponse.getStatusCode().isError(), "HTTP status should indicate an error.");
        assertNull(lastSuccessfulOrder, "Order should not be created on failure.");

        String responseBody = lastResponse.getBody();
        assertNotNull(responseBody);

        assertTrue(responseBody.contains(expectedMessage),
                "Error response body should contain the expected message. Actual: " + responseBody);
    }

    @Then("the order creation should fail with status {int} with message {string}")
    public void the_order_creation_should_fail_with_status_with_message(int expectedStatus, String expectedMessage) {
        assertNotNull(lastResponse, "No response was received from the API.");
        assertEquals(expectedStatus, lastResponse.getStatusCode().value(), "HTTP status should match expected status.");
        assertNull(lastSuccessfulOrder, "Order should not be created on failure.");

        String responseBody = lastResponse.getBody();
        assertNotNull(responseBody);

        assertTrue(responseBody.contains(expectedMessage),
                "Error response body should contain the expected message. Actual: " + responseBody);
    }


    @Then("the order should be created successfully with status {string}")
    public void the_order_should_be_created_successfully_with_status(String expectedStatus) {
        assertNotNull(lastResponse, "No response was received from the API.");
        assertEquals(HttpStatus.CREATED, lastResponse.getStatusCode(), "HTTP status should be 201 Created.");
        assertNotNull(lastSuccessfulOrder, "The order response body should not be null.");
        assertEquals(expectedStatus, lastSuccessfulOrder.status(), "Order status in the response should match.");
        assertNotNull(lastSuccessfulOrder.orderId(), "The order ID in the response should not be null.");
        assertTrue(Objects.requireNonNull(lastResponse.getHeaders().getLocation()).toString().endsWith("/api/orders/" + lastSuccessfulOrder.orderId()));
    }

    @Then("an {string} event should have been published for the order of {string}")
    public void an_event_should_have_been_published_for_the_order_of(String eventType, String customerName) {
        assertNotNull(lastSuccessfulOrder, "Cannot verify event for a failed order creation.");
        await().atMost(5, SECONDS).untilAsserted(() -> {
            boolean eventFound = testEventListener.getCapturedEvents().stream()
                    .filter(OrderCreatedEvent.class::isInstance)
                    .map(OrderCreatedEvent.class::cast)
                    .anyMatch(orderCreatedEvent ->
                            orderCreatedEvent.getOrder().getOrderId().equals(lastSuccessfulOrder.orderId()) &&
                                    orderCreatedEvent.getOrder().getCustomerName().equals(customerName));
            assertTrue(eventFound, String.format("Expected %s event for customer %s not found.", eventType, customerName));
        });
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
                    mockedInventoryService.deductStock(itemRequest.isbn(), itemRequest.quantity());
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

    @When("I view the order with ID of the last created order")
    public void i_view_the_order_with_id_of_the_last_created_order() throws IOException {
        assertNotNull(lastSuccessfulOrder, "No order was successfully created to view.");
        lastResponse = testRestTemplate.getForEntity("/api/orders/" + lastSuccessfulOrder.orderId(), String.class);
        if (lastResponse.getStatusCode() == HttpStatus.OK) {
            retrievedOrder = objectMapper.readValue(lastResponse.getBody(), OrderResponse.class);
        }
    }

    @When("I view the order with ID {string}")
    public void i_view_the_order_with_id(String orderId) throws IOException {
        lastResponse = testRestTemplate.getForEntity("/api/orders/" + orderId, String.class);
        if (lastResponse.getStatusCode() == HttpStatus.OK) {
            retrievedOrder = objectMapper.readValue(lastResponse.getBody(), OrderResponse.class);
        }
    }

    @Then("the retrieved order should have customer name {string} and status {string}")
    public void the_retrieved_order_should_have_customer_name_and_status(String customerName, String status) {
        assertNotNull(retrievedOrder, "No order was retrieved.");
        assertEquals(customerName, retrievedOrder.customerName(), "Customer name should match.");
        assertEquals(status, retrievedOrder.status(), "Order status should match.");
    }

    @Then("it should contain the following items:")
    public void it_should_contain_the_following_items(DataTable dataTable) {
        assertNotNull(retrievedOrder, "No order was retrieved to check items.");
        List<OrderItemRequest> expectedItems = new ArrayList<>();
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            String isbn = row.get("productId");
            int quantity = Integer.parseInt(row.get("quantity"));
            expectedItems.add(new OrderItemRequest(isbn, quantity));
        }

        List<io.bmeurant.bookordermanager.application.dto.OrderLineResponse> actualItems = retrievedOrder.orderLines();

        assertNotNull(actualItems, "Retrieved order should have order lines.");
        assertEquals(expectedItems.size(), actualItems.size(), "Number of items should match.");

        // Sort both lists for reliable comparison
        expectedItems.sort(Comparator.comparing(OrderItemRequest::isbn));
        actualItems.sort(Comparator.comparing(io.bmeurant.bookordermanager.application.dto.OrderLineResponse::isbn));

        for (int i = 0; i < expectedItems.size(); i++) {
            OrderItemRequest expected = expectedItems.get(i);
            io.bmeurant.bookordermanager.application.dto.OrderLineResponse actual = actualItems.get(i);
            assertEquals(expected.isbn(), actual.isbn(), "ISBN should match for item " + i);
            assertEquals(expected.quantity(), actual.quantity(), "Quantity should match for item " + i);
        }
    }

    @Then("the order retrieval should fail with status {int}")
    public void the_order_retrieval_should_fail_with_status(int expectedStatus) {
        assertNotNull(lastResponse, "No response was received from the API.");
        assertEquals(expectedStatus, lastResponse.getStatusCode().value(), "HTTP status should match expected failure status.");
        assertNull(retrievedOrder, "No order should be retrieved on failure.");
    }

    @Then("the order should transition to status {string}")
    public void the_order_should_transition_to_status(String expectedStatus) {
        assertNotNull(currentOrder, "Current order should not be null for status transition verification.");
        await().atMost(5, SECONDS).untilAsserted(() -> {
            OrderResponse updatedOrderResponse = orderService.getOrderById(currentOrder.getOrderId());
            assertEquals(Order.OrderStatus.valueOf(expectedStatus), Order.OrderStatus.valueOf(updatedOrderResponse.status()), "Order status should be updated as expected.");
        });
    }

    @Given("the stock for product {string} is {int}")
    public void the_stock_for_product_is(String isbn, Integer stock) {
        inventoryItemRepository.findById(isbn).ifPresent(inventoryItemRepository::delete);
        InventoryItem newItem = new InventoryItem(isbn, stock);
        inventoryItemRepository.save(newItem);
    }

    @When("the external system confirms the order")
    public void the_external_system_confirms_the_order() throws IOException {
        assertNotNull(currentOrder, "Current order should not be null for confirmation.");
        lastResponse = testRestTemplate.postForEntity("/api/orders/" + currentOrder.getOrderId() + "/confirm", null, String.class);
        if (lastResponse.getStatusCode() == HttpStatus.OK) {
            retrievedOrder = objectMapper.readValue(lastResponse.getBody(), OrderResponse.class);
        }
    }

    @When("I try to confirm the order")
    public void i_try_to_confirm_the_order() {
        assertNotNull(currentOrder, "Current order should not be null for confirmation attempt.");
        try {
            orderService.confirmOrder(currentOrder.getOrderId());
            fail("Expected confirmation to fail, but it succeeded.");
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("the confirmation should fail with message {string}")
    public void the_confirmation_should_fail_with_message(String expectedMessage) {
        assertNotNull(errorMessage, "No error message was captured.");
        assertTrue(errorMessage.contains(expectedMessage),
                "Error message should contain: '" + expectedMessage + "'. Actual: '" + errorMessage + "'");
    }

    @When("I cancel the order")
    public void i_cancel_the_order() throws IOException {
        assertNotNull(currentOrder, "Current order should not be null for cancellation.");
        lastResponse = testRestTemplate.postForEntity("/api/orders/" + currentOrder.getOrderId() + "/cancel", null, String.class);
        if (lastResponse.getStatusCode() == HttpStatus.OK) {
            retrievedOrder = objectMapper.readValue(lastResponse.getBody(), OrderResponse.class);
        }
    }

    @Then("the order should have status {string}")
    public void the_order_should_have_status(String expectedStatus) {
        assertNotNull(currentOrder, "Current order should not be null for status verification.");
        await().atMost(5, SECONDS).untilAsserted(() -> {
            OrderResponse updatedOrder = orderService.getOrderById(currentOrder.getOrderId());
            assertEquals(Order.OrderStatus.valueOf(expectedStatus), Order.OrderStatus.valueOf(updatedOrder.status()), "Order status should be updated as expected.");
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
            verify(mockedInventoryService, times(1)).releaseStock(orderLine.getIsbn(), orderLine.getQuantity());
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

    @Given("an order exists for {string} with items:")
    public void an_order_exists_for_with_items(String customerName, DataTable dataTable) {
        an_existing_order_for_with_status_and_items(customerName, "PENDING", dataTable);
    }

    @When("I request the list of all orders")
    public void i_request_the_list_of_all_orders() throws IOException {
        lastResponse = testRestTemplate.getForEntity("/api/orders", String.class);
        if (lastResponse.getStatusCode() == HttpStatus.OK) {
            retrievedOrderList = objectMapper.readValue(lastResponse.getBody(), new TypeReference<List<OrderResponse>>() {});
        }
    }

    @Then("the response should contain {int} orders")
    public void the_response_should_contain_orders(int count) {
        assertNotNull(retrievedOrderList, "The retrieved order list should not be null.");
        assertEquals(count, retrievedOrderList.size(), "The number of orders should match.");
    }

    @Then("one order should be for {string}")
    public void one_order_should_be_for(String customerName) {
        assertNotNull(retrievedOrderList, "The retrieved order list should not be null.");
        assertTrue(retrievedOrderList.stream().anyMatch(o -> o.customerName().equals(customerName)),
                "An order for customer " + customerName + " should exist in the list.");
    }
}
