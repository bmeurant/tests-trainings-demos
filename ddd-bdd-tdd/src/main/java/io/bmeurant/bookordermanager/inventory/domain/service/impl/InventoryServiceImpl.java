package io.bmeurant.bookordermanager.inventory.domain.service.impl;

import io.bmeurant.bookordermanager.application.dto.InventoryResponse;
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

    @Override
    public void checkStock(String isbn, int quantity) {
        log.debug("Attempting to check if {} of ISBN {} is available.", quantity, isbn);
        InventoryItem inventoryItem = inventoryItemRepository.findById(isbn)
                .orElseThrow(() -> {
                    log.warn("Inventory item with ISBN {} not found during stock check.", isbn);
                    return new InventoryItemNotFoundException(isbn);
                });

        inventoryItem.checkAvailability(quantity);
    }

    @Override
    @Transactional
    public void releaseStock(String isbn, int quantity) {
        log.debug("Attempting to release {} to stock for ISBN {}.", quantity, isbn);
        InventoryItem inventoryItem = inventoryItemRepository.findById(isbn)
                .orElseThrow(() -> {
                    log.warn("Inventory item with ISBN {} not found for stock release.", isbn);
                    return new InventoryItemNotFoundException(isbn);
                });

        inventoryItem.releaseStock(quantity);
        inventoryItemRepository.save(inventoryItem);
        log.info("Stock for ISBN {} released to: {}.", isbn, inventoryItem.getStock());
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse getStockByIsbn(String isbn) {
        log.debug("Attempting to retrieve stock for ISBN {}.", isbn);
        InventoryItem inventoryItem = inventoryItemRepository.findById(isbn)
                .orElseThrow(() -> {
                    log.warn("Inventory item with ISBN {} not found.", isbn);
                    return new InventoryItemNotFoundException(isbn);
                });
        return new InventoryResponse(inventoryItem.getIsbn(), inventoryItem.getStock());
    }
}