package io.bmeurant.bookordermanager.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for representing inventory item stock information.
 *
 * @param isbn  The International Standard Book Number of the inventory item.
 * @param stock The current stock level of the inventory item.
 */
@Schema(description = "Response DTO for inventory item stock information")
public record InventoryResponse(
        @Schema(description = "International Standard Book Number", example = "978-0321765723")
        String isbn,
        @Schema(description = "Current stock level", example = "100")
        Integer stock
) {
}
