package io.bmeurant.bookordermanager.catalog.domain.service.impl;

import io.bmeurant.bookordermanager.application.dto.BookResponse;
import io.bmeurant.bookordermanager.application.mapper.BookMapper;
import io.bmeurant.bookordermanager.catalog.domain.exception.BookNotFoundException;
import io.bmeurant.bookordermanager.catalog.domain.repository.BookRepository;
import io.bmeurant.bookordermanager.catalog.domain.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link BookService} interface.
 * Handles operations related to books in the catalog.
 */
@Service
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    /**
     * Constructs a new {@code BookServiceImpl} with the given {@link BookRepository}.
     *
     * @param bookRepository The repository for accessing book data.
     * @param bookMapper     The mapper for converting Book domain objects to DTOs.
     */
    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookResponse getBookByIsbn(String isbn) {
        log.debug("Attempting to find book with ISBN: {}.", isbn);
        return bookRepository.findById(isbn)
                .map(bookMapper::mapBookToResponse)
                .orElseThrow(() -> {
                    log.warn("Book with ISBN {} not found in catalog.", isbn);
                    return new BookNotFoundException(isbn);
                });
    }

    @Override
    public java.util.List<BookResponse> findAllBooks() {
        log.debug("Retrieving all books from the catalog.");
        return bookRepository.findAll().stream()
                .map(bookMapper::mapBookToResponse)
                .toList();
    }
}
