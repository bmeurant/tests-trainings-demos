package io.bmeurant.bookordermanager.unit.inventory.domain.model;

import io.bmeurant.bookordermanager.inventory.domain.model.InventoryItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryItemTest {

    @Test
    void shouldCreateInventoryItemWithValidParameters() {
        String isbn = "978-0321765723";
        int stock = 10;

        InventoryItem item = new InventoryItem(isbn, stock);

        assertNotNull(item);
        assertEquals(isbn, item.getIsbn());
        assertEquals(stock, item.getStock());
    }

    @Test
    void shouldThrowExceptionWhenIsbnIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new InventoryItem(null, 10);
        });
        assertTrue(exception.getMessage().contains("ISBN cannot be null or blank"));
    }

    @Test
    void shouldThrowExceptionWhenIsbnIsBlank() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new InventoryItem("", 10);
        });
        assertTrue(exception.getMessage().contains("ISBN cannot be null or blank"));
    }

    @Test
    void shouldThrowExceptionWhenStockIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new InventoryItem("978-0321765723", -1);
        });
        assertTrue(exception.getMessage().contains("Stock cannot be negative"));
    }

    @Test
    void shouldBeEqualWhenIsbnIsSame() {
        InventoryItem item1 = new InventoryItem("978-0321765723", 10);
        InventoryItem item2 = new InventoryItem("978-0321765723", 5);

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenIsbnIsDifferent() {
        InventoryItem item1 = new InventoryItem("978-0321765723", 10);
        InventoryItem item2 = new InventoryItem("978-0132350884", 10);

        assertNotEquals(item1, item2);
        assertNotEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    void shouldDeductStockCorrectly() {
        InventoryItem item = new InventoryItem("978-0321765723", 10);
        item.deductStock(3);
        assertEquals(7, item.getStock());
    }

    @Test
    void shouldThrowExceptionWhenDeductingZeroStock() {
        InventoryItem item = new InventoryItem("978-0321765723", 10);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            item.deductStock(0);
        });
        assertTrue(exception.getMessage().contains("Quantity to deduct must be positive"));
    }

    @Test
    void shouldThrowExceptionWhenDeductingNegativeStock() {
        InventoryItem item = new InventoryItem("978-0321765723", 10);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            item.deductStock(-1);
        });
        assertTrue(exception.getMessage().contains("Quantity to deduct must be positive"));
    }

    @Test
    void shouldThrowExceptionWhenDeductingMoreThanAvailableStock() {
        InventoryItem item = new InventoryItem("978-0321765723", 5);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            item.deductStock(10);
        });
        assertTrue(exception.getMessage().contains("Not enough stock to deduct"));
    }
}
