package io.bmeurant.spring6.features.observation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.logging.Logger;

public class ObservationApplication {
    private static final Logger logger = Logger.getLogger(ObservationApplication.class.getName());

    public static void main(String[] args) {
        // Create a Spring ApplicationContext using AnnotationConfigApplicationContext,
        // which loads beans from @Configuration classes.
        ApplicationContext context = new AnnotationConfigApplicationContext(ObservationConfig.class);
        logger.info("Spring ApplicationContext initialized.");

        // Manually trigger services for demonstration
        logger.info("\n--- Running Observation Demo ---");

        // Example for Observation
        ObservationService observationDemoService = context.getBean(ObservationService.class);
        try {
            observationDemoService.run(args); // Simulate CommandLineRunner behavior
        } catch (Exception e) {
            logger.severe("Error running observation demo: " + e.getMessage());
        }

        logger.info("--- Observation Demo Finished ---");
        // Close the context to ensure all destroy methods are called and resources released
        ((AnnotationConfigApplicationContext) context).close();
        logger.info("Spring ApplicationContext closed.");
    }
}
