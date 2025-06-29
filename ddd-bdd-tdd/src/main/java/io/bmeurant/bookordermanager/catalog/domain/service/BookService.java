package io.bmeurant.bookordermanager.catalog.domain.service;

import io.bmeurant.bookordermanager.catalog.domain.model.Book;

public interface BookService {
    Book findBookByIsbn(String isbn);
}
