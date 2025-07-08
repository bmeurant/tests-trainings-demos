package io.bmeurant.bookordermanager.unit.domain.service.impl;

import io.bmeurant.bookordermanager.application.dto.BookResponse;
import io.bmeurant.bookordermanager.application.mapper.BookMapper;
import io.bmeurant.bookordermanager.catalog.domain.exception.BookNotFoundException;
import io.bmeurant.bookordermanager.catalog.domain.model.Book;
import io.bmeurant.bookordermanager.catalog.domain.repository.BookRepository;
import io.bmeurant.bookordermanager.catalog.domain.service.impl.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Should find a book by ISBN when it exists")
    void findBookByIsbn_shouldReturnBookResponseWhenExists() {
        // Given
        String isbn = "978-0321765723";
        Book book = new Book(isbn, "Effective Java", "Joshua Bloch", new BigDecimal("45.00"));
        BookResponse expectedBookResponse = new BookResponse(isbn, "Effective Java", "Joshua Bloch", new BigDecimal("45.00"));

        when(bookRepository.findById(isbn)).thenReturn(Optional.of(book));
        when(bookMapper.mapBookToResponse(book)).thenReturn(expectedBookResponse);

        // When
        BookResponse actualBookResponse = bookService.findBookByIsbn(isbn);

        // Then
        assertNotNull(actualBookResponse, "The returned book response should not be null.");
        assertEquals(expectedBookResponse, actualBookResponse, "The returned book response should match the expected book response.");
        verify(bookRepository, times(1)).findById(isbn);
        verify(bookMapper, times(1)).mapBookToResponse(book);
    }

    @Test
    @DisplayName("Should throw BookNotFoundException when book is not found by ISBN")
    void findBookByIsbn_shouldThrowExceptionWhenNotFound() {
        // Given
        String isbn = "978-0321765723";
        when(bookRepository.findById(isbn)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(BookNotFoundException.class, () -> bookService.findBookByIsbn(isbn),
                "Should throw BookNotFoundException when book is not found.");
        assertTrue(exception.getMessage().contains("Book with ISBN " + isbn + " not found in catalog."),
                "Exception message should indicate book not found.");
        verify(bookRepository, times(1)).findById(isbn);
        verify(bookMapper, never()).mapBookToResponse(any(Book.class));
    }
}
