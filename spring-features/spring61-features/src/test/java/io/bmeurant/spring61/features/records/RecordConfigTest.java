package io.bmeurant.spring61.features.records;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for RecordProperties, verifying that record-based configuration
 * is correctly bound by Spring when using standard Spring Test annotations
 * and minimal Spring Boot support for @ConfigurationProperties.
 */
@SpringJUnitConfig(RecordConfig.class) // Combines @ExtendWith(SpringExtension.class) and @ContextConfiguration
@TestPropertySource(properties = {
        "app.name=RecordConfigTestApp",
        "app.version=3.0.0",
        "app.enabled=true"
})
public class RecordConfigTest {

    @Autowired
    private RecordProperties recordProperties;

    @Test
    void testAppPropertiesAreBound() {
        assertNotNull(recordProperties);
        assertEquals("RecordConfigTestApp", recordProperties.name());
        assertEquals("3.0.0", recordProperties.version());
        assertTrue(recordProperties.enabled());
    }
}
