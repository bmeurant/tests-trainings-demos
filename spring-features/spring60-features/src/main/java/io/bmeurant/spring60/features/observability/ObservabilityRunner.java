package io.bmeurant.spring60.features.observability;

import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

import java.util.logging.Logger;

/**
 * A runner class to trigger various observability demonstrations.
 * This centralizes the calls to ObservedService methods.
 */
@Component
public class ObservabilityRunner {

    private static final Logger logger = Logger.getLogger(ObservabilityRunner.class.getName());

    private final ObservedService observedService;

    public ObservabilityRunner(ObservedService observedService) {
        this.observedService = observedService;
    }

    public void runObservabilityDemos() {
        logger.info("\n--- Demonstration of Observability (Micrometer Integration) ---");

        // Demoing @Observed annotation and manual Observation API
        observedService.doObservedWork("test-operation-annotated");
        observedService.doManualObservedWork("test-operation-manual");
        try {
            Thread.sleep(100); // Give time for observation to complete logging
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Trigger WebClient observations.
        // We use subscribe() with error handling for non-blocking calls.
        // These calls will likely fail as no actual server is running on 8080,
        // but the instrumentation will still record the attempt and failure.
        logger.info("Attempting WebClient calls (check logs for client.requests observations)...");

        // Call 1
        observedService.callExternalService("/some-remote-path")
                .subscribeOn(Schedulers.boundedElastic()) // Run on a separate thread to avoid blocking Main thread
                .doOnSuccess(response -> logger.info("WebClient call 1 successful: " + response))
                .doOnError(error -> logger.warning("WebClient call 1 failed: " + error.getMessage()))
                .subscribe(); // Subscribe to trigger the actual HTTP call

        // Call 2
        observedService.callExternalService("/another-remote-path")
                .subscribeOn(Schedulers.boundedElastic()) // Run on a separate thread
                .doOnSuccess(response -> logger.info("WebClient call 2 successful: " + response))
                .doOnError(error -> logger.warning("WebClient call 2 failed: " + error.getMessage()))
                .subscribe(); // Subscribe to trigger the actual HTTP call

        try {
            // Give enough time for the asynchronous HTTP calls (and potential failures) to complete
            Thread.sleep(5000); // Increased sleep to account for network timeouts
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        logger.info("Observability Demos Finished.");
    }
}
