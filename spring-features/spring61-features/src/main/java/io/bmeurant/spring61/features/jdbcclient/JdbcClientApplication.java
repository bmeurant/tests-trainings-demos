package io.bmeurant.spring61.features.jdbcclient;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.logging.Logger;

public class JdbcClientApplication {
    private static final Logger logger = Logger.getLogger(JdbcClientApplication.class.getName());

    public static void main(String[] args) {
        // Create a Spring ApplicationContext using AnnotationConfigApplicationContext,
        // which loads beans from @Configuration classes.
        ApplicationContext context = new AnnotationConfigApplicationContext(JdbcClientConfig.class);
        logger.info("Spring ApplicationContext initialized.");

        // Manually trigger services for demonstration
        logger.info("\n--- Running JdbcClient Demo ---");

        // Example for JdbcClient
        JdbcClientService jdbcClientDemoService = context.getBean(JdbcClientService.class);
        jdbcClientDemoService.runJdbcClientDemo(); // This method contains a @PostConstruct, but we call it explicitly for demo order.

        logger.info("--- JdbcClient Demo Finished ---");

        // Close the context to ensure all destroy methods are called and resources released
        ((AnnotationConfigApplicationContext) context).close();
        logger.info("Spring ApplicationContext closed.");
    }
}
