package io.bmeurant.spring61.features.observation;

import brave.Tracing;
import brave.sampler.Sampler;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.brave.bridge.BraveCurrentTraceContext;
import io.micrometer.tracing.brave.bridge.BraveTracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration for Micrometer Observation and Tracing.
 * Manually sets up the ObservationRegistry and a Brave Tracer.
 */
@Configuration
@ComponentScan(basePackages = "io.bmeurant.spring61.features.observation")
public class ObservationConfig {

    /**
     * Configures the Brave Tracing instance.
     * This is the foundation for Brave-based tracing.
     *
     * @return A Tracing instance for Brave.
     */
    @Bean
    public Tracing tracing() {
        return Tracing.newBuilder()
                .localServiceName("spring61-features-app")
                .sampler(Sampler.ALWAYS_SAMPLE)
                .build();
    }

    /**
     * Configures the Micrometer Tracer from the Brave Tracing instance.
     * This bridges Brave's Tracer with Micrometer's abstraction.
     *
     * @param tracing The Brave Tracing instance.
     * @return A Micrometer Tracer instance wrapping the Brave Tracer.
     */
    @Bean
    public Tracer tracer(Tracing tracing) {
        // Get the Brave's internal Tracer
        brave.Tracer braveTracer = tracing.tracer();

        // Bridge Brave's CurrentTraceContext to Micrometer Tracing's CurrentTraceContext
        io.micrometer.tracing.CurrentTraceContext micrometerCurrentTraceContext =
                new BraveCurrentTraceContext(tracing.currentTraceContext());

        // Return the Micrometer Tracer, using the bridged Brave Tracer and CurrentTraceContext
        return new BraveTracer(braveTracer, micrometerCurrentTraceContext);
        // If you need BaggageManager, you would inject it as a bean and pass it here too:
        // return new BraveTracer(braveTracer, micrometerCurrentTraceContext, baggageManager);
    }


    /**
     * Configures the Micrometer ObservationRegistry.
     * This is the central component for creating and managing observations.
     *
     * @param tracer The Micrometer Tracer to be used for tracing.
     * @return An ObservationRegistry instance.
     */
    @Bean
    public ObservationRegistry observationRegistry(Tracer tracer) {
        ObservationRegistry registry = ObservationRegistry.create();
        registry.observationConfig().observationHandler(new io.micrometer.tracing.handler.DefaultTracingObservationHandler(tracer));
        // You can add more handlers here (e.g., for metrics, logging)
        return registry;
    }
}