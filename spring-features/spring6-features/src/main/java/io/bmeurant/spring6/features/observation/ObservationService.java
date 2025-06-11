package io.bmeurant.spring6.features.observation;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Service demonstrating Micrometer Observation in Spring 6.1.
 * This will create traces and metrics for operations.
 */
@Service
public class ObservationService implements CommandLineRunner {

    private static final Logger logger = Logger.getLogger(ObservationService.class.getName());
    private final ObservationRegistry observationRegistry;

    public ObservationService(ObservationRegistry observationRegistry) {
        this.observationRegistry = observationRegistry;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("\n--- Starting Observation Demo ---");
        performComplexOperation("initial-request");
        performSimpleOperation("sub-task");
        performComplexOperation("another-request");
        logger.info("--- Observation Demo Finished ---\n");
    }

    /**
     * A method that represents a 'complex' operation, creating an Observation.
     */
    public void performComplexOperation(String requestId) {
        // Create an Observation with a name and a custom context
        Observation.createNotStarted("complex.operation", this.observationRegistry)
                .lowCardinalityKeyValue("operation.type", "complex") // Low cardinality key-values for dimensions/tags
                .highCardinalityKeyValue("request.id", requestId) // High cardinality key-values for unique IDs
                .observe(() -> { // The actual work to observe
                    logger.info("  Performing complex operation for request: " + requestId);
                    try {
                        TimeUnit.MILLISECONDS.sleep(100 + (long) (Math.random() * 200)); // Simulate work
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    performSimpleOperation("nested-subtask-for-" + requestId);
                });
    }

    /**
     * A method that represents a 'simple' operation, also creating an Observation.
     */
    public void performSimpleOperation(String taskId) {
        // Use Observation.createNotStarted for child observations or simple ones
        Observation.createNotStarted("simple.operation", this.observationRegistry)
                .lowCardinalityKeyValue("task.id", taskId)
                .observe(() -> {
                    logger.info("    Performing simple operation for task: " + taskId);
                    try {
                        TimeUnit.MILLISECONDS.sleep(50 + (long) (Math.random() * 50)); // Simulate work
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
    }
}
