package io.bmeurant.bookordermanager.unit.inventory.domain.repository;

import io.bmeurant.bookordermanager.inventory.domain.model.InventoryItem;
import io.bmeurant.bookordermanager.inventory.domain.repository.InventoryItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InventoryItemRepositoryTest {

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldSaveAndFindInventoryItem() {
        InventoryItem item = new InventoryItem("978-0321765723", 10);
        InventoryItem savedItem = inventoryItemRepository.save(item);

        assertNotNull(savedItem.getVersion(), "Version should not be null after saving.");
        assertEquals(0L, savedItem.getVersion(), "Initial version should be 0.");

        Optional<InventoryItem> foundItem = inventoryItemRepository.findById(item.getIsbn());

        assertTrue(foundItem.isPresent(), "Expected inventory item to be present after saving.");
        assertEquals(savedItem, foundItem.get(), "Saved and found inventory item should be equal.");
        assertEquals(savedItem.getVersion(), foundItem.get().getVersion(), "Versions should match after initial save.");
    }

    @Test
    void shouldIncrementVersionOnUpdate() {
        InventoryItem item = new InventoryItem("978-0321765723", 10);
        InventoryItem savedItem = inventoryItemRepository.save(item);

        assertEquals(0L, savedItem.getVersion(), "Initial version should be 0 before update.");

        // Modify the saved item and save it again
        savedItem.deductStock(1);
        inventoryItemRepository.save(savedItem);

        // Flush and clear the persistence context to ensure the next fetch comes from the DB
        entityManager.flush();
        entityManager.clear();

        // Fetch the item again to verify the updated version
        Optional<InventoryItem> foundItem = inventoryItemRepository.findById(item.getIsbn());
        assertTrue(foundItem.isPresent(), "Expected inventory item to be present after update.");
        InventoryItem verifiedItem = foundItem.get();

        assertEquals(1L, verifiedItem.getVersion(), "Version should be incremented to 1 after update.");
    }
}
