package io.bmeurant.bookordermanager.unit.application.dto;

import io.bmeurant.bookordermanager.application.dto.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorResponseTest {

    @Test
    void testRecord() {
        // Given
        LocalDateTime timestamp = LocalDateTime.now();
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String error = "Internal Server Error";
        String message = "An unexpected error occurred";
        String path = "/test";

        ErrorResponse response = new ErrorResponse(timestamp, status, error, message, path);

        // When & Then
        assertEquals(timestamp, response.timestamp());
        assertEquals(status, response.status());
        assertEquals(error, response.error());
        assertEquals(message, response.message());
        assertEquals(path, response.path());
    }
}
