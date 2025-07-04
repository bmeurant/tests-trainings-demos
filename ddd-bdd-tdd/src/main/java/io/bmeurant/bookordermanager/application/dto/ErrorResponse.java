package io.bmeurant.bookordermanager.application.dto;

import java.time.LocalDateTime;

/**
 * Represents a standardized error response for the API.
 *
 * @param timestamp The time the error occurred.
 * @param status    The HTTP status code.
 * @param error     The type of the error.
 * @param message   The detailed error message.
 * @param path      The path where the error occurred.
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
}
