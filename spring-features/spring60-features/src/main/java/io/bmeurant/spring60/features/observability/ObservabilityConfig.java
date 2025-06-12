package io.bmeurant.spring60.features.observability;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.filter.reactive.ServerHttpObservationFilter;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for Micrometer Observability related beans.
 * This centralizes the setup for ObservationRegistry, AOP for @Observed,
 * WebClient instrumentation, and server-side HTTP observation filters.
 */
@Configuration
@EnableAspectJAutoProxy // Enables Spring AOP for @Observed annotation processing
public class ObservabilityConfig {

    @Bean
    public ObservationRegistry observationRegistry() {
        // In a real application, you'd integrate with a backend like Prometheus, Zipkin, etc.
        // For demonstration, a simple registry that logs observations is sufficient.
        return ObservationRegistry.create();
    }

    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }

    @Bean
    public WebClient.Builder webClientBuilder(ObservationRegistry observationRegistry) {
        // WebClient.Builder is automatically instrumented if ObservationRegistry is available.
        // This builder should be used by any component creating WebClient instances to get instrumentation.
        return WebClient.builder().observationRegistry(observationRegistry);
    }

    @Bean
    public ServerHttpObservationFilter serverHttpObservationFilter(ObservationRegistry observationRegistry) {
        // Registers a filter to observe incoming HTTP requests for Spring WebFlux.
        // Similar filter exists for Spring MVC.
        return new ServerHttpObservationFilter(observationRegistry);
    }
}