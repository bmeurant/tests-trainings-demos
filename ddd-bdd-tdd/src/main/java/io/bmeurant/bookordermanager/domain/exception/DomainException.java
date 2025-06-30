package io.bmeurant.bookordermanager.domain.exception;

/**
 * Abstract base class for all domain-specific exceptions in the application.
 * This class extends {@code RuntimeException} to indicate that these are unchecked exceptions,
 * typically representing business rule violations or invalid states within the domain.
 */
public abstract class DomainException extends RuntimeException {
    /**
     * Constructs a new {@code DomainException} with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    protected DomainException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code DomainException} with the specified detail message and
     * cause.
     *
     * @param message The detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     *                (A {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    protected DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
