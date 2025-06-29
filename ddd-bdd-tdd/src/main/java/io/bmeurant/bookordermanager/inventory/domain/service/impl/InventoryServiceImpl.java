package io.bmeurant.bookordermanager.inventory.domain.service.impl;

import io.bmeurant.bookordermanager.inventory.domain.exception.InventoryItemNotFoundException;
import io.bmeurant.bookordermanager.inventory.domain.model.InventoryItem;
import io.bmeurant.bookordermanager.inventory.domain.repository.InventoryItemRepository;
import io.bmeurant.bookordermanager.inventory.domain.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the InventoryService interface.
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);

    private final InventoryItemRepository inventoryItemRepository;

    @Autowired
    public InventoryServiceImpl(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    @Override
    @Transactional
    public InventoryItem deductStock(String isbn, int quantity) {
        log.debug("Attempting to deduct {} from stock for ISBN {}.", quantity, isbn);
        InventoryItem inventoryItem = inventoryItemRepository.findById(isbn)
                .orElseThrow(() -> {
                    log.warn("Inventory item with ISBN {} not found for deduction.", isbn);
                    return new InventoryItemNotFoundException(isbn);
                });

        inventoryItem.deductStock(quantity);
        InventoryItem updatedItem = inventoryItemRepository.save(inventoryItem);
        log.info("Stock for ISBN {} updated to: {}.", isbn, updatedItem.getStock());
        return updatedItem;
    }
}
