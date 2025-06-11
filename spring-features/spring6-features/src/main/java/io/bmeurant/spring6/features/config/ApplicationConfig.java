package io.bmeurant.spring6.features.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * This configuration class replaces the old XML configuration files.
 * It tells Spring to scan the specified package for components annotated with @Component, @Service, etc.
 */
@Configuration
@ComponentScan("io.bmeurant.spring6.features.service")
public class ApplicationConfig {
    // No explicit bean definitions here: components will be auto-detected.
}
