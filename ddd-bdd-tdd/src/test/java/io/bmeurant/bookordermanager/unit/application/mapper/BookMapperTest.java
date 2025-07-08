package io.bmeurant.bookordermanager.unit.application.mapper;

import io.bmeurant.bookordermanager.application.dto.BookResponse;
import io.bmeurant.bookordermanager.application.mapper.BookMapper;
import io.bmeurant.bookordermanager.catalog.domain.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        bookMapper = new BookMapper();
    }

    @Test
    @DisplayName("Should correctly map a Book entity to a BookResponse DTO")
    void mapBookToResponse_shouldMapCorrectly() {
        // Given
        Book book = new Book("978-0321765723", "The Lord of the Rings", "J.R.R. Tolkien", new BigDecimal("25.00"));

        // When
        BookResponse bookResponse = bookMapper.mapBookToResponse(book);

        // Then
        assertNotNull(bookResponse, "BookResponse should not be null");
        assertEquals(book.getIsbn(), bookResponse.isbn(), "ISBN should match");
        assertEquals(book.getTitle(), bookResponse.title(), "Title should match");
        assertEquals(book.getAuthor(), bookResponse.author(), "Author should match");
        assertEquals(book.getPrice(), bookResponse.price(), "Price should match");
    }

    @Test
    @DisplayName("Should return null when mapping a null Book entity")
    void mapBookToResponse_shouldReturnNullForNullBook() {
        // Given
        Book book = null;

        // When
        BookResponse bookResponse = bookMapper.mapBookToResponse(book);

        // Then
        assertNull(bookResponse, "BookResponse should be null for a null Book");
    }
}
