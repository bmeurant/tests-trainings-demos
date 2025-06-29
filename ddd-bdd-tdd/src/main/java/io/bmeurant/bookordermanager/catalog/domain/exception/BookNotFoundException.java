package io.bmeurant.bookordermanager.catalog.domain.exception;

import io.bmeurant.bookordermanager.domain.exception.DomainException;

/**
 * Exception thrown when a requested book is not found in the catalog.
 */
public class BookNotFoundException extends DomainException {
    /**
     * Constructs a new {@code BookNotFoundException} with a detail message including the ISBN.
     *
     * @param isbn The ISBN of the book that was not found.
     */
    public BookNotFoundException(String isbn) {
        super("Book with ISBN " + isbn + " not found in catalog.");
    }
}
