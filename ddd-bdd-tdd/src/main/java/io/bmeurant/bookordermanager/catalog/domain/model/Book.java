package io.bmeurant.bookordermanager.catalog.domain.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import jakarta.persistence.*;
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
     * @param isbn The International Standard Book Number, a unique identifier for the book. Must not be null or blank.
     * @param title The title of the book. Must not be null or blank.
     * @param author The author of the book. Must not be null or blank.
     * @param price The price of the book. Must not be null and must be non-negative.
     * @throws IllegalArgumentException if any validation fails.
     */
    public Book(String isbn, String title, String author, BigDecimal price) {
        Assert.hasText(isbn, "ISBN cannot be null or blank");
        Assert.hasText(title, "Title cannot be null or blank");
        Assert.hasText(author, "Author cannot be null or blank");
        Assert.notNull(price, "Price cannot be null");
        Assert.isTrue(price.compareTo(BigDecimal.ZERO) >= 0, "Price cannot be negative");

        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    /**
     * Updates the title of the book.
     * @param newTitle The new title for the book. Must not be null or blank.
     * @throws IllegalArgumentException if the new title is null or blank.
     */
    public void updateTitle(String newTitle) {
        Assert.hasText(newTitle, "New title cannot be null or blank");
        this.title = newTitle;
    }
}

