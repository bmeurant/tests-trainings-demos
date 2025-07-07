package io.bmeurant.bookordermanager.unit.domain.model;

import io.bmeurant.bookordermanager.catalog.domain.model.Book;
import io.bmeurant.bookordermanager.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

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
        ValidationException exception = assertThrows(ValidationException.class, () -> new Book(null, "Title", "Author", new BigDecimal("10.00")), "Should throw ValidationException when ISBN is null.");
        assertTrue(exception.getMessage().contains("ISBN cannot be null or blank"), "Exception message should indicate null ISBN.");
        assertEquals(Book.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be Book.");
    }

    @Test
    void shouldThrowExceptionWhenIsbnIsBlank() {
        ValidationException exception = assertThrows(ValidationException.class, () -> new Book("", "Title", "Author", new BigDecimal("10.00")), "Should throw ValidationException when ISBN is blank.");
        assertTrue(exception.getMessage().contains("ISBN cannot be null or blank"), "Exception message should indicate blank ISBN.");
        assertEquals(Book.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be Book.");
    }

    @Test
    void shouldThrowExceptionWhenTitleIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () -> new Book("123-456", null, "Author", new BigDecimal("10.00")), "Should throw ValidationException when title is null.");
        assertTrue(exception.getMessage().contains("Title cannot be null or blank"), "Exception message should indicate null title.");
        assertEquals(Book.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be Book.");
    }

    @Test
    void shouldThrowExceptionWhenTitleIsBlank() {
        ValidationException exception = assertThrows(ValidationException.class, () -> new Book("123-456", "", "Author", new BigDecimal("10.00")), "Should throw ValidationException when title is blank.");
        assertTrue(exception.getMessage().contains("Title cannot be null or blank"), "Exception message should indicate blank title.");
        assertEquals(Book.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be Book.");
    }

    @Test
    void shouldThrowExceptionWhenAuthorIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () -> new Book("123-456", "Title", null, new BigDecimal("10.00")), "Should throw ValidationException when author is null.");
        assertTrue(exception.getMessage().contains("Author cannot be null or blank"), "Exception message should indicate null author.");
        assertEquals(Book.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be Book.");
    }

    @Test
    void shouldThrowExceptionWhenAuthorIsBlank() {
        ValidationException exception = assertThrows(ValidationException.class, () -> new Book("123-456", "Title", "", new BigDecimal("10.00")), "Should throw ValidationException when author is blank.");
        assertTrue(exception.getMessage().contains("Author cannot be null or blank"), "Exception message should indicate blank author.");
        assertEquals(Book.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be Book.");
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () -> new Book("123-456", "Title", "Author", null), "Should throw ValidationException when price is null.");
        assertTrue(exception.getMessage().contains("Price cannot be null"), "Exception message should indicate null price.");
        assertEquals(Book.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be Book.");
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        ValidationException exception = assertThrows(ValidationException.class, () -> new Book("123-456", "Title", "Author", new BigDecimal("-0.01")), "Should throw ValidationException when price is negative.");
        assertTrue(exception.getMessage().contains("Price cannot be negative"), "Exception message should indicate negative price.");
        assertEquals(Book.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be Book.");
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
        ValidationException exception = assertThrows(ValidationException.class, () -> book.updateTitle(null), "Should throw ValidationException when updating title with null.");
        assertTrue(exception.getMessage().contains("New title cannot be null or blank"), "Exception message should indicate null title.");
        assertEquals(Book.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be Book.");
    }

    @Test
    void shouldThrowExceptionWhenUpdatingTitleWithBlank() {
        Book book = new Book("978-0321765723", "Old Title", "Author", new BigDecimal("25.00"));
        ValidationException exception = assertThrows(ValidationException.class, () -> book.updateTitle(""), "Should throw ValidationException when updating title with blank.");
        assertTrue(exception.getMessage().contains("New title cannot be null or blank"), "Exception message should indicate blank title.");
        assertEquals(Book.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be Book.");
    }
}
