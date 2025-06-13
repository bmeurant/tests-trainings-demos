package io.bmeurant.spring62.features;

import io.bmeurant.spring62.features.autowiring.AutowiringRunner;
import io.bmeurant.spring62.features.fallback.FallbackRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.logging.Logger;

public class MainApplication {

    private static final Logger logger = Logger.getLogger(MainApplication.class.getName());

    public static void main(String[] args) throws Exception {
        logger.info("Starting Spring Framework 6.2 Features Demo Application...");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            logger.info("Spring Application Context initialized successfully.");

            // --- Demonstration of Autowiring Algorithm Revision ---
            logger.info("\n--- Demonstration of Autowiring Algorithm Revision (Spring Framework 6.2) ---");
            AutowiringRunner autowiringRunner = context.getBean(AutowiringRunner.class);
            autowiringRunner.runDemo();
            Thread.sleep(100); // Give some time for logs to settle

            // --- Demonstration of @Fallback Annotation ---
            logger.info("\n--- Demonstration of @Fallback Annotation (Spring Framework 6.2) ---");
            FallbackRunner fallbackRunner = context.getBean(FallbackRunner.class);
            fallbackRunner.runDemo();
            Thread.sleep(100); // Give some time for logs to settle

            logger.info("Spring Framework 6.2 Features Demo Application Finished.");
        }
    }
}
