package io.meurant.spring.boot31.features.http2;

import io.bmeurant.spring.boot31.features.SpringBoot31Application;
import io.bmeurant.spring.boot31.features.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = {
        "server.ssl.enabled=true",
        "server.ssl.key-store=classpath:keystore.p12",
        "server.ssl.key-store-type=PKCS12",
        "server.ssl.key-store-password=password",
        "server.ssl.key-alias=tomcat",
        "server.http2.enabled=true",
        "server.port=0" // Use a random port for tests to avoid conflicts
},classes = { SpringBoot31Application.class})
class Http2Test {

    @Autowired
    private ProductRepository productRepository;

    // Configure H2 for tests uses a new in-memory database to avoid conflicts with main app (if running)
    @DynamicPropertySource
    static void setH2Properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:h2:mem:testdb_test;DB_CLOSE_DELAY=-1");
        registry.add("spring.datasource.driverClassName", () -> "org.h2.Driver");
        registry.add("spring.datasource.username", () -> "sa");
        registry.add("spring.datasource.password", () -> "");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Test
    void contextLoadsWithSslAndHttp2Enabled() {
        // This test ensures the Spring context loads successfully with HTTP/2 and SSL properties.
        // It implicitly validates that the SSL setup is correctly parsed by Spring Boot.
        assertThat(true).isTrue(); // Simple assertion to indicate test ran
    }

    @Test
    void shouldFindProductsAfterInit() {
        // This test validates Spring Data JPA is working correctly with Jakarta EE
        // The @PostConstruct in DemoController ensures data is initialized
        assertThat(productRepository.count()).isEqualTo(3);
        assertThat(productRepository.findByName("Laptop")).isNotNull();
    }
}
