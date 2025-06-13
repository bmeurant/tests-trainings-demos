package io.bmeurant.spring62.features.autowiring;

// Interface for Autowiring Demo
public interface MyService {
    String getMessage();
}

// NO @Component, @Priority, @Primary, @Qualifier here
class DefaultMyServiceImpl implements MyService {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DefaultMyServiceImpl.class.getName());

    @Override
    public String getMessage() {
        logger.info("Using DefaultMyServiceImpl (defined via @Bean)");
        return "Default message (Priority 10)";
    }
}

// NO @Component, @Priority, @Primary, @Qualifier here
class HighPriorityServiceImpl implements MyService {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(HighPriorityServiceImpl.class.getName());

    @Override
    public String getMessage() {
        logger.info("Using HighPriorityServiceImpl (defined via @Bean, Priority 5)");
        return "High priority message (Priority 5)";
    }
}

// NO @Component, @Priority, @Primary, @Qualifier here
class PrimaryMyServiceImpl implements MyService {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PrimaryMyServiceImpl.class.getName());

    @Override
    public String getMessage() {
        logger.info("Using PrimaryMyServiceImpl (defined via @Bean, @Primary)");
        return "Primary message";
    }
}