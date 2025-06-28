package io.bmeurant.bookordermanager.unit.catalog.book.domain.model;

import io.bmeurant.bookordermanager.catalog.Book;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BookEqualityTest {

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
