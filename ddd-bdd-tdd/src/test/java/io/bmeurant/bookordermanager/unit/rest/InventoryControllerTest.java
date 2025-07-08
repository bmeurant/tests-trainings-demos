package io.bmeurant.bookordermanager.unit.rest;

import io.bmeurant.bookordermanager.application.dto.InventoryResponse;
import io.bmeurant.bookordermanager.interfaces.rest.InventoryController;
import io.bmeurant.bookordermanager.inventory.domain.exception.InventoryItemNotFoundException;
import io.bmeurant.bookordermanager.inventory.domain.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventoryService inventoryService;

    @Test
    void getStockByIsbn_whenItemExists_shouldReturn200OkAndStock() throws Exception {
        // Given
        String isbn = "978-0321765723";
        Integer stock = 100;
        InventoryResponse inventoryResponse = new InventoryResponse(isbn, stock);

        when(inventoryService.getStockByIsbn(isbn)).thenReturn(inventoryResponse);

        // When & Then
        mockMvc.perform(get("/api/inventory/{isbn}", isbn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(isbn))
                .andExpect(jsonPath("$.stock").value(stock));
    }

    @Test
    void getStockByIsbn_whenItemDoesNotExist_shouldReturn404NotFound() throws Exception {
        // Given
        String isbn = "nonExistentISBN";

        when(inventoryService.getStockByIsbn(isbn)).thenThrow(new InventoryItemNotFoundException(isbn));

        // When & Then
        mockMvc.perform(get("/api/inventory/{isbn}", isbn))
                .andExpect(status().isNotFound());
    }
}
