package io.bmeurant.bookordermanager.catalog.domain.model;

import io.bmeurant.bookordermanager.domain.exception.ValidationException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

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
     * @param price  The price of the book. Must not be null and must be non-negative.
     * @throws ValidationException if any validation fails.
     */
    public Book(String isbn, String title, String author, BigDecimal price) {
        log.debug("Creating Book with ISBN: {}, Title: {}, Author: {}, Price: {}", isbn, title, author, price);
        if (isbn == null || isbn.isBlank()) {
            throw new ValidationException("ISBN cannot be null or blank", Book.class);
        }
        if (title == null || title.isBlank()) {
            throw new ValidationException("Title cannot be null or blank", Book.class);
        }
        if (author == null || author.isBlank()) {
            throw new ValidationException("Author cannot be null or blank", Book.class);
        }
        if (price == null) {
            throw new ValidationException("Price cannot be null", Book.class);
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Price cannot be negative", Book.class);
        }

        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        log.info("Book created: {}", this);
    }

    /**
     * Updates the title of the book.
     *
     * @param newTitle The new title for the book. Must not be null or blank.
     * @throws ValidationException if the new title is null or blank.
     */
    public void updateTitle(String newTitle) {
        log.debug("Updating title for Book {}. New title: {}", this.isbn, newTitle);
        if (newTitle == null || newTitle.isBlank()) {
            throw new ValidationException("New title cannot be null or blank", Book.class);
        }
        this.title = newTitle;
        log.info("Book {} title updated to: {}", this.isbn, newTitle);
    }
}


