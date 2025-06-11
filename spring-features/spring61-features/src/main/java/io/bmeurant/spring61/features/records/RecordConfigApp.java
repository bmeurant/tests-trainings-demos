package io.bmeurant.spring61.features.records;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.logging.Logger;

/**
 * Standalone application to demonstrate record-based configuration properties
 * using standard Spring Framework without Spring Boot's application runner.
 * We manually create an AnnotationConfigApplicationContext.
 */
public class RecordConfigApp {
    private static final Logger logger = Logger.getLogger(RecordConfigApp.class.getName());

    public static void main(String[] args) {
        // Set system properties for the app properties, or provide an application.properties file
        // For simple execution, setting system properties is direct.
        System.setProperty("app.name", "Manual Record App");
        System.setProperty("app.version", "1.0.1");
        System.setProperty("app.enabled", "false");


        // Create a Spring ApplicationContext using Java Config
        ApplicationContext context = new AnnotationConfigApplicationContext(RecordConfig.class);

        // Retrieve the RecordProperties bean
        RecordProperties appProperties = context.getBean(RecordProperties.class);

        logger.info("--- Loaded App Properties (JUL) ---");
        logger.info("App Name: " + appProperties.name());
        logger.info("App Version: " + appProperties.version());
        logger.info("App Enabled: " + appProperties.enabled());
        logger.info("-----------------------------------");

        // Close the context when done
        ((AnnotationConfigApplicationContext) context).close();
    }
}
