package io.bmeurant.bookordermanager.catalog.domain.exception;

import io.bmeurant.bookordermanager.domain.exception.DomainException;

public class BookNotFoundException extends DomainException {
    public BookNotFoundException(String isbn) {
        super("Book with ISBN " + isbn + " not found in catalog.");
    }
}
