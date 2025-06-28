package io.bmeurant.bookordermanager.catalog.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode(of = "isbn")
@ToString
public class Book {
    private String isbn;
    private String title;
    private String author;
    private BigDecimal price;

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
}
