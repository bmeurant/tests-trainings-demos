package io.bmeurant.bookordermanager.integration.steps;

import io.bmeurant.bookordermanager.application.dto.OrderItemRequest;
import io.bmeurant.bookordermanager.application.service.OrderService;
import io.bmeurant.bookordermanager.catalog.domain.model.Book;
import io.bmeurant.bookordermanager.catalog.domain.repository.BookRepository;
import io.bmeurant.bookordermanager.integration.events.TestEventListener;
import io.bmeurant.bookordermanager.inventory.domain.model.InventoryItem;
import io.bmeurant.bookordermanager.inventory.domain.repository.InventoryItemRepository;
import io.bmeurant.bookordermanager.order.domain.event.OrderCreatedEvent;
import io.bmeurant.bookordermanager.order.domain.model.Order;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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
    private TestEventListener testEventListener;

    private Order currentOrder;

    @Before
    public void setup() {
        currentOrder = null;
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
        currentOrder = orderService.createOrder(customerName, itemRequests);
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
            if (event instanceof OrderCreatedEvent orderCreatedEvent) {
                if (orderCreatedEvent.getOrder().getCustomerName().equals(customerName)) {
                    eventFound = true;
                    break;
                }
            }
        }
        assertTrue(eventFound, String.format("Expected %s event for customer %s not found.", eventType, customerName));
    }

    @Then("the inventory service receives the stock deduction request for the order")
    public void the_inventory_service_receives_the_stock_deduction_request_for_the_order() {
        // TODO: Implement stock deduction request verification
        throw new io.cucumber.java.PendingException();
    }

    @Then("the order should transition to status {string}")
    public void the_order_should_transition_to_status(String string) {
        // TODO: Implement order status transition verification
        throw new io.cucumber.java.PendingException();
    }

    @Then("the stock for product {string} should be {int}")
    public void the_stock_for_product_should_be(String string, Integer int1) {
        // TODO: Implement stock level verification
        throw new io.cucumber.java.PendingException();
    }

    @Then("a {string} event should have been published for product {string} with stock {int}")
    public void a_event_should_have_been_published_for_product_with_stock(String string, String string2, Integer int1) {
        // TODO: Implement product stock low event verification
        throw new io.cucumber.java.PendingException();
    }
}

