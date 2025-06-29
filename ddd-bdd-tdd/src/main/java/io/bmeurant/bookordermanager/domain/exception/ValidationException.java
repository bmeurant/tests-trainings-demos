package io.bmeurant.bookordermanager.domain.exception;

import org.springframework.util.Assert;

/**
 * Exception thrown to indicate that a validation rule has been violated within the domain.
 * This exception typically wraps a descriptive message about what validation failed and for which domain class.
 */
public class ValidationException extends DomainException {
    private final Class<?> domainClass;

    /**
     * Constructs a new {@code ValidationException} with the specified detail message and the domain class
     * where the validation failed.
     *
     * @param message   The detail message.
     * @param domainClass The class within the domain where the validation error occurred. Must not be {@code null}.
     */
    public ValidationException(String message, Class<?> domainClass) {
        super(message);
        Assert.notNull(domainClass, "Domain class must not be null");
        this.domainClass = domainClass;
    }

    /**
     * Constructs a new {@code ValidationException} with the specified detail message, cause, and the domain class
     * where the validation failed.
     *
     * @param message   The detail message.
     * @param cause     The cause of the exception.
     * @param domainClass The class within the domain where the validation error occurred. Must not be {@code null}.
     */
    public ValidationException(String message, Throwable cause, Class<?> domainClass) {
        super(message, cause);
        Assert.notNull(domainClass, "Domain class must not be null");
        this.domainClass = domainClass;
    }

    /**
     * Returns the simple name of the domain class where the validation exception occurred.
     *
     * @return The simple name of the domain class.
     */
    public String getDomainClassName() {
        return domainClass.getSimpleName();
    }
}
