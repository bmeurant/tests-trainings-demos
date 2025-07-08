package io.bmeurant.bookordermanager.integration;

import io.bmeurant.bookordermanager.inventory.domain.service.InventoryService;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@CucumberContextConfiguration
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@Transactional
public class CucumberSpringContextConfiguration {
    // This class provides the Spring context configuration for Cucumber tests.
    // It ensures that the Spring application context is set up correctly
    // and is reset before each test method (scenario) to ensure test isolation.

    @MockitoSpyBean
    private InventoryService mockedInventoryService;
}
