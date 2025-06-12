package io.bmeurant.spring60.features.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Service demonstrating the logging infrastructure in Spring Framework 6.0.
 * Uses SLF4J as the abstraction and Logback as the underlying implementation (configured in pom.xml).
 */
@Component
public class LoggingRunner {

    // Using SLF4J Logger, which is the standard abstraction in Spring 6.0
    private static final Logger logger = LoggerFactory.getLogger(LoggingRunner.class);

    public void performLoggingOperations() {
        logger.info("This is an INFO message from LogDemoService.");
        logger.debug("This is a DEBUG message. You might not see it if level is higher.");
        logger.warn("This is a WARNING message. Something might be suboptimal.");
        logger.error("This is an ERROR message. Something went wrong!", new RuntimeException("Simulated error"));

        // With SLF4J 2.x, parameterized logging is more efficient and avoids string concatenation overhead
        String user = "Alice";
        int count = 5;
        logger.info("User {} performed {} operations.", user, count);
    }
}