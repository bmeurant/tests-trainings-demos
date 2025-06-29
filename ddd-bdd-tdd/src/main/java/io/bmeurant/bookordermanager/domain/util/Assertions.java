package io.bmeurant.bookordermanager.domain.util;

import io.bmeurant.bookordermanager.domain.exception.ValidationException;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Utility class for asserting conditions and throwing {@link ValidationException} if conditions are not met.
 * These assertions are intended for use within the domain layer to enforce business rules and invariants.
 */
public final class Assertions {

    private Assertions() {
        // Private constructor to prevent instantiation
    }

    /**
     * Asserts that the given text is not null or blank.
     *
     * @param text The text to check.
     * @param fieldName The name of the field being validated, used in the exception message.
     * @param domainClass The domain class where the validation is performed.
     * @throws ValidationException if the text is null or blank.
     */
    public static void assertHasText(String text, String fieldName, Class<?> domainClass) {
        if (text == null || text.isBlank()) {
            throw new ValidationException(fieldName + " cannot be null or blank", domainClass);
        }
    }

    /**
     * Asserts that the given BigDecimal value is not null and is non-negative (>= 0).
     *
     * @param value The BigDecimal value to check.
     * @param fieldName The name of the field being validated, used in the exception message.
     * @param domainClass The domain class where the validation is performed.
     * @throws ValidationException if the value is null or negative.
     */
    public static void assertIsNonNegative(BigDecimal value, String fieldName, Class<?> domainClass) {
        if (value == null) {
            throw new ValidationException(fieldName + " cannot be null", domainClass);
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(fieldName + " cannot be negative", domainClass);
        }
    }

    /**
     * Asserts that the given integer value is positive (> 0).
     *
     * @param value The integer value to check.
     * @param fieldName The name of the field being validated, used in the exception message.
     * @param domainClass The domain class where the validation is performed.
     * @throws ValidationException if the value is not positive.
     */
    public static void assertIsPositive(int value, String fieldName, Class<?> domainClass) {
        if (value <= 0) {
            throw new ValidationException(fieldName + " must be positive", domainClass);
        }
    }

    /**
     * Asserts that the given object is not null.
     *
     * @param object The object to check.
     * @param fieldName The name of the field being validated, used in the exception message.
     * @param domainClass The domain class where the validation is performed.
     * @throws ValidationException if the object is null.
     */
    public static void assertNotNull(Object object, String fieldName, Class<?> domainClass) {
        if (object == null) {
            throw new ValidationException(fieldName + " cannot be null", domainClass);
        }
    }

    /**
     * Asserts that the given collection is not null and not empty.
     *
     * @param collection The collection to check.
     * @param fieldName The name of the field being validated, used in the exception message.
     * @param domainClass The domain class where the validation is performed.
     * @throws ValidationException if the collection is null or empty.
     */
    public static void assertNotEmpty(Collection<?> collection, String fieldName, Class<?> domainClass) {
        if (collection == null || collection.isEmpty()) {
            throw new ValidationException(fieldName + " cannot be null or empty", domainClass);
        }
    }

    /**
     * Asserts that the given condition is true.
     *
     * @param condition The condition to check.
     * @param message The message for the exception if the condition is false.
     * @param domainClass The domain class where the validation is performed.
     * @throws ValidationException if the condition is false.
     */
    public static void assertIsTrue(boolean condition, String message, Class<?> domainClass) {
        if (!condition) {
            throw new ValidationException(message, domainClass);
        }
    }
}
