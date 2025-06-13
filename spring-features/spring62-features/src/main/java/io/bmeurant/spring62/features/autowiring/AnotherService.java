package io.bmeurant.spring62.features.autowiring;

// Interface for Autowiring by parameter name demo
public interface AnotherService {
    String getName();
}

// NO @Component, @Qualifier here
class AlphaAnotherServiceImpl implements AnotherService {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AlphaAnotherServiceImpl.class.getName());

    @Override
    public String getName() {
        logger.info("Using AlphaAnotherServiceImpl (defined via @Bean, name 'alphaAnotherService')");
        return "Alpha";
    }
}

// NO @Component, @Qualifier here
class BetaAnotherServiceImpl implements AnotherService {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BetaAnotherServiceImpl.class.getName());

    @Override
    public String getName() {
        logger.info("Using BetaAnotherServiceImpl (defined via @Bean, name 'betaAnotherService')");
        return "Beta";
    }
}