package io.bmeurant.spring6.features.service;

import io.bmeurant.spring6.features.config.ApplicationConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(ApplicationConfig.class)
public class GreetingServiceInjectionTest {

    @Autowired
    private GreetingService greetingService;

    @Test
    void testGreeting() {
        assertEquals("Hello, Spring!", greetingService.greet("Spring"));
        assertTrue(greetingService.isInitialized());
    }
}
