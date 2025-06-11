package io.bmeurant.java61.features.records;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * A record-based configuration properties class.
 * Spring Boot 3.2+ (and thus Spring Framework 6.1+) natively supports
 * binding external configuration to Java Records using @ConfigurationProperties.
 * This provides immutability and reduces boilerplate code compared to traditional POJOs.
 */
@ConfigurationProperties(prefix = "app")
public record RecordProperties(String name, String version, boolean enabled) {
}
