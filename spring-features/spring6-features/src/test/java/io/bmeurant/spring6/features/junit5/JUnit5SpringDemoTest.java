package io.bmeurant.spring6.features.junit5;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JUnit 5 features demonstration with Spring 6")
@SpringJUnitConfig(JUnit5SpringDemoTest.TestConfig.class)
class JUnit5SpringDemoTest {

    @Autowired
    private GreetingService greetingService;

    @BeforeAll
    static void beforeAll() {
        System.out.println("🧪 Executed once before all tests");
    }

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        System.out.println("➡️ Starting test: " + testInfo.getDisplayName());
    }

    @AfterEach
    void afterEach(TestInfo testInfo) {
        System.out.println("✅ Finished test: " + testInfo.getDisplayName());
    }

    @Test
    @DisplayName("Should return proper greeting")
    void testGreeting() {
        String result = greetingService.greet("Bob");
        assertEquals("Hello, Bob", result);
    }

    @Test
    @DisplayName("Should throw on null input")
    void testException() {
        assertThrows(IllegalArgumentException.class, () -> greetingService.greet(null));
    }

    @Test
    @Disabled("Temporarily ignored")
    void skippedTest() {
        fail("This test is disabled and should not run");
    }

    @ParameterizedTest(name = "Greet {0}")
    @ValueSource(strings = {"Alice", "Charlie", "Eve"})
    void testParameterizedGreeting(String name) {
        String result = greetingService.greet(name);
        assertTrue(result.contains(name));
    }

    @Test
    @DisplayName("Grouped assertions for greetings")
    void testGroupedAssertions() {
        assertAll("Greeting checks",
                () -> assertEquals("Hello, Alice", greetingService.greet("Alice")),
                () -> assertEquals("Hello, John", greetingService.greet("John"))
        );
    }

    @Nested
    @DisplayName("Nested test class")
    class InnerTests {
        @Test
        void nestedTest() {
            assertEquals("Hello, Bob", greetingService.greet("Bob"));
        }
    }

    @Configuration
    static class TestConfig {
        @Bean
        GreetingService greetingService() {
            return new GreetingService();
        }
    }

    static class GreetingService {
        public String greet(String name) {
            if (name == null) throw new IllegalArgumentException("Name cannot be null");
            return "Hello, " + name;
        }
    }
}
