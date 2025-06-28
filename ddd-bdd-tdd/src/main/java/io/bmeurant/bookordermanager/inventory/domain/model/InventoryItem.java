package io.bmeurant.bookordermanager.inventory.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

@Getter
@EqualsAndHashCode(of = "isbn")
@ToString
public class InventoryItem {
    private String isbn;
    private int stock;

    public InventoryItem(String isbn, int stock) {
        Assert.hasText(isbn, "ISBN cannot be null or blank");
        Assert.isTrue(stock >= 0, "Stock cannot be negative");

        this.isbn = isbn;
        this.stock = stock;
    }
}
