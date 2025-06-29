package io.bmeurant.bookordermanager.inventory.domain.repository;

import io.bmeurant.bookordermanager.inventory.domain.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing InventoryItem entities.
 */
@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, String> {
}
