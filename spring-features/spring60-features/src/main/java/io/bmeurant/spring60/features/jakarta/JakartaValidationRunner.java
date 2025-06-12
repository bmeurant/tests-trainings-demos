package io.bmeurant.spring60.features.jakarta;

import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * Component to demonstrate Jakarta EE Validation features.
 * This class is a Spring component and will be managed by the Spring IoC container.
 */
@Component
public class JakartaValidationRunner {
    private static final Logger logger = Logger.getLogger(JakartaValidationRunner.class.getName());

    private final JakartaValidationService jakartaValidationService;

    /**
     * Constructor for dependency injection.
     * Spring will automatically inject an instance of ValidationDemoService.
     *
     * @param validationDemoService The service responsible for validation logic.
     */
    public JakartaValidationRunner(JakartaValidationService validationDemoService) {
        this.jakartaValidationService = validationDemoService;
    }

    /**
     * This method contains the demonstration logic for Jakarta EE validation.
     * It is now a public method to be called explicitly by another component (e.g., MainApplication).
     *
     * @throws InterruptedException if the thread sleep is interrupted.
     */
    public void runValidationDemo() throws InterruptedException {
        logger.info("Validating a correct user form:");
        UserForm validUser = new UserForm("john_doe", "john.doe@example.com", "securePassword123");
        jakartaValidationService.validateUserForm(validUser);

        logger.info("Validating an incorrect user form (empty username, invalid email, short password):");
        UserForm invalidUser = new UserForm("", "invalid-email", "short");
        jakartaValidationService.validateUserForm(invalidUser);

        Thread.sleep(100); // Give time for logs to appear
    }
}
