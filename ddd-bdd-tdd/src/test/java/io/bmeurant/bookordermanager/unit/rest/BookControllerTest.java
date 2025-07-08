package io.bmeurant.bookordermanager.unit.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bmeurant.bookordermanager.application.dto.BookResponse;
import io.bmeurant.bookordermanager.catalog.domain.exception.BookNotFoundException;
import io.bmeurant.bookordermanager.catalog.domain.service.BookService;
import io.bmeurant.bookordermanager.interfaces.rest.BookController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookService bookService;

    @Test
    void getBookByIsbn_whenBookExists_shouldReturn200Ok() throws Exception {
        // Given
        String isbn = "978-0321765723";
        BookResponse bookResponse = new BookResponse(isbn, "The Lord of the Rings", "J.R.R. Tolkien", new BigDecimal("25.00"));

        when(bookService.findBookByIsbn(isbn)).thenReturn(bookResponse);

        // When & Then
        mockMvc.perform(get("/api/books/{isbn}", isbn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(isbn))
                .andExpect(jsonPath("$.title").value("The Lord of the Rings"))
                .andExpect(jsonPath("$.author").value("J.R.R. Tolkien"))
                .andExpect(jsonPath("$.price").value(25.00));
    }

    @Test
    void getBookByIsbn_whenBookDoesNotExist_shouldReturn404NotFound() throws Exception {
        // Given
        String isbn = "nonExistentIsbn";
        when(bookService.findBookByIsbn(isbn)).thenThrow(new BookNotFoundException(isbn));

        // When & Then
        mockMvc.perform(get("/api/books/{isbn}", isbn))
                .andExpect(status().isNotFound());
    }
}
