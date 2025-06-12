package io.bmeurant.spring60.features.jakarta;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Service demonstrating the use of Jakarta Validation API with Spring Framework 6.0.
 * Note the 'jakarta.validation' imports for Validator and ConstraintViolation.
 */
@Service
public class JakartaValidationService {
    private static final Logger logger = Logger.getLogger(JakartaValidationService.class.getName());
    private final Validator validator;

    public JakartaValidationService() {
        // Initialize the Validator instance
        // In a Spring Boot application, Validator is automatically provided as a bean.
        // In a pure Spring Framework context, you might need to configure it explicitly.
        // For this demo, we'll create a standalone instance.
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public void validateUserForm(UserForm form) {
        logger.info("Attempting to validate UserForm: " + form.getUsername());
        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);

        if (violations.isEmpty()) {
            logger.info("UserForm is valid: " + form.getUsername());
        } else {
            logger.warning("UserForm has validation errors for: " + form.getUsername());
            for (ConstraintViolation<UserForm> violation : violations) {
                logger.warning("  - " + violation.getPropertyPath() + ": " + violation.getMessage() + " (Invalid value: " + violation.getInvalidValue() + ")");
            }
        }
    }
}
