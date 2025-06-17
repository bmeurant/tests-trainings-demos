package io.meurant.spring.boot31.features.observability;

import io.bmeurant.spring.boot31.features.SpringBoot31Application;
import io.bmeurant.spring.boot31.features.config.ActuatorConfig;
import io.bmeurant.spring.boot31.features.config.ObservedAspectConfig;
import io.bmeurant.spring.boot31.features.model.Product;
import io.bmeurant.spring.boot31.features.repository.ProductRepository;
import io.bmeurant.spring.boot31.features.service.ProductService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.tck.TestObservationRegistry;
import io.micrometer.observation.tck.TestObservationRegistryAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {SpringBoot31Application.class},
                properties = {
                        "management.endpoints.web.exposure.include=health,info,metrics,httpexchanges,trace",
                        "management.endpoint.metrics.enabled=true",
                        "management.endpoint.httpexchanges.enabled=true",
                        "management.tracing.sampling.probability=1.0",
                        "server.port=0", // Use a random port for tests
                        "server.ssl.enabled=false" // Disable SSL for tests for simplicity with MockMvc
                })
@AutoConfigureMockMvc
@Import(ObservabilityTest.ObservationTestConfiguration.class)
class ObservabilityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestObservationRegistry observationRegistry; // Autowired our test registry

    @Autowired
    private MeterRegistry meterRegistry; // To check metrics

    @SpyBean // Spy on the actual service and repository to check if their methods are called and observed
    private ProductService productService;

    @SpyBean
    private ProductRepository productRepository;

    @TestConfiguration
    static class ObservationTestConfiguration {
        @Bean
        TestObservationRegistry observationRegistry() {
            return TestObservationRegistry.create();
        }
    }

    // Configure H2 for tests to avoid conflicts and ensure clean state
    @DynamicPropertySource
    static void setH2Properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:h2:mem:testdb_observability;DB_CLOSE_DELAY=-1");
        registry.add("spring.datasource.driverClassName", () -> "org.h2.Driver");
        registry.add("spring.datasource.username", () -> "sa");
        registry.add("spring.datasource.password", () -> "");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Test
    void actuatorHealthEndpointShouldBeUp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/actuator/health"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", is("UP")));
    }

    @Test
    void actuatorMetricsEndpointShouldContainCommonMetrics() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/actuator/metrics"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.names").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.names", hasItem("jvm.memory.used"))); // Check for a common JVM metric
    }

    @Test
    void actuatorHttpExchangesShouldShowRequestsAfterHittingEndpoint() throws Exception {
        // Hit a controller endpoint first to generate an exchange
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Hello from Spring Boot 3.0.13")));

        // Then check the httpexchanges endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/actuator/httpexchanges"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.exchanges").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.exchanges", hasSize(greaterThan(0))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.exchanges[0].request.uri", is("http://localhost/api/v1/hello")));
    }

    @Test
    void findAllProductsMethodShouldBeObserved() {
        // When
        productService.findAllProducts();

        // Then
        TestObservationRegistryAssert.assertThat(observationRegistry)
                .hasObservationWithNameEqualTo("product.find-all") // Correct method name
                .that()
                .hasContextualNameEqualTo("finding-all-products")
                .hasLowCardinalityKeyValue("component", "data-access")
                .hasBeenStarted()
                .hasBeenStopped();

        // Verify a timer metric was registered for the service method
        assertThat(meterRegistry.get("product.find-all") // Get the meter by name
                .tag("component", "data-access") // Filter by tags
                .timer()) // Get it as a timer
                .isNotNull(); // Assert that it exists

        // Verify that the underlying repository method was called
        verify(productRepository, atLeastOnce()).findAll();
    }

    @Test
    void addProductMethodShouldBeObserved() {
        // When
        productService.addProduct(new Product(null, "Test Observed Product", 10.0));

        // Then
        TestObservationRegistryAssert.assertThat(observationRegistry)
                .hasObservationWithNameEqualTo("product.add-one") // Correct method name
                .that()
                .hasContextualNameEqualTo("adding-single-product")
                .hasLowCardinalityKeyValue("operation", "write")
                .hasBeenStarted()
                .hasBeenStopped();

        // Verify a timer metric was registered for the service method
        assertThat(meterRegistry.get("product.add-one") // Get the meter by name
                .tag("operation", "write") // Filter by tags
                .timer()) // Get it as a timer
                .isNotNull(); // Assert that it exists

        // Also verify that the underlying repository save method was called
        verify(productRepository, atLeastOnce()).save(org.mockito.ArgumentMatchers.any(Product.class));
    }
}