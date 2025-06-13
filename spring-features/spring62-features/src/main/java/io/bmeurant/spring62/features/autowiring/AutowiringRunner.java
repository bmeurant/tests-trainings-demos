package io.bmeurant.spring62.features.autowiring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class AutowiringRunner {

    private static final Logger logger = Logger.getLogger(AutowiringRunner.class.getName());

    // Scenario 1: @Qualifier takes precedence over @Priority and @Primary
    private final MyService myServiceWithQualifier;

    // Scenario 2: Parameter name takes precedence over @Priority and @Primary
    private final AnotherService alphaAnotherService; // Parameter name 'alphaAnotherService'

    // Scenario 3: Generic dependency without qualifier
    // Spring 6.2 will choose @Primary if present, otherwise @Priority(5)
    private final MyService genericMyService;

    @Autowired
    public AutowiringRunner(@Qualifier("highPriorityService") MyService myServiceWithQualifier,
                            @Qualifier("alphaAnotherService") AnotherService alphaAnotherService, // Parameter name matches bean name
                            MyService genericMyService) { // No specific qualifier
        this.myServiceWithQualifier = myServiceWithQualifier;
        this.alphaAnotherService = alphaAnotherService;
        this.genericMyService = genericMyService;
    }

    public void runDemo() {
        logger.info("\n--- Autowiring Priority Demo ---");
        logger.info("Service with @Qualifier(\"highPriorityService\") (Expected: High priority message): " + myServiceWithQualifier.getMessage());
        logger.info("Service by parameter name 'alphaAnotherService' (Expected: Alpha): " + alphaAnotherService.getName());
        logger.info("Generic MyService (Expected: Primary message - if PrimaryMyServiceImpl exists, else High priority message): " + genericMyService.getMessage());
    }
}
