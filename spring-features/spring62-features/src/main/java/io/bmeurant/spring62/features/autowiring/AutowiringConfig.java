package io.bmeurant.spring62.features.autowiring;

import jakarta.annotation.Priority;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.logging.Logger;

@Configuration
public class AutowiringConfig {

    private static final Logger logger = Logger.getLogger(AutowiringConfig.class.getName());

    // --- MyService Beans ---

    // A bean with low priority
    @Bean("defaultMyService") // Nomme explicitement le bean
    @Priority(10) // Lower priority
    public MyService defaultMyServiceImplBean() {
        logger.info("Defining defaultMyService bean.");
        return new DefaultMyServiceImpl();
    }

    // A bean with @Qualifier and higher priority
    @Bean("highPriorityService") // Nomme explicitement le bean et correspond au qualifier
    @Qualifier("highPriorityService") // Assure que le qualifier est aussi sur le bean
    @Priority(5) // Higher priority
    public MyService highPriorityServiceImplBean() {
        logger.info("Defining highPriorityService bean.");
        return new HighPriorityServiceImpl();
    }

    // The single @Primary bean for MyService
    @Bean("primaryMyService") // Nomme explicitement le bean
    @Primary // Marked as the primary bean
    public MyService primaryMyServiceImplBean() {
        logger.info("Defining primaryMyService bean.");
        return new PrimaryMyServiceImpl();
    }

    // --- AnotherService Beans ---

    // Bean for AlphaAnotherService, named to match parameter in runner
    @Bean("alphaAnotherService")
    public AnotherService alphaAnotherServiceBean() {
        logger.info("Defining alphaAnotherService bean.");
        return new AlphaAnotherServiceImpl();
    }

    // Bean for BetaAnotherService, named to provide another option
    @Bean("betaAnotherService")
    public AnotherService betaAnotherServiceBean() {
        logger.info("Defining betaAnotherService bean.");
        return new BetaAnotherServiceImpl();
    }
}