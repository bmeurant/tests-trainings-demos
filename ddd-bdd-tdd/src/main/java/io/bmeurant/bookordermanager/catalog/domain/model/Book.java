package io.bmeurant.bookordermanager.catalog.domain.model;

import io.bmeurant.bookordermanager.domain.exception.ValidationException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static io.bmeurant.bookordermanager.domain.util.Assertions.*;

/**
 * Represents a book in the catalog domain. A book is identified by its ISBN.
 * This is a value object in the DDD context, but acts as an aggregate root for its own properties.
 */
@Entity
@Getter
@EqualsAndHashCode(of = "isbn")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {
    private static final Logger log = LoggerFactory.getLogger(Book.class);

    @Id
    private String isbn;
    private String title;
    private String author;
    private BigDecimal price;
    @Version
    private Long version;

    /**
     * Constructs a new Book instance.
     * All parameters are validated to ensure the book is created in a valid state.
     *
     * @param isbn   The International Standard Book Number, a unique identifier for the book. Must not be null or blank.
     * @param title  The title of the book. Must not be null or blank.
     * @param author The author of the book. Must not be null or blank.
     * @param price  The price of the book at the time of order. Must not be null and must be non-negative.
     * @throws ValidationException if any validation fails.
     */
    public Book(String isbn, String title, String author, BigDecimal price) {
        log.debug("Creating Book with ISBN: {}, Title: {}, Author: {}, Price: {}", isbn, title, author, price);
        assertBookIsValid(isbn, title, author, price);

        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        log.info("Book created: {}", this);
    }

    private static void assertBookIsValid(String isbn, String title, String author, BigDecimal price) {
        assertHasText(isbn, "ISBN", Book.class);
        assertHasText(title, "Title", Book.class);
        assertHasText(author, "Author", Book.class);
        assertNotNull(price, "Price", Book.class);
        assertIsNonNegative(price, "Price", Book.class);
    }

    /**
     * Updates the title of the book.
     *
     * @param newTitle The new title for the book. Must not be null or blank.
     * @throws ValidationException if the new title is null or blank.
     */
    public void updateTitle(String newTitle) {
        log.debug("Updating title for Book {}. New title: {}", this.isbn, newTitle);
        assertTitleIsValid(newTitle);
        this.title = newTitle;
        log.info("Book {} title updated to: {}", this.isbn, newTitle);
    }

    private static void assertTitleIsValid(String newTitle) {
        assertHasText(newTitle, "New title", Book.class);
    }
}


