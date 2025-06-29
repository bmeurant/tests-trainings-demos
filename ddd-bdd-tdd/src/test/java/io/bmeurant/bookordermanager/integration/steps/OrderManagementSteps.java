package io.bmeurant.bookordermanager.integration.steps;

import static org.junit.jupiter.api.Assertions.*;

import io.bmeurant.bookordermanager.catalog.domain.model.Book;
import io.bmeurant.bookordermanager.catalog.domain.repository.BookRepository;
import io.bmeurant.bookordermanager.inventory.domain.model.InventoryItem;
import io.bmeurant.bookordermanager.inventory.domain.repository.InventoryItemRepository;
import io.bmeurant.bookordermanager.order.domain.model.Order;
import io.bmeurant.bookordermanager.order.domain.model.OrderLine;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cucumber step definitions for managing book orders.
 * This class integrates with the Spring Boot test context.
 */
@CucumberContextConfiguration
@SpringBootTest(classes = io.bmeurant.bookordermanager.integration.TestApplication.class)
public class OrderManagementSteps {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    // Stores the current order being processed in the scenario
    private Order currentOrder;

    /**
     * Initializes the maps before each scenario.
     */
    @Before
    public void setup() {
        currentOrder = null;
    }

    /**
     * Defines a book with the given details and stores it.
     * @param isbn The ISBN of the book.
     * @param title The title of the book.
     * @param author The author of the book.
     * @param price The price of the book.
     */
    @Given("a book with ISBN {string}, title {string}, author {string}, price {bigdecimal}")
    public void a_book_with_isbn_title_author_price(String isbn, String title, String author, BigDecimal price) {
        Book book = new Book(isbn, title, author, price);
        bookRepository.save(book);
    }

    /**
     * Defines an inventory item with the given ISBN and initial stock.
     * @param isbn The ISBN of the book associated with the inventory item.
     * @param stock The initial stock level.
     */
    @Given("an inventory item {string} with initial stock of {int}")
    public void an_inventory_item_with_initial_stock_of(String isbn, Integer stock) {
        InventoryItem item = new InventoryItem(isbn, stock);
        inventoryItemRepository.save(item);
    }

    /**
     * Attempts to create a new order for a given customer with specified items.
     * @param customerName The name of the customer.
     * @param dataTable A DataTable containing product IDs and quantities for the order lines.
     */
    @When("I try to create an order for {string} with the following items:")
    public void i_try_to_create_an_order_for_with_the_following_items(String customerName, DataTable dataTable) {
        List<OrderLine> orderLines = new ArrayList<>();
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            String productId = row.get("productId");
            int quantity = Integer.parseInt(row.get("quantity"));
            Book book = bookRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Book with ISBN " + productId + " not found in catalog."));
            InventoryItem inventoryItem = inventoryItemRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Inventory item with ISBN " + productId + " not found."));
            orderLines.add(new OrderLine(productId, quantity, book.getPrice()));
        }
        currentOrder = new Order(customerName, orderLines);
    }

    @Then("the order should be created successfully with status {string}")
    public void the_order_should_be_created_successfully_with_status(String expectedStatus) {
        assertNotNull(currentOrder, "Order should have been created.");
        assertEquals(Order.OrderStatus.valueOf(expectedStatus), currentOrder.getStatus(), "Order status should match expected status.");
    }

    @Then("an {string} event should have been published for the order of {string}")
    public void an_event_should_have_been_published_for_the_order_of(String string, String string2) {
        // TODO: Implement event publication verification
        throw new io.cucumber.java.PendingException();
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

