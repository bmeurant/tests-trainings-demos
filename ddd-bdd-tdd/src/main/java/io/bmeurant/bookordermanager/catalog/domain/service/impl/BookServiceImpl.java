package io.bmeurant.bookordermanager.catalog.domain.service.impl;

import io.bmeurant.bookordermanager.catalog.domain.exception.BookNotFoundException;
import io.bmeurant.bookordermanager.catalog.domain.model.Book;
import io.bmeurant.bookordermanager.catalog.domain.repository.BookRepository;
import io.bmeurant.bookordermanager.catalog.domain.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        log.debug("Attempting to find book with ISBN: {}.", isbn);
        return bookRepository.findById(isbn)
                .orElseThrow(() -> {
                    log.warn("Book with ISBN {} not found in catalog.", isbn);
                    return new BookNotFoundException(isbn);
                });
    }
}
