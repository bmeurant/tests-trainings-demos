package io.bmeurant.spring.boot31.features.dockercompose;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Configuration class to demonstrate automatic Redis connection via Docker Compose.
 */
@Configuration
public class RedisDemo {

    private static final Logger log = LoggerFactory.getLogger(RedisDemo.class);

    /**
     * A CommandLineRunner bean that executes code once the Spring application context is loaded.
     * It attempts to ping Redis and perform a simple key-value operation.
     *
     * @param connectionFactory The RedisConnectionFactory, automatically configured by Spring Boot
     *                          based on Docker Compose detection.
     * @return A CommandLineRunner instance.
     */
    @Bean
    public CommandLineRunner redisConnectionTest(RedisConnectionFactory connectionFactory) {
        return args -> {
            try {
                // Ping Redis to verify connection
                connectionFactory.getConnection().ping();
                log.info("Successfully connected to Redis via Docker Compose detection!");

                // Perform a simple set and get operation
                StringRedisTemplate template = new StringRedisTemplate(connectionFactory);
                String testKey = "my-docker-compose-test-key";
                String testValue = "Hello from Spring Boot 3.3.1 Redis (Docker Compose)!";

                template.opsForValue().set(testKey, testValue);
                String retrievedValue = template.opsForValue().get(testKey);

                log.info("Redis value for '{}': {}", testKey, retrievedValue);

                template.delete(testKey); // Clean up the key
                log.info("Redis key '{}' deleted.", testKey);

            } catch (Exception e) {
                log.error("Failed to connect to Redis, ensure Docker and compose services are running or detected: {}", e.getMessage());
                // Optionally re-throw or handle more gracefully based on your needs
            }
        };
    }
}
