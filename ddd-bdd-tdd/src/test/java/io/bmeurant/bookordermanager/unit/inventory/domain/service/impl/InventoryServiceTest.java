package io.bmeurant.bookordermanager.unit.inventory.domain.service.impl;

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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InventoryServiceTest {

    @Mock
    private InventoryItemRepository inventoryItemRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
        Exception exception = assertThrows(InsufficientStockException.class, () -> inventoryService.deductStock(isbn, quantityToDeduct), "Should throw InsufficientStockException when stock is insufficient.");
        assertTrue(exception.getMessage().contains(String.format("Not enough stock for ISBN %s. Requested: %d, Available: %d.", isbn, quantityToDeduct, initialStock)), "Exception message should indicate insufficient stock.");
        verify(inventoryItemRepository, times(1)).findById(isbn);
        verify(inventoryItemRepository, never()).save(any(InventoryItem.class));
    }
}
