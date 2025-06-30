package io.bmeurant.bookordermanager.inventory.domain.service.impl;

import io.bmeurant.bookordermanager.inventory.domain.event.ProductStockLowEvent;
import io.bmeurant.bookordermanager.inventory.domain.exception.InventoryItemNotFoundException;
import io.bmeurant.bookordermanager.inventory.domain.model.InventoryItem;
import io.bmeurant.bookordermanager.inventory.domain.repository.InventoryItemRepository;
import io.bmeurant.bookordermanager.inventory.domain.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the InventoryService interface.
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);

    private final InventoryItemRepository inventoryItemRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Value("${inventory.low-stock-threshold:5}")
    private int lowStockThreshold;

    /**
     * Constructs a new {@code InventoryServiceImpl} with the given {@link InventoryItemRepository}.
     *
     * @param inventoryItemRepository The repository for accessing inventory item data.
     */
    @Autowired
    public InventoryServiceImpl(InventoryItemRepository inventoryItemRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.applicationEventPublisher = applicationEventPublisher;
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

        // Publish ProductStockLowEvent if stock falls below threshold
        if (updatedItem.getStock() <= lowStockThreshold) {
            applicationEventPublisher.publishEvent(new ProductStockLowEvent(this, updatedItem.getIsbn(), updatedItem.getStock()));
        }

        return updatedItem;
    }
}