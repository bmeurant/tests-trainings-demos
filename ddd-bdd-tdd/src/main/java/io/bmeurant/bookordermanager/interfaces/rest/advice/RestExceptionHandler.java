package io.bmeurant.bookordermanager.interfaces.rest.advice;

import io.bmeurant.bookordermanager.application.dto.ErrorResponse;
import io.bmeurant.bookordermanager.catalog.domain.exception.BookNotFoundException;
import io.bmeurant.bookordermanager.domain.exception.ValidationException;
import io.bmeurant.bookordermanager.inventory.domain.exception.InsufficientStockException;
import io.bmeurant.bookordermanager.inventory.domain.exception.InventoryItemNotFoundException;
import io.bmeurant.bookordermanager.order.domain.exception.OrderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Global exception handler for the REST API.
 * <p>
 * This class captures unhandled exceptions and translates them into a standardized
 * {@link ErrorResponse} format.
 */
@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);
    public static final String NOT_FOUND = "Not Found";

    /**
     * Handles {@link ValidationException} and returns a 400 Bad Request status.
     * This exception is typically thrown for business rule violations.
     *
     * @param ex      The ValidationException that was thrown.
     * @param request The current web request.
     * @return A {@link ResponseEntity} containing a standardized error response with HTTP status 400 Bad Request.
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex, WebRequest request) {
        logger.warn("Validation failed: {}", ex.getMessage());

        final ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link BookNotFoundException} and returns a 404 Not Found status.
     * This indicates that the requested book resource could not be found.
     *
     * @param ex      The BookNotFoundException that was thrown.
     * @param request The current web request.
     * @return A {@link ResponseEntity} containing a standardized error response with HTTP status 404 Not Found.
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
        logger.warn("Book not found: {}", ex.getMessage());

        final ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link OrderNotFoundException} and returns a 404 Not Found status.
     * This indicates that the requested order resource could not be found.
     *
     * @param ex      The OrderNotFoundException that was thrown.
     * @param request The current web request.
     * @return A {@link ResponseEntity} containing a standardized error response with HTTP status 404 Not Found.
     */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException ex, WebRequest request) {
        logger.warn("Order not found: {}", ex.getMessage());

        final ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link InsufficientStockException} and returns a 409 Conflict status.
     * This indicates that the request could not be processed because of a conflict
     * in the current state of the resource, specifically insufficient stock.
     *
     * @param ex      The InsufficientStockException that was thrown.
     * @param request The current web request.
     * @return A {@link ResponseEntity} containing a standardized error response with HTTP status 409 Conflict.
     */
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStockException(InsufficientStockException ex, WebRequest request) {
        logger.warn("Insufficient stock: {}", ex.getMessage());

        final ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handles {@link InventoryItemNotFoundException} and returns a 404 Not Found status.
     * This indicates that the requested inventory item resource could not be found.
     *
     * @param ex      The InventoryItemNotFoundException that was thrown.
     * @param request The current web request.
     * @return A {@link ResponseEntity} containing a standardized error response with HTTP status 404 Not Found.
     */
    @ExceptionHandler(InventoryItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInventoryItemNotFoundException(InventoryItemNotFoundException ex, WebRequest request) {
        logger.warn("Inventory item not found: {}", ex.getMessage());

        final ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link MethodArgumentNotValidException} which occurs when argument annotated with @Valid fails validation.
     * It extracts validation error messages and returns a 400 Bad Request status.
     *
     * @param ex      The MethodArgumentNotValidException that was thrown.
     * @param request The current web request.
     * @return A {@link ResponseEntity} containing a standardized error response with HTTP status 400 Bad Request.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        logger.warn("Method argument validation failed: {}", ex.getMessage());

        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        final ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                "Invalid input data: " + errorMessage,
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles any unhandled exception as a last resort, mapping it to a 500 Internal Server Error.
     * <p>
     * This method prevents leaking internal stack traces to the client.
     *
     * @param ex      The exception that was thrown.
     * @param request The current web request.
     * @return A {@link ResponseEntity} containing a standardized error response.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Unhandled exception occurred: {}", ex.getMessage(), ex);

        final ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected internal server error occurred. Please contact support.",
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
