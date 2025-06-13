package io.bmeurant.spring62.features.fallback;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Fallback;

import java.util.logging.Logger;

@Configuration
public class FallbackConfig {

    private static final Logger logger = Logger.getLogger(FallbackConfig.class.getName());

    // This is the @Fallback bean. It will only be injected if no other FallbackService bean
    // is found via @Qualifier, parameter name, @Primary, or @Priority.
    @Bean
    @Fallback
    public FallbackService fallbackOnlyService() {
        logger.info("Defining FallbackOnlyService bean.");
        return () -> "Fallback message from FallbackOnlyService";
    }

    // You could define other FallbackService beans here too (e.g., with @Primary)
    // and see how they override the @Fallback bean. For this specific demo,
    // we want @Fallback to be the only available candidate to ensure it gets picked.
}
