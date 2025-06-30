package io.bmeurant.bookordermanager.unit.catalog.domain.repository;

import io.bmeurant.bookordermanager.catalog.domain.model.Book;
import io.bmeurant.bookordermanager.catalog.domain.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldSaveAndFindBook() {
        Book book = new Book("978-0321765723", "The Lord of the Rings", "J.R.R. Tolkien", new BigDecimal("25.00"));
        Book savedBook = bookRepository.save(book);

        assertNotNull(savedBook.getVersion(), "Version should not be null after saving.");
        assertEquals(0L, savedBook.getVersion(), "Initial version should be 0.");

        Optional<Book> foundBook = bookRepository.findById(book.getIsbn());

        assertTrue(foundBook.isPresent(), "Expected book to be present after saving.");
        assertEquals(savedBook, foundBook.get(), "Saved and found book should be equal.");
        assertEquals(savedBook.getVersion(), foundBook.get().getVersion(), "Versions should match after initial save.");
    }

    @Test
    void shouldIncrementVersionOnUpdate() {
        Book book = new Book("978-0321765723", "The Lord of the Rings", "J.R.R. Tolkien", new BigDecimal("25.00"));
        Book savedBook = bookRepository.save(book);

        assertEquals(0L, savedBook.getVersion(), "Initial version should be 0 before update.");

        // Modify the saved book and save it again
        savedBook.updateTitle("The Lord of the Rings - Updated");
        bookRepository.save(savedBook);

        // Flush and clear the persistence context to ensure the next fetch comes from the DB
        entityManager.flush();
        entityManager.clear();

        // Fetch the book again to verify the updated version
        Optional<Book> foundBook = bookRepository.findById(book.getIsbn());
        assertTrue(foundBook.isPresent(), "Expected book to be present after update.");
        Book verifiedBook = foundBook.get();

        assertEquals(1L, verifiedBook.getVersion(), "Version should be incremented to 1 after update.");
    }
}
