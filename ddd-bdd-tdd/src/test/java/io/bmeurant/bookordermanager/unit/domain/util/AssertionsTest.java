package io.bmeurant.bookordermanager.unit.domain.util;

import io.bmeurant.bookordermanager.domain.exception.ValidationException;
import io.bmeurant.bookordermanager.domain.util.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class AssertionsTest {

    private static final Class<?> TEST_CLASS = AssertionsTest.class;

    @Test
    @DisplayName("assertHasText should not throw exception for valid text")
    void assertHasText_validText_noException() {
        assertDoesNotThrow(() -> Assertions.assertHasText("some text", "Field", TEST_CLASS));
    }

    @Test
    @DisplayName("assertHasText should throw ValidationException for null text")
    void assertHasText_nullText_throwsException() {
        ValidationException exception = assertThrows(ValidationException.class, () -> Assertions.assertHasText(null, "Field", TEST_CLASS));
        assertTrue(exception.getMessage().contains("Field cannot be null or blank"));
        assertEquals(TEST_CLASS.getSimpleName(), exception.getDomainClassName());
    }

    @Test
    @DisplayName("assertHasText should throw ValidationException for blank text")
    void assertHasText_blankText_throwsException() {
        ValidationException exception = assertThrows(ValidationException.class, () -> Assertions.assertHasText(" ", "Field", TEST_CLASS));
        assertTrue(exception.getMessage().contains("Field cannot be null or blank"));
        assertEquals(TEST_CLASS.getSimpleName(), exception.getDomainClassName());
    }

    @Test
    @DisplayName("assertIsNonNegative should not throw exception for non-negative BigDecimal")
    void assertIsNonNegative_nonNegative_noException() {
        assertDoesNotThrow(() -> Assertions.assertIsNonNegative(BigDecimal.ZERO, "Price", TEST_CLASS));
        assertDoesNotThrow(() -> Assertions.assertIsNonNegative(new BigDecimal("10.00"), "Price", TEST_CLASS));
    }

    @Test
    @DisplayName("assertIsNonNegative should throw ValidationException for null BigDecimal")
    void assertIsNonNegative_nullValue_throwsException() {
        ValidationException exception = assertThrows(ValidationException.class, () -> Assertions.assertIsNonNegative(null, "Price", TEST_CLASS));
        assertTrue(exception.getMessage().contains("Price cannot be null"));
        assertEquals(TEST_CLASS.getSimpleName(), exception.getDomainClassName());
    }

    @Test
    @DisplayName("assertIsNonNegative should throw ValidationException for negative BigDecimal")
    void assertIsNonNegative_negativeValue_throwsException() {
        ValidationException exception = assertThrows(ValidationException.class, () -> Assertions.assertIsNonNegative(new BigDecimal("-0.01"), "Price", TEST_CLASS));
        assertTrue(exception.getMessage().contains("Price cannot be negative"));
        assertEquals(TEST_CLASS.getSimpleName(), exception.getDomainClassName());
    }

    @Test
    @DisplayName("assertIsPositive should not throw exception for positive int")
    void assertIsPositive_positive_noException() {
        assertDoesNotThrow(() -> Assertions.assertIsPositive(1, "Quantity", TEST_CLASS));
    }

    @Test
    @DisplayName("assertIsPositive should throw ValidationException for zero int")
    void assertIsPositive_zeroValue_throwsException() {
        ValidationException exception = assertThrows(ValidationException.class, () -> Assertions.assertIsPositive(0, "Quantity", TEST_CLASS));
        assertTrue(exception.getMessage().contains("Quantity must be positive"));
        assertEquals(TEST_CLASS.getSimpleName(), exception.getDomainClassName());
    }

    @Test
    @DisplayName("assertIsPositive should throw ValidationException for negative int")
    void assertIsPositive_negativeValue_throwsException() {
        ValidationException exception = assertThrows(ValidationException.class, () -> Assertions.assertIsPositive(-1, "Quantity", TEST_CLASS));
        assertTrue(exception.getMessage().contains("Quantity must be positive"));
        assertEquals(TEST_CLASS.getSimpleName(), exception.getDomainClassName());
    }

    @Test
    @DisplayName("assertNotNull should not throw exception for non-null object")
    void assertNotNull_nonNull_noException() {
        assertDoesNotThrow(() -> Assertions.assertNotNull(new Object(), "Object", TEST_CLASS));
    }

    @Test
    @DisplayName("assertNotNull should throw ValidationException for null object")
    void assertNotNull_nullObject_throwsException() {
        ValidationException exception = assertThrows(ValidationException.class, () -> Assertions.assertNotNull(null, "Object", TEST_CLASS));
        assertTrue(exception.getMessage().contains("Object cannot be null"));
        assertEquals(TEST_CLASS.getSimpleName(), exception.getDomainClassName());
    }

    @Test
    @DisplayName("assertNotEmpty should not throw exception for non-empty collection")
    void assertNotEmpty_nonEmpty_noException() {
        assertDoesNotThrow(() -> Assertions.assertNotEmpty(Arrays.asList(1, 2, 3), "List", TEST_CLASS));
    }

    @Test
    @DisplayName("assertNotEmpty should throw ValidationException for null collection")
    void assertNotEmpty_nullCollection_throwsException() {
        ValidationException exception = assertThrows(ValidationException.class, () -> Assertions.assertNotEmpty(null, "List", TEST_CLASS));
        assertTrue(exception.getMessage().contains("List cannot be null or empty"));
        assertEquals(TEST_CLASS.getSimpleName(), exception.getDomainClassName());
    }

    @Test
    @DisplayName("assertNotEmpty should throw ValidationException for empty collection")
    void assertNotEmpty_emptyCollection_throwsException() {
        ValidationException exception = assertThrows(ValidationException.class, () -> Assertions.assertNotEmpty(Collections.emptyList(), "List", TEST_CLASS));
        assertTrue(exception.getMessage().contains("List cannot be null or empty"));
        assertEquals(TEST_CLASS.getSimpleName(), exception.getDomainClassName());
    }

    @Test
    @DisplayName("assertIsTrue should not throw exception for true condition")
    void assertIsTrue_true_noException() {
        assertDoesNotThrow(() -> Assertions.assertIsTrue(true, "Condition should be true", TEST_CLASS));
    }

    @Test
    @DisplayName("assertIsTrue should throw ValidationException for false condition")
    void assertIsTrue_false_throwsException() {
        ValidationException exception = assertThrows(ValidationException.class, () -> Assertions.assertIsTrue(false, "Condition should be true", TEST_CLASS));
        assertEquals("Condition should be true", exception.getMessage());
        assertEquals(TEST_CLASS.getSimpleName(), exception.getDomainClassName());
    }
}
