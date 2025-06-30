package io.bmeurant.bookordermanager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BookOrderManagerApplicationTest {

    @Test
    void contextLoads() {
        // This test simply ensures that the Spring application context loads successfully.
        // If the context fails to load, this test will fail.
    }

    @Test
    void mainMethodLoadsContext() {
        ConfigurableApplicationContext context = null;
        try {
            context = SpringApplication.run(BookOrderManagerApplication.class);
            assertNotNull(context, "Application context should not be null");
        } finally {
            if (context != null) {
                context.close();
            }
        }
    }
}
