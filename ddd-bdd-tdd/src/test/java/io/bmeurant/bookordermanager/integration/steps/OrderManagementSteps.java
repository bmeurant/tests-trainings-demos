package io.bmeurant.bookordermanager.integration.steps;

import io.bmeurant.bookordermanager.catalog.Book;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@CucumberContextConfiguration
@SpringBootTest(classes = io.bmeurant.bookordermanager.integration.TestApplication.class)
public class OrderManagementSteps {

    @Given("a book with ISBN {string}, title {string}, author {string}, price {bigdecimal}")
    public void a_book_with_isbn_title_author_price(String isbn, String title, String author, BigDecimal price) {
        Book book = new Book(isbn, title, author, price);
    }

    @Given("an inventory item {string} with initial stock of {int}")
    public void an_inventory_item_with_initial_stock_of(String string, Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("I try to create an order for {string} with the following items:")
    public void i_try_to_create_an_order_for_with_the_following_items(String string, io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        throw new io.cucumber.java.PendingException();
    }

    @Then("the order should be created successfully with status {string}")
    public void the_order_should_be_created_successfully_with_status(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("an {string} event should have been published for the order of {string}")
    public void an_event_should_have_been_published_for_the_order_of(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the inventory service receives the stock deduction request for the order")
    public void the_inventory_service_receives_the_stock_deduction_request_for_the_order() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the order should transition to status {string}")
    public void the_order_should_transition_to_status(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the stock for product {string} should be {int}")
    public void the_stock_for_product_should_be(String string, Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("a {string} event should have been published for product {string} with stock {int}")
    public void a_event_should_have_been_published_for_product_with_stock(String string, String string2, Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
