package io.bmeurant.bookordermanager.unit.domain.exception;

import io.bmeurant.bookordermanager.domain.exception.DomainException;
import io.bmeurant.bookordermanager.domain.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomainExceptionTest {

    @Test
    void domainException_shouldBeCreatedWithMessage() {
        String message = "Test Domain Exception";
        DomainException exception = new TestDomainException(message);
        assertEquals(message, exception.getMessage(), "DomainException message should match.");
    }

    @Test
    void domainException_shouldBeCreatedWithMessageAndCause() {
        String message = "Test Domain Exception with cause";
        Throwable cause = new RuntimeException("Original cause");
        DomainException exception = new TestDomainException(message, cause);
        assertEquals(message, exception.getMessage(), "DomainException message should match.");
        assertEquals(cause, exception.getCause(), "DomainException cause should match.");
    }

    @Test
    void validationException_shouldBeCreatedWithMessageAndDomainClass() {
        String message = "Test Validation Exception";
        Class<?> domainClass = String.class;
        ValidationException exception = new ValidationException(message, domainClass);
        assertEquals(message, exception.getMessage(), "ValidationException message should match.");
        assertEquals(domainClass.getSimpleName(), exception.getDomainClassName(), "ValidationException domain class name should match.");
    }

    @Test
    void validationException_shouldBeCreatedWithMessageCauseAndDomainClass() {
        String message = "Test Validation Exception with cause";
        Throwable cause = new RuntimeException("Original cause");
        Class<?> domainClass = Integer.class;
        ValidationException exception = new ValidationException(message, cause, domainClass);
        assertEquals(message, exception.getMessage(), "ValidationException message should match.");
        assertEquals(cause, exception.getCause(), "ValidationException cause should match.");
        assertEquals(domainClass.getSimpleName(), exception.getDomainClassName(), "ValidationException domain class name should match.");
    }

    @Test
    void validationException_shouldThrowIllegalArgumentExceptionWhenDomainClassIsNull() {
        String message = "Test Validation Exception";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new ValidationException(message, null), "Should throw IllegalArgumentException when domain class is null.");
        assertTrue(exception.getMessage().contains("Domain class must not be null"), "Exception message should indicate null domain class.");
    }

    @Test
    void validationException_shouldThrowIllegalArgumentExceptionWhenDomainClassIsNullWithCause() {
        String message = "Test Validation Exception with cause";
        Throwable cause = new RuntimeException("Original cause");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new ValidationException(message, cause, null), "Should throw IllegalArgumentException when domain class is null with cause.");
        assertTrue(exception.getMessage().contains("Domain class must not be null"), "Exception message should indicate null domain class.");
    }

    // Helper class to instantiate abstract DomainException
    private static class TestDomainException extends DomainException {
        public TestDomainException(String message) {
            super(message);
        }

        public TestDomainException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
