package io.bmeurant.bookordermanager.unit.inventory.domain.model;

import io.bmeurant.bookordermanager.domain.exception.ValidationException;
import io.bmeurant.bookordermanager.inventory.domain.exception.InsufficientStockException;
import io.bmeurant.bookordermanager.inventory.domain.model.InventoryItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryItemTest {

    @Test
    void shouldCreateInventoryItemWithValidParameters() {
        String isbn = "978-0321765723";
        int stock = 10;

        InventoryItem item = new InventoryItem(isbn, stock);

        assertNotNull(item, "InventoryItem should not be null.");
        assertEquals(isbn, item.getIsbn(), "ISBN should match the provided value.");
        assertEquals(stock, item.getStock(), "Stock should match the provided value.");
    }

    @Test
    void shouldThrowExceptionWhenIsbnIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () -> new InventoryItem(null, 10), "Should throw ValidationException when ISBN is null.");
        assertTrue(exception.getMessage().contains("ISBN cannot be null or blank"), "Exception message should indicate null ISBN.");
        assertEquals(InventoryItem.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be InventoryItem.");
    }

    @Test
    void shouldThrowExceptionWhenIsbnIsBlank() {
        ValidationException exception = assertThrows(ValidationException.class, () -> new InventoryItem("", 10), "Should throw ValidationException when ISBN is blank.");
        assertTrue(exception.getMessage().contains("ISBN cannot be null or blank"), "Exception message should indicate blank ISBN.");
        assertEquals(InventoryItem.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be InventoryItem.");
    }

    @Test
    void shouldThrowExceptionWhenStockIsNegative() {
        ValidationException exception = assertThrows(ValidationException.class, () -> new InventoryItem("978-0321765723", -1), "Should throw ValidationException when stock is negative.");
        assertTrue(exception.getMessage().contains("Stock cannot be negative"), "Exception message should indicate negative stock.");
        assertEquals(InventoryItem.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be InventoryItem.");
    }

    @Test
    void shouldBeEqualWhenIsbnIsSame() {
        InventoryItem item1 = new InventoryItem("978-0321765723", 10);
        InventoryItem item2 = new InventoryItem("978-0321765723", 5);

        assertEquals(item1, item2, "Inventory items with same ISBN should be equal.");
        assertEquals(item1.hashCode(), item2.hashCode(), "Hash codes should be equal for items with same ISBN.");
    }

    @Test
    void shouldNotBeEqualWhenIsbnIsDifferent() {
        InventoryItem item1 = new InventoryItem("978-0321765723", 10);
        InventoryItem item2 = new InventoryItem("978-0132350884", 10);

        assertNotEquals(item1, item2, "Inventory items with different ISBNs should not be equal.");
        assertNotEquals(item1.hashCode(), item2.hashCode(), "Hash codes should not be equal for items with different ISBNs.");
    }

    @Test
    void shouldDeductStockCorrectly() {
        InventoryItem item = new InventoryItem("978-0321765723", 10);
        item.deductStock(3);
        assertEquals(7, item.getStock(), "Stock should be correctly deducted.");
    }

    @Test
    void shouldThrowExceptionWhenDeductingZeroStock() {
        InventoryItem item = new InventoryItem("978-0321765723", 10);
        ValidationException exception = assertThrows(ValidationException.class, () -> item.deductStock(0), "Should throw ValidationException when deducting zero stock.");
        assertTrue(exception.getMessage().contains("Quantity to deduct must be positive"), "Exception message should indicate positive quantity.");
        assertEquals(InventoryItem.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be InventoryItem.");
    }

    @Test
    void shouldThrowExceptionWhenDeductingNegativeStock() {
        InventoryItem item = new InventoryItem("978-0321765723", 10);
        ValidationException exception = assertThrows(ValidationException.class, () -> item.deductStock(-1), "Should throw ValidationException when deducting negative stock.");
        assertTrue(exception.getMessage().contains("Quantity to deduct must be positive"), "Exception message should indicate positive quantity.");
        assertEquals(InventoryItem.class.getSimpleName(), exception.getDomainClassName(), "Domain class name should be InventoryItem.");
    }

    @Test
    void shouldThrowExceptionWhenDeductingMoreThanAvailableStock() {
        String isbn = "978-0321765723";
        int initialStock = 5;
        int quantityToDeduct = 10;
        InventoryItem item = new InventoryItem(isbn, initialStock);
        Exception exception = assertThrows(InsufficientStockException.class, () -> item.deductStock(quantityToDeduct), "Should throw InsufficientStockException when deducting more than available stock.");
        assertTrue(exception.getMessage().contains(String.format("Not enough stock for ISBN %s. Requested: %d, Available: %d.", isbn, quantityToDeduct, initialStock)), "Exception message should indicate insufficient stock.");
    }
}
