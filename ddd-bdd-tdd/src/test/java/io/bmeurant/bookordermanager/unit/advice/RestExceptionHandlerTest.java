package io.bmeurant.bookordermanager.unit.advice;

import io.bmeurant.bookordermanager.application.dto.ErrorResponse;
import io.bmeurant.bookordermanager.catalog.domain.exception.BookNotFoundException;
import io.bmeurant.bookordermanager.domain.exception.ValidationException;
import io.bmeurant.bookordermanager.interfaces.rest.advice.RestExceptionHandler;
import io.bmeurant.bookordermanager.order.domain.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class RestExceptionHandlerTest {

    @InjectMocks
    private RestExceptionHandler restExceptionHandler;

    @Mock
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleGlobalException_shouldReturnInternalServerError() {
        // Given
        Exception ex = new RuntimeException("Test exception");
        when(webRequest.getDescription(false)).thenReturn("uri=/test");

        // When
        ResponseEntity<ErrorResponse> responseEntity = restExceptionHandler.handleGlobalException(ex, webRequest);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode(), "Status code should be 500.");
        assertNotNull(responseEntity.getBody(), "Response body should not be null.");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getBody().status(), "Error status should be 500.");
        assertEquals("Internal Server Error", responseEntity.getBody().error(), "Error type should be Internal Server Error.");
        assertEquals("An unexpected internal server error occurred. Please contact support.", responseEntity.getBody().message(), "Error message should be generic.");
        assertEquals("uri=/test", responseEntity.getBody().path(), "Path should match.");
        assertNotNull(responseEntity.getBody().timestamp(), "Timestamp should not be null.");
    }

    @Test
    void handleValidationException_shouldReturnBadRequest() {
        // Given
        ValidationException ex = new ValidationException("Invalid order details", Order.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/order");

        // When
        ResponseEntity<ErrorResponse> responseEntity = restExceptionHandler.handleValidationException(ex, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode(), "Status code should be 400.");
        assertNotNull(responseEntity.getBody(), "Response body should not be null.");
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getBody().status(), "Error status should be 400.");
        assertEquals("Validation Error", responseEntity.getBody().error(), "Error type should be Validation Error.");
        assertEquals("Invalid order details", responseEntity.getBody().message(), "Error message should match exception.");
        assertEquals("uri=/order", responseEntity.getBody().path(), "Path should match.");
        assertNotNull(responseEntity.getBody().timestamp(), "Timestamp should not be null.");
    }

    @Test
    void handleBookNotFoundException_shouldReturnNotFound() {
        // Given
        BookNotFoundException ex = new BookNotFoundException("1234567890");
        when(webRequest.getDescription(false)).thenReturn("uri=/book/1234567890");

        // When
        ResponseEntity<ErrorResponse> responseEntity = restExceptionHandler.handleBookNotFoundException(ex, webRequest);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode(), "Status code should be 404.");
        assertNotNull(responseEntity.getBody(), "Response body should not be null.");
        assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getBody().status(), "Error status should be 404.");
        assertEquals("Not Found", responseEntity.getBody().error(), "Error type should be Not Found.");
        assertEquals("Book with ISBN 1234567890 not found in catalog.", responseEntity.getBody().message(), "Error message should match exception.");
        assertEquals("uri=/book/1234567890", responseEntity.getBody().path(), "Path should match.");
        assertNotNull(responseEntity.getBody().timestamp(), "Timestamp should not be null.");
    }
}
