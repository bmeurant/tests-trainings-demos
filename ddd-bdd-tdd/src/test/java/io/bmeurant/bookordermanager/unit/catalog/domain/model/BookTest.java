package io.bmeurant.bookordermanager.unit.catalog.domain.model;

import io.bmeurant.bookordermanager.catalog.domain.model.Book;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @Test
    void shouldCreateBookWithValidParameters() {
        String isbn = "978-0321765723";
        String title = "The Lord of the Rings";
        String author = "J.R.R. Tolkien";
        BigDecimal price = new BigDecimal("25.00");

        Book book = new Book(isbn, title, author, price);

        assertNotNull(book, "Book should not be null.");
        assertEquals(isbn, book.getIsbn(), "ISBN should match the provided value.");
        assertEquals(title, book.getTitle(), "Title should match the provided value.");
        assertEquals(author, book.getAuthor(), "Author should match the provided value.");
        assertEquals(price, book.getPrice(), "Price should match the provided value.");
    }

    @Test
    void shouldThrowExceptionWhenIsbnIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Book(null, "Title", "Author", new BigDecimal("10.00")), "Should throw IllegalArgumentException when ISBN is null.");
        assertTrue(exception.getMessage().contains("ISBN cannot be null or blank"), "Exception message should indicate null ISBN.");
    }

    @Test
    void shouldThrowExceptionWhenIsbnIsBlank() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Book("", "Title", "Author", new BigDecimal("10.00")), "Should throw IllegalArgumentException when ISBN is blank.");
        assertTrue(exception.getMessage().contains("ISBN cannot be null or blank"), "Exception message should indicate blank ISBN.");
    }

    @Test
    void shouldThrowExceptionWhenTitleIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Book("123-456", null, "Author", new BigDecimal("10.00")), "Should throw IllegalArgumentException when title is null.");
        assertTrue(exception.getMessage().contains("Title cannot be null or blank"), "Exception message should indicate null title.");
    }

    @Test
    void shouldThrowExceptionWhenTitleIsBlank() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Book("123-456", "", "Author", new BigDecimal("10.00")), "Should throw IllegalArgumentException when title is blank.");
        assertTrue(exception.getMessage().contains("Title cannot be null or blank"), "Exception message should indicate blank title.");
    }

    @Test
    void shouldThrowExceptionWhenAuthorIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Book("123-456", "Title", null, new BigDecimal("10.00")), "Should throw IllegalArgumentException when author is null.");
        assertTrue(exception.getMessage().contains("Author cannot be null or blank"), "Exception message should indicate null author.");
    }

    @Test
    void shouldThrowExceptionWhenAuthorIsBlank() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Book("123-456", "Title", "", new BigDecimal("10.00")), "Should throw IllegalArgumentException when author is blank.");
        assertTrue(exception.getMessage().contains("Author cannot be null or blank"), "Exception message should indicate blank author.");
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Book("123-456", "Title", "Author", null), "Should throw IllegalArgumentException when price is null.");
        assertTrue(exception.getMessage().contains("Price cannot be null"), "Exception message should indicate null price.");
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Book("123-456", "Title", "Author", new BigDecimal("-0.01")), "Should throw IllegalArgumentException when price is negative.");
        assertTrue(exception.getMessage().contains("Price cannot be negative"), "Exception message should indicate negative price.");
    }

    @Test
    void shouldBeEqualWhenIsbnIsSame() {
        BigDecimal price1 = new BigDecimal("25.00");
        BigDecimal price2 = new BigDecimal("30.00");

        Book book1 = new Book("978-0321765723", "Title A", "Author A", price1);
        Book book2 = new Book("978-0321765723", "Title B", "Author B", price2);

        assertEquals(book1, book2, "Books with the same ISBN should be equal.");
        assertEquals(book1.hashCode(), book2.hashCode(), "Hash codes should be equal for books with the same ISBN.");
    }

    @Test
    void shouldNotBeEqualWhenIsbnIsDifferent() {
        BigDecimal price = new BigDecimal("25.00");

        Book book1 = new Book("978-0321765723", "Title A", "Author A", price);
        Book book2 = new Book("978-0132350884", "Title A", "Author A", price);

        assertNotEquals(book1, book2, "Books with different ISBNs should not be equal.");
        assertNotEquals(book1.hashCode(), book2.hashCode(), "Hash codes should not be equal for books with different ISBNs.");
    }

    @Test
    void shouldBeEqualWithSelf() {
        Book book = new Book("978-0321765723", "Title", "Author", new BigDecimal("25.00"));
        assertEquals(book, book, "A book should be equal to itself.");
        assertEquals(book.hashCode(), book.hashCode(), "Hash code should be consistent for a book.");
    }

    @Test
    void shouldNotBeEqualToNull() {
        Book book = new Book("978-0321765723", "Title", "Author", new BigDecimal("25.00"));
        assertNotEquals(null, book, "A book should not be equal to null.");
    }

    @Test
    void shouldNotBeEqualToDifferentClass() {
        Book book = new Book("978-0321765723", "Title", "Author", new BigDecimal("25.00"));
        assertNotEquals(new Object(), book, "A book should not be equal to an object of a different class.");
    }

    @Test
    void shouldUpdateTitleCorrectly() {
        Book book = new Book("978-0321765723", "Old Title", "Author", new BigDecimal("25.00"));
        String newTitle = "New Title";
        book.updateTitle(newTitle);
        assertEquals(newTitle, book.getTitle(), "Book title should be updated correctly.");
    }

    @Test
    void shouldThrowExceptionWhenUpdatingTitleWithNull() {
        Book book = new Book("978-0321765723", "Old Title", "Author", new BigDecimal("25.00"));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> book.updateTitle(null), "Should throw IllegalArgumentException when updating title with null.");
        assertTrue(exception.getMessage().contains("New title cannot be null or blank"), "Exception message should indicate null title.");
    }

    @Test
    void shouldThrowExceptionWhenUpdatingTitleWithBlank() {
        Book book = new Book("978-0321765723", "Old Title", "Author", new BigDecimal("25.00"));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> book.updateTitle(""), "Should throw IllegalArgumentException when updating title with blank.");
        assertTrue(exception.getMessage().contains("New title cannot be null or blank"), "Exception message should indicate blank title.");
    }
}
