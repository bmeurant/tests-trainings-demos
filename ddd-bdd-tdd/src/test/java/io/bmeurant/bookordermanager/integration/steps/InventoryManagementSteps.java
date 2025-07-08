package io.bmeurant.bookordermanager.integration.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bmeurant.bookordermanager.inventory.domain.model.InventoryItem;
import io.bmeurant.bookordermanager.inventory.domain.repository.InventoryItemRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class InventoryManagementSteps {

    @Autowired
    private InventoryItemRepository inventoryItemRepository;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private ResponseEntity<String> lastResponse;
    private Integer retrievedStock;

    @Before
    public void setup() {
        lastResponse = null;
        retrievedStock = null;
    }

    @Given("an inventory item {string} with initial stock of {int}")
    public void an_inventory_item_with_initial_stock_of(String isbn, Integer stock) {
        InventoryItem item = new InventoryItem(isbn, stock);
        inventoryItemRepository.save(item);
    }

    @Given("the stock for product {string} is {int}")
    public void the_stock_for_product_is(String isbn, Integer stock) {
        inventoryItemRepository.findById(isbn).ifPresent(inventoryItemRepository::delete);
        InventoryItem newItem = new InventoryItem(isbn, stock);
        inventoryItemRepository.save(newItem);
    }

    @When("I request the stock for ISBN {string}")
    public void i_request_the_stock_for_isbn(String isbn) throws IOException {
        lastResponse = testRestTemplate.getForEntity("/api/inventory/{isbn}", String.class, isbn);
        if (lastResponse.getStatusCode() == HttpStatus.OK) {
            retrievedStock = objectMapper.readTree(lastResponse.getBody()).get("stock").asInt();
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

    @Then("the retrieved stock should be {int}")
    public void the_retrieved_stock_should_be(Integer expectedStock) {
        assertNotNull(retrievedStock, "Stock should have been retrieved.");
        assertEquals(expectedStock, retrievedStock, "Retrieved stock should match expected stock.");
    }

    @Then("the stock retrieval should fail with status {int} and message {string}")
    public void the_stock_retrieval_should_fail_with_status_and_message(int expectedStatus, String expectedMessage) {
        assertNotNull(lastResponse, "No response was received from the API.");
        assertEquals(expectedStatus, lastResponse.getStatusCode().value(), "HTTP status should match expected status.");
        assertNull(retrievedStock, "No stock should be retrieved on failure.");

        String responseBody = lastResponse.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.contains(expectedMessage),
                "Error response body should contain the expected message. Actual: " + responseBody);
    }
}
