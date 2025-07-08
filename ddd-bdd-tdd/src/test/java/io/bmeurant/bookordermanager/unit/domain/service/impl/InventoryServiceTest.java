package io.bmeurant.bookordermanager.unit.domain.service.impl;

import io.bmeurant.bookordermanager.domain.exception.ValidationException;
import io.bmeurant.bookordermanager.inventory.domain.event.ProductStockLowEvent;
import io.bmeurant.bookordermanager.inventory.domain.exception.InsufficientStockException;
import io.bmeurant.bookordermanager.inventory.domain.exception.InventoryItemNotFoundException;
import io.bmeurant.bookordermanager.inventory.domain.model.InventoryItem;
import io.bmeurant.bookordermanager.inventory.domain.repository.InventoryItemRepository;
import io.bmeurant.bookordermanager.inventory.domain.service.impl.InventoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceTest {

    @Mock
    private InventoryItemRepository inventoryItemRepository;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Manually set the lowStockThreshold for the test
        ReflectionTestUtils.setField(inventoryService, "lowStockThreshold", 5);
    }

    @Test
    void deductStock_shouldDeductStockSuccessfully() {
        // Given
        String isbn = "978-0321765723";
        int initialStock = 10;
        int quantityToDeduct = 3;
        InventoryItem inventoryItem = new InventoryItem(isbn, initialStock);

        when(inventoryItemRepository.findById(isbn)).thenReturn(Optional.of(inventoryItem));
        when(inventoryItemRepository.save(any(InventoryItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        InventoryItem updatedItem = inventoryService.deductStock(isbn, quantityToDeduct);

        // Then
        assertNotNull(updatedItem, "Updated item should not be null.");
        assertEquals(initialStock - quantityToDeduct, updatedItem.getStock(), "Stock should be deducted correctly.");
        verify(inventoryItemRepository, times(1)).findById(isbn);
        verify(inventoryItemRepository, times(1)).save(inventoryItem);
        verify(applicationEventPublisher, never()).publishEvent(any()); // No low stock event expected
    }

    @Test
    void deductStock_shouldNotPublishProductStockLowEventWhenStockIsAboveThreshold() {
        // Given
        String isbn = "978-0132350884";
        int initialStock = 10;
        int quantityToDeduct = 2;
        InventoryItem inventoryItem = new InventoryItem(isbn, initialStock);

        when(inventoryItemRepository.findById(isbn)).thenReturn(Optional.of(inventoryItem));
        when(inventoryItemRepository.save(any(InventoryItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        InventoryItem updatedItem = inventoryService.deductStock(isbn, quantityToDeduct);

        // Then
        assertNotNull(updatedItem, "Updated item should not be null.");
        assertEquals(initialStock - quantityToDeduct, updatedItem.getStock(), "Stock should be deducted correctly.");
        verify(inventoryItemRepository, times(1)).findById(isbn);
        verify(inventoryItemRepository, times(1)).save(inventoryItem);
        verify(applicationEventPublisher, never()).publishEvent(any()); // No low stock event expected
    }

    @Test
    void deductStock_shouldPublishProductStockLowEventWhenStockReachesThreshold() {
        // Given
        String isbn = "978-0132350884";
        int initialStock = 7; // Threshold is 5, so 7 - 2 = 5 (reaches threshold)
        int quantityToDeduct = 2;
        InventoryItem inventoryItem = new InventoryItem(isbn, initialStock);

        when(inventoryItemRepository.findById(isbn)).thenReturn(Optional.of(inventoryItem));
        when(inventoryItemRepository.save(any(InventoryItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        InventoryItem updatedItem = inventoryService.deductStock(isbn, quantityToDeduct);

        // Then
        assertNotNull(updatedItem, "Updated item should not be null.");
        assertEquals(initialStock - quantityToDeduct, updatedItem.getStock(), "Stock should be deducted correctly.");
        verify(inventoryItemRepository, times(1)).findById(isbn);
        verify(inventoryItemRepository, times(1)).save(inventoryItem);
        verify(applicationEventPublisher, times(1)).publishEvent(any(ProductStockLowEvent.class));
    }

    @Test
    void deductStock_shouldPublishProductStockLowEventWhenStockFallsBelowThreshold() {
        // Given
        String isbn = "978-0132350884";
        int initialStock = 6; // Threshold is 5, so 6 - 2 = 4 (below threshold)
        int quantityToDeduct = 2;
        InventoryItem inventoryItem = new InventoryItem(isbn, initialStock);

        when(inventoryItemRepository.findById(isbn)).thenReturn(Optional.of(inventoryItem));
        when(inventoryItemRepository.save(any(InventoryItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        InventoryItem updatedItem = inventoryService.deductStock(isbn, quantityToDeduct);

        // Then
        assertNotNull(updatedItem, "Updated item should not be null.");
        assertEquals(initialStock - quantityToDeduct, updatedItem.getStock(), "Stock should be deducted correctly.");
        verify(inventoryItemRepository, times(1)).findById(isbn);
        verify(inventoryItemRepository, times(1)).save(inventoryItem);
        verify(applicationEventPublisher, times(1)).publishEvent(any(ProductStockLowEvent.class));
    }

    @Test
    void deductStock_shouldThrowExceptionWhenItemNotFound() {
        // Given
        String isbn = "978-0321765723";
        int quantityToDeduct = 3;

        when(inventoryItemRepository.findById(isbn)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(InventoryItemNotFoundException.class, () -> inventoryService.deductStock(isbn, quantityToDeduct), "Should throw InventoryItemNotFoundException when inventory item is not found.");
        assertTrue(exception.getMessage().contains("Inventory item with ISBN " + isbn + " not found."), "Exception message should indicate item not found.");
        verify(inventoryItemRepository, times(1)).findById(isbn);
        verify(inventoryItemRepository, never()).save(any(InventoryItem.class));
        verify(applicationEventPublisher, never()).publishEvent(any());
    }

    @Test
    void deductStock_shouldThrowExceptionWhenInsufficientStock() {
        // Given
        String isbn = "978-0321765723";
        int initialStock = 5;
        int quantityToDeduct = 10;
        InventoryItem inventoryItem = new InventoryItem(isbn, initialStock);

        when(inventoryItemRepository.findById(isbn)).thenReturn(Optional.of(inventoryItem));

        // When & Then
        assertThrows(InsufficientStockException.class, () -> inventoryService.deductStock(isbn, quantityToDeduct), "Should throw InsufficientStockException when stock is insufficient.");
        verify(inventoryItemRepository, times(1)).findById(isbn);
        verify(inventoryItemRepository, never()).save(any(InventoryItem.class));
        verify(applicationEventPublisher, never()).publishEvent(any());
    }

    @Test
    void checkStock_shouldNotThrowExceptionWhenStockIsSufficient() {
        // Given
        String isbn = "978-0321765723";
        int initialStock = 10;
        int quantityToCheck = 5;
        InventoryItem inventoryItem = new InventoryItem(isbn, initialStock);

        when(inventoryItemRepository.findById(isbn)).thenReturn(Optional.of(inventoryItem));

        // When & Then
        assertDoesNotThrow(() -> inventoryService.checkStock(isbn, quantityToCheck), "Should not throw exception when stock is sufficient.");
        verify(inventoryItemRepository, times(1)).findById(isbn);
    }

    @Test
    void checkStock_shouldThrowExceptionWhenItemNotFound() {
        // Given
        String isbn = "978-0321765723";
        int quantityToCheck = 5;

        when(inventoryItemRepository.findById(isbn)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(InventoryItemNotFoundException.class, () -> inventoryService.checkStock(isbn, quantityToCheck), "Should throw InventoryItemNotFoundException when inventory item is not found.");
        verify(inventoryItemRepository, times(1)).findById(isbn);
    }

    @Test
    void checkStock_shouldThrowExceptionWhenQuantityIsZero() {
        // Given
        String isbn = "978-0321765723";
        int initialStock = 10;
        int quantityToCheck = 0;
        InventoryItem inventoryItem = new InventoryItem(isbn, initialStock);

        when(inventoryItemRepository.findById(isbn)).thenReturn(Optional.of(inventoryItem));

        // When & Then
        assertThrows(ValidationException.class, () -> inventoryService.checkStock(isbn, quantityToCheck), "Should throw ValidationException when quantity is zero.");
    }

    @Test
    void checkStock_shouldThrowExceptionWhenQuantityIsNegative() {
        // Given
        String isbn = "978-0321765723";
        int initialStock = 10;
        int quantityToCheck = -1;
        InventoryItem inventoryItem = new InventoryItem(isbn, initialStock);

        when(inventoryItemRepository.findById(isbn)).thenReturn(Optional.of(inventoryItem));

        // When & Then
        assertThrows(ValidationException.class, () -> inventoryService.checkStock(isbn, quantityToCheck), "Should throw ValidationException when quantity is negative.");
    }

    @Test
    void checkStock_shouldThrowExceptionWhenInsufficientStock() {
        // Given
        String isbn = "978-0321765723";
        int initialStock = 5;
        int quantityToCheck = 10;
        InventoryItem inventoryItem = new InventoryItem(isbn, initialStock);

        when(inventoryItemRepository.findById(isbn)).thenReturn(Optional.of(inventoryItem));

        // When & Then
        assertThrows(InsufficientStockException.class, () -> inventoryService.checkStock(isbn, quantityToCheck), "Should throw InsufficientStockException when stock is insufficient.");
        verify(inventoryItemRepository, times(1)).findById(isbn);
    }

    @Test
    void releaseStock_shouldReleaseStockSuccessfully() {
        // Given
        String isbn = "978-0321765723";
        int initialStock = 10;
        int quantityToRelease = 5;
        InventoryItem inventoryItem = new InventoryItem(isbn, initialStock);

        when(inventoryItemRepository.findById(isbn)).thenReturn(Optional.of(inventoryItem));
        when(inventoryItemRepository.save(any(InventoryItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        inventoryService.releaseStock(isbn, quantityToRelease);

        // Then
        assertEquals(initialStock + quantityToRelease, inventoryItem.getStock(), "Stock should be correctly released.");
        verify(inventoryItemRepository, times(1)).findById(isbn);
        verify(inventoryItemRepository, times(1)).save(inventoryItem);
        verify(applicationEventPublisher, never()).publishEvent(any());
    }

    @Test
    void releaseStock_shouldThrowInventoryItemNotFoundExceptionWhenItemNotFound() {
        // Given
        String isbn = "978-0321765723";
        int quantityToRelease = 5;

        when(inventoryItemRepository.findById(isbn)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(InventoryItemNotFoundException.class, () -> inventoryService.releaseStock(isbn, quantityToRelease), "Should throw InventoryItemNotFoundException when inventory item is not found.");
        verify(inventoryItemRepository, times(1)).findById(isbn);
        verify(inventoryItemRepository, never()).save(any(InventoryItem.class));
    }

    @Test
    void releaseStock_shouldThrowValidationExceptionWhenQuantityIsZero() {
        // Given
        String isbn = "978-0321765723";
        int initialStock = 10;
        int quantityToRelease = 0;
        InventoryItem inventoryItem = new InventoryItem(isbn, initialStock);

        when(inventoryItemRepository.findById(isbn)).thenReturn(Optional.of(inventoryItem));

        // When & Then
        assertThrows(ValidationException.class, () -> inventoryService.releaseStock(isbn, quantityToRelease), "Should throw ValidationException when quantity is zero.");
        verify(inventoryItemRepository, times(1)).findById(isbn);
        verify(inventoryItemRepository, never()).save(any(InventoryItem.class));
    }

    @Test
    void releaseStock_shouldThrowValidationExceptionWhenQuantityIsNegative() {
        // Given
        String isbn = "978-0321765723";
        int initialStock = 10;
        int quantityToRelease = -1;
        InventoryItem inventoryItem = new InventoryItem(isbn, initialStock);

        when(inventoryItemRepository.findById(isbn)).thenReturn(Optional.of(inventoryItem));

        // When & Then
        assertThrows(ValidationException.class, () -> inventoryService.releaseStock(isbn, quantityToRelease), "Should throw ValidationException when quantity is negative.");
        verify(inventoryItemRepository, times(1)).findById(isbn);
        verify(inventoryItemRepository, never()).save(any(InventoryItem.class));
    }

    @Test
    void getStockByIsbn_shouldReturnStockWhenItemFound() {
        // Given
        String isbn = "978-0321765723";
        int stock = 100;
        InventoryItem inventoryItem = new InventoryItem(isbn, stock);
        when(inventoryItemRepository.findById(isbn)).thenReturn(Optional.of(inventoryItem));

        // When
        io.bmeurant.bookordermanager.application.dto.InventoryResponse response = inventoryService.getStockByIsbn(isbn);

        // Then
        assertNotNull(response);
        assertEquals(isbn, response.isbn());
        assertEquals(stock, response.stock());
        verify(inventoryItemRepository, times(1)).findById(isbn);
    }

    @Test
    void getStockByIsbn_shouldThrowInventoryItemNotFoundExceptionWhenItemNotFound() {
        // Given
        String isbn = "nonExistentISBN";
        when(inventoryItemRepository.findById(isbn)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(InventoryItemNotFoundException.class, () -> inventoryService.getStockByIsbn(isbn));
        verify(inventoryItemRepository, times(1)).findById(isbn);
    }
}
