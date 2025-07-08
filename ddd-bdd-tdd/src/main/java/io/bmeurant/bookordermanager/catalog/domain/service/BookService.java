package io.bmeurant.bookordermanager.catalog.domain.service;

import io.bmeurant.bookordermanager.application.dto.BookResponse;
import io.bmeurant.bookordermanager.catalog.domain.exception.BookNotFoundException;

import java.util.List;

/**
 * Service interface for managing books in the catalog.
 */
public interface BookService {
    /**
     * Finds a book by its International Standard Book Number (ISBN).
     *
     * @param isbn The ISBN of the book to find.
     * @return The BookResponse object if found.
     * @throws BookNotFoundException if the book with the given ISBN is not found.
     */
    BookResponse getBookByIsbn(String isbn);

    /**
     * Finds all books in the catalog.
     *
     * @return A list of all BookResponse objects.
     */
    List<BookResponse> findAllBooks();
}
