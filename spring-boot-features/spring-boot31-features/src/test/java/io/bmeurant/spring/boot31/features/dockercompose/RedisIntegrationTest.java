package io.bmeurant.spring.boot31.features.dockercompose;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for Redis connectivity using Spring Boot's Docker Compose support.
 * This test will use the 'test' profile to activate our application-test.properties.
 */
@SpringBootTest
@Testcontainers
class RedisIntegrationTest {

    @SuppressWarnings("rawtypes")
    public static DockerComposeContainer<?> composeContainer =
            new DockerComposeContainer<>(new File("./compose.yaml")) // Path to your compose file
                    .withExposedService("redis-db", 6379, Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(60)));

    // This method will start the compose container before all tests
    @BeforeAll
    static void startContainer() {
        composeContainer.start();
    }

    // This method will stop the compose container after all tests
    @AfterAll
    static void stopContainer() {
        composeContainer.stop();
    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void testRedisConnectionAndOperations() {
        // ... (rest of your test method remains the same)
        String key = "testKey";
        String value = "testValue";

        redisTemplate.opsForValue().set(key, value);
        String retrievedValue = redisTemplate.opsForValue().get(key);

        assertThat(retrievedValue).isEqualTo(value);

        redisTemplate.delete(key);
        assertThat(redisTemplate.hasKey(key)).isFalse();
    }
}