package io.bmeurant.bookordermanager.catalog.domain.service;

import io.bmeurant.bookordermanager.catalog.domain.model.Book;

/**
 * Service interface for managing books in the catalog.
 */
public interface BookService {
    /**
     * Finds a book by its International Standard Book Number (ISBN).
     *
     * @param isbn The ISBN of the book to find.
     * @return The Book object if found.
     * @throws io.bmeurant.bookordermanager.catalog.domain.exception.BookNotFoundException if the book with the given ISBN is not found.
     */
    Book findBookByIsbn(String isbn);
}
