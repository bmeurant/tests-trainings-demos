package io.bmeurant.bookordermanager.integration.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bmeurant.bookordermanager.application.dto.BookResponse;
import io.bmeurant.bookordermanager.catalog.domain.model.Book;
import io.bmeurant.bookordermanager.catalog.domain.repository.BookRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class BookCatalogManagementSteps {


    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookRepository bookRepository;

    private ResponseEntity<String> lastResponse;
    private BookResponse retrievedBook;
    private List<BookResponse> retrievedBookList;

    @Given("a book with ISBN {string}, title {string}, author {string}, price {bigdecimal}")
    public void a_book_with_isbn_title_author_price(String isbn, String title, String author, BigDecimal price) {
        Book book = new Book(isbn, title, author, price);
        bookRepository.save(book);
    }

    @Given("the catalog is empty")
    public void the_catalog_is_empty() {
        bookRepository.deleteAll();
    }

    @When("I retrieve the book with ISBN {string}")
    public void i_retrieve_the_book_with_isbn(String isbn) throws Exception {
        lastResponse = testRestTemplate.getForEntity("/api/books/" + isbn, String.class);
        if (lastResponse.getStatusCode() == HttpStatus.OK) {
            retrievedBook = objectMapper.readValue(lastResponse.getBody(), BookResponse.class);
        }
    }

    @Then("the retrieved book should have ISBN {string}, title {string}, author {string}, and price {bigdecimal}")
    public void the_retrieved_book_should_have_details(String isbn, String title, String author, BigDecimal price) {
        assertNotNull(retrievedBook, "Retrieved book should not be null.");
        assertEquals(isbn, retrievedBook.isbn(), "ISBN should match.");
        assertEquals(title, retrievedBook.title(), "Title should match.");
        assertEquals(author, retrievedBook.author(), "Author should match.");
        assertEquals(price, retrievedBook.price(), "Price should match.");
    }

    @Then("the book retrieval should fail with status {int} and message {string}")
    public void the_book_retrieval_should_fail_with_status_and_message(int expectedStatus, String expectedMessage) {
        assertNotNull(lastResponse, "No response was received from the API.");
        assertEquals(expectedStatus, lastResponse.getStatusCode().value(), "HTTP status should match expected failure status.");
        assertNull(retrievedBook, "No book should be retrieved on failure.");
        assertTrue(Objects.requireNonNull(lastResponse.getBody()).contains(expectedMessage),
                "Error message should contain: '" + expectedMessage + "'. Actual: '" + lastResponse.getBody() + "'");
    }

    @When("I retrieve all books")
    public void i_retrieve_all_books() throws Exception {
        lastResponse = testRestTemplate.getForEntity("/api/books", String.class);
        if (lastResponse.getStatusCode() == HttpStatus.OK) {
            retrievedBookList = Arrays.asList(objectMapper.readValue(lastResponse.getBody(), BookResponse[].class));
        }
    }

    @Then("the retrieved book list should contain {int} books")
    public void the_retrieved_book_list_should_contain_books(int expectedCount) {
        assertNotNull(retrievedBookList, "Retrieved book list should not be null.");
        assertEquals(expectedCount, retrievedBookList.size(), "Number of books should match.");
    }

    @Then("the retrieved book list should contain a book with ISBN {string}")
    public void the_retrieved_book_list_should_contain_a_book_with_isbn(String isbn) {
        assertTrue(retrievedBookList.stream().anyMatch(book -> book.isbn().equals(isbn)),
                "Book with ISBN " + isbn + " should be in the list.");
    }

    @Then("the retrieved book list should be empty")
    public void the_retrieved_book_list_should_be_empty() {
        assertNotNull(retrievedBookList, "Retrieved book list should not be null.");
        assertTrue(retrievedBookList.isEmpty(), "Book list should be empty.");
    }
}
