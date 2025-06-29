package io.bmeurant.bookordermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main entry point for the Book Order Manager Spring Boot application.
 * This class enables auto-configuration, component scanning, and asynchronous method execution.
 */
@SpringBootApplication
@EnableAsync
public class BookOrderManagerApplication {

    /**
     * The main method that starts the Spring Boot application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(BookOrderManagerApplication.class, args);
    }

}
