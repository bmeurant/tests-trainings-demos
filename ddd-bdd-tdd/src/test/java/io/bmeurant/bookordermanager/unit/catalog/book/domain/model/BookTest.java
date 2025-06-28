package io.bmeurant.bookordermanager.unit.catalog.book.domain.model;

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

        assertNotNull(book);
        assertEquals(isbn, book.getIsbn());
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(price, book.getPrice());
    }

    @Test
    void shouldThrowExceptionWhenIsbnIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book(null, "Title", "Author", new BigDecimal("10.00"));
        });
        assertTrue(exception.getMessage().contains("ISBN cannot be null or blank"));
    }

    @Test
    void shouldThrowExceptionWhenIsbnIsBlank() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book("", "Title", "Author", new BigDecimal("10.00"));
        });
        assertTrue(exception.getMessage().contains("ISBN cannot be null or blank"));
    }

    @Test
    void shouldThrowExceptionWhenTitleIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book("123-456", null, "Author", new BigDecimal("10.00"));
        });
        assertTrue(exception.getMessage().contains("Title cannot be null or blank"));
    }

    @Test
    void shouldThrowExceptionWhenTitleIsBlank() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book("123-456", "", "Author", new BigDecimal("10.00"));
        });
        assertTrue(exception.getMessage().contains("Title cannot be null or blank"));
    }

    @Test
    void shouldThrowExceptionWhenAuthorIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book("123-456", "Title", null, new BigDecimal("10.00"));
        });
        assertTrue(exception.getMessage().contains("Author cannot be null or blank"));
    }

    @Test
    void shouldThrowExceptionWhenAuthorIsBlank() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book("123-456", "Title", "", new BigDecimal("10.00"));
        });
        assertTrue(exception.getMessage().contains("Author cannot be null or blank"));
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book("123-456", "Title", "Author", null);
        });
        assertTrue(exception.getMessage().contains("Price cannot be null"));
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book("123-456", "Title", "Author", new BigDecimal("-0.01"));
        });
        assertTrue(exception.getMessage().contains("Price cannot be negative"));
    }

    @Test
    void shouldBeEqualWhenIsbnIsSame() {
        BigDecimal price1 = new BigDecimal("25.00");
        BigDecimal price2 = new BigDecimal("30.00");

        Book book1 = new Book("978-0321765723", "Title A", "Author A", price1);
        Book book2 = new Book("978-0321765723", "Title B", "Author B", price2);

        assertEquals(book1, book2);
        assertEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenIsbnIsDifferent() {
        BigDecimal price = new BigDecimal("25.00");

        Book book1 = new Book("978-0321765723", "Title A", "Author A", price);
        Book book2 = new Book("978-0132350884", "Title A", "Author A", price);

        assertNotEquals(book1, book2);
        assertNotEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    void shouldBeEqualWithSelf() {
        Book book = new Book("978-0321765723", "Title", "Author", new BigDecimal("25.00"));
        assertEquals(book, book);
        assertEquals(book.hashCode(), book.hashCode());
    }

    @Test
    void shouldNotBeEqualToNull() {
        Book book = new Book("978-0321765723", "Title", "Author", new BigDecimal("25.00"));
        assertNotEquals(null, book);
    }

    @Test
    void shouldNotBeEqualToDifferentClass() {
        Book book = new Book("978-0321765723", "Title", "Author", new BigDecimal("25.00"));
        assertNotEquals(book, new Object());
    }
}
