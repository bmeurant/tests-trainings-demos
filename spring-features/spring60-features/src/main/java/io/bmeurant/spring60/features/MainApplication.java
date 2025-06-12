package io.bmeurant.spring60.features;

import io.bmeurant.spring60.features.jakarta.JakartaValidationRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

/**
 * Main application class to demonstrate Spring Framework 6.0 features.
 * This sets up a pure Spring Framework context without Spring Boot auto-configuration.
 */
@Configuration // Marks this class as a source of bean definitions
@ComponentScan("io.bmeurant.spring60.features") // Scans for Spring components within this package
public class MainApplication {

    private static final Logger logger = Logger.getLogger(MainApplication.class.getName());

    public static void main(String[] args) throws Exception {
        logger.info("Starting Spring Framework 6.0 Features Demo Application (Initial Setup)...");

        // Create a Spring application context based on annotation configuration
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainApplication.class)) {
            logger.info("Spring Application Context initialized successfully.");

            logger.info("--- Demonstration of Jakarta EE Migration (javax -> jakarta) ---");
            JakartaValidationRunner jakartaValidationRunner = context.getBean(JakartaValidationRunner.class);
            jakartaValidationRunner.runValidationDemo();
            Thread.sleep(100); // Give time for logs to appear
        }
        logger.info("Spring Framework 6.0 Features Demo Application Finished.");
    }
}