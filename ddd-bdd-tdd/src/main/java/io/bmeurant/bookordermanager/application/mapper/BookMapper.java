package io.bmeurant.bookordermanager.application.mapper;

import io.bmeurant.bookordermanager.application.dto.BookResponse;
import io.bmeurant.bookordermanager.catalog.domain.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookResponse mapBookToResponse(Book book) {
        if (book == null) {
            return null;
        }
        return new BookResponse(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPrice());
    }
}
