package io.bmeurant.bookordermanager.interfaces.rest;

import io.bmeurant.bookordermanager.application.dto.InventoryResponse;
import io.bmeurant.bookordermanager.inventory.domain.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing inventory items.
 * Provides API endpoints for retrieving inventory stock levels.
 */
@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Inventory", description = "Operations pertaining to inventory management in Book Order Manager")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * Retrieves the stock level for a specific inventory item by its ISBN.
     *
     * @param isbn The ISBN of the inventory item to retrieve stock for.
     * @return A {@link ResponseEntity} with the {@link InventoryResponse} containing the stock level if found (HTTP status 200 OK),
     *         or HTTP status 404 Not Found if the inventory item does not exist.
     */
    @GetMapping("/{isbn}")
    @Operation(summary = "Get inventory stock by ISBN", description = "Retrieves the stock level for a specific inventory item by its ISBN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock level found and returned",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InventoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Inventory item not found", content = @Content)
    })
    public ResponseEntity<InventoryResponse> getStockByIsbn(@PathVariable String isbn) {
        InventoryResponse inventoryResponse = inventoryService.getStockByIsbn(isbn);
        return ResponseEntity.ok(inventoryResponse);
    }
}
