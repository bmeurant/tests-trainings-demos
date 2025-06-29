package io.bmeurant.bookordermanager.catalog.domain.repository;

import io.bmeurant.bookordermanager.catalog.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Book entities.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, String> {
}
