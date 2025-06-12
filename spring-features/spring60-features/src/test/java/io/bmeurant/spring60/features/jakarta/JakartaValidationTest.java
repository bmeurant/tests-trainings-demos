package io.bmeurant.spring60.features.jakarta;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for Jakarta Validation API integration, confirming 'jakarta' namespace usage.
 */
@SpringJUnitConfig(JakartaValidationTest.TestConfig.class)
public class JakartaValidationTest {

    @Autowired
    private Validator validator;

    @BeforeEach
    void setup() {
        // No specific setup needed for validator in JUnit test, it's injected.
    }

    @Test
    void testValidUserForm() {
        UserForm userForm = new UserForm("validuser", "valid.email@example.com", "securepassword");
        assertTrue(validator.validate(userForm).isEmpty(), "Valid form should have no violations.");
    }

    @Test
    void testInvalidUserForm() {
        UserForm userForm = new UserForm("", "invalid-email", "short");
        assertFalse(validator.validate(userForm).isEmpty(), "Invalid form should have violations.");
        // We could add more specific assertions about the violations if needed
    }

    @Configuration
    static class TestConfig {
        @Bean
        public Validator validator() {
            // Provide a Validator bean for the test context
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            return factory.getValidator();
        }
    }
}
