package io.bmeurant.bookordermanager.interfaces.rest;

import io.bmeurant.bookordermanager.application.dto.BookResponse;
import io.bmeurant.bookordermanager.catalog.domain.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing books in the catalog.
 */
@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Operations pertaining to books in the catalog")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Retrieves the details of a specific book by its unique ISBN.
     *
     * @param isbn The unique ISBN of the book to retrieve.
     * @return A {@link ResponseEntity} with the {@link BookResponse} if found (HTTP status 200 OK),
     *         or HTTP status 404 Not Found if the book does not exist.
     */
    @GetMapping("/{isbn}")
    @Operation(summary = "Get book by ISBN", description = "Retrieves the details of a specific book by its unique ISBN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found and returned",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    })
    public ResponseEntity<BookResponse> getBookByIsbn(@PathVariable String isbn) {
        BookResponse bookResponse = bookService.findBookByIsbn(isbn);
        return ResponseEntity.ok(bookResponse);
    }

    /**
     * Retrieves all books in the catalog.
     *
     * @return A {@link ResponseEntity} with a list of {@link BookResponse} objects (HTTP status 200 OK).
     */
    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieves a list of all books in the catalog.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of books retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponse.class)))
    })
    public ResponseEntity<java.util.List<BookResponse>> getAllBooks() {
        java.util.List<BookResponse> books = bookService.findAllBooks();
        return ResponseEntity.ok(books);
    }
}
