package io.bmeurant.bookordermanager.interfaces.rest.advice;

import io.bmeurant.bookordermanager.application.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * Global exception handler for the REST API.
 * <p>
 * This class captures unhandled exceptions and translates them into a standardized
 * {@link ErrorResponse} format.
 */
@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

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
