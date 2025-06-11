package io.bmeurant.spring61.features.records;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Framework configuration for record-based properties without Spring Boot's main application class.
 * `@EnableConfigurationProperties` is a Spring Boot annotation, but it works with a standard
 * AnnotationConfigApplicationContext.
 */
@Configuration
@EnableConfigurationProperties(RecordProperties.class)
public class RecordConfig {
    // No other beans needed for this demo besides the AppProperties.
}
