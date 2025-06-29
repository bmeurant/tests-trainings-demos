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
public class InventoryItemRepositoryTest {

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldSaveAndFindInventoryItem() {
        InventoryItem item = new InventoryItem("978-0321765723", 10);
        InventoryItem savedItem = inventoryItemRepository.save(item);

        assertNotNull(savedItem.getVersion());
        assertEquals(0L, savedItem.getVersion());

        Optional<InventoryItem> foundItem = inventoryItemRepository.findById(item.getIsbn());

        assertTrue(foundItem.isPresent());
        assertEquals(savedItem, foundItem.get());
        assertEquals(savedItem.getVersion(), foundItem.get().getVersion());
    }

    @Test
    void shouldIncrementVersionOnUpdate() {
        InventoryItem item = new InventoryItem("978-0321765723", 10);
        InventoryItem savedItem = inventoryItemRepository.save(item);

        assertEquals(0L, savedItem.getVersion());

        // Modify the saved item and save it again
        savedItem.deductStock(1);
        inventoryItemRepository.save(savedItem);

        // Flush and clear the persistence context to ensure the next fetch comes from the DB
        entityManager.flush();
        entityManager.clear();

        // Fetch the item again to verify the updated version
        Optional<InventoryItem> foundItem = inventoryItemRepository.findById(item.getIsbn());
        assertTrue(foundItem.isPresent(), "Expected inventoryItem to be present");
        InventoryItem verifiedItem = foundItem.get();

        assertEquals(1L, verifiedItem.getVersion());
    }
}
