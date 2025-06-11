package io.bmeurant.spring61.features.jdbcclient;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Framework configuration for record-based properties without Spring Boot's main application class.
 * `@EnableConfigurationProperties` is a Spring Boot annotation, but it works with a standard
 * AnnotationConfigApplicationContext.
 */
@Configuration
@ComponentScan(basePackages = "io.bmeurant.spring61.features.jdbcclient")
// Scans for @Component, @Service, @Repository, etc.
public class JdbcClientConfig {
    // No other beans needed for this demo
}
