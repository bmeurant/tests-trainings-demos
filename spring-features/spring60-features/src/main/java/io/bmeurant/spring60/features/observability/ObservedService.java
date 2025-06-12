package io.bmeurant.spring60.features.observability;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

/**
 * A service demonstrating Spring 6.0's new observability features with Micrometer.
 * Includes direct API usage, @Observed annotation, and WebClient instrumentation.
 */
@Service
public class ObservedService {

    private static final Logger logger = Logger.getLogger(ObservedService.class.getName());
    private final ObservationRegistry observationRegistry;
    private final WebClient webClient; // WebClient for HTTP client observations

    public ObservedService(ObservationRegistry observationRegistry, WebClient.Builder webClientBuilder) {
        this.observationRegistry = observationRegistry;
        // WebClient is automatically instrumented if ObservationRegistry is available in context
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build(); // Base URL for local test server
    }

    /**
     * An observed method using the @Observed annotation.
     * Spring will automatically create an Observation (and a trace span) around this method.
     */
    @Observed(name = "my.service.observed-method", contextualName = "observedMethod")
    public String doObservedWork(String input) {
        logger.info("Performing observed work with input: " + input);
        try {
            Thread.sleep(50); // Simulate some work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Processed: " + input;
    }

    /**
     * An observed method using the Observation API directly.
     * Gives more fine-grained control over the observation.
     */
    public String doManualObservedWork(String input) {
        // Start an observation manually
        return Observation.start("my.service.manual-method", observationRegistry)
                .contextualName("manualObservedMethod")
                .lowCardinalityKeyValue("param.length", String.valueOf(input.length()))
                .observe(() -> { // The actual work to observe
                    logger.info("Performing manual observed work with input: " + input);
                    try {
                        Thread.sleep(70); // Simulate work
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return "Manual Processed: " + input;
                });
    }

    /**
     * Demonstrates WebClient HTTP client observation.
     * The WebClient automatically registers observations (client.requests) if the ObservationRegistry is present.
     */
    @Observed(name = "my.service.webclient-call", contextualName = "webclientCall")
    public Mono<String> callExternalService(String path) {
        logger.info("Making an observed WebClient call to: " + path);
        return webClient.get().uri(path)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> logger.info("WebClient call successful: " + response))
                .doOnError(error -> logger.warning("WebClient call failed: " + error.getMessage()));
    }
}
