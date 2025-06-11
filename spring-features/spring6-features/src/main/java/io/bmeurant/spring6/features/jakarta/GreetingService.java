package io.bmeurant.spring6.features.jakarta;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * A simple service that will be auto-detected by component scanning.
 * The use of jakarta.annotation.PostConstruct replaces javax.annotation (now deprecated).
 */
@Component
public class GreetingService {

    private boolean initialized = false;

    @PostConstruct
    public void init() {
        // Called after the bean is fully constructed and dependencies injected.
        initialized = true;
    }

    public String greet(String name) {
        return "Hello, " + name + "!";
    }

    public boolean isInitialized() {
        return initialized;
    }
}
