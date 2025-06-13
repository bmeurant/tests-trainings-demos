package io.bmeurant.spring62.features.fallback;

import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class FallbackRunner {
    private static final Logger logger = Logger.getLogger(FallbackRunner.class.getName());
    private final FallbackService fallbackService;

    // This constructor will attempt to autowire a FallbackService bean.
    // If FallbackOnlyService is the only one available via AppConfig (through FallbackConfig),
    // it will be injected due to @Fallback.
    public FallbackRunner(FallbackService fallbackService) {
        this.fallbackService = fallbackService;
        logger.info("FallbackDemoRunner initialized with: " + fallbackService.getClass().getSimpleName() + " (resolved from default candidates)");
    }

    public void runDemo() {
        logger.info("\n--- @Fallback Annotation Demo ---");
        logger.info("Service consumed by FallbackDemoRunner (Expected: Fallback message): " + fallbackService.getMessage());
        logger.info("Note: @Fallback is a 'last resort' mechanism. If @Qualifier, parameter name, @Primary, or @Priority find a bean, @Fallback is not used for that specific injection point.");
    }
}
