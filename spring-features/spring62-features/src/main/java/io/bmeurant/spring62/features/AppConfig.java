package io.bmeurant.spring62.features;

import io.bmeurant.spring62.features.autowiring.AutowiringConfig;
import io.bmeurant.spring62.features.fallback.FallbackConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(value = "io.bmeurant.spring62",
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "io\\.bmeurant\\.spring62\\.MainApplication"))
@Import({AutowiringConfig.class, FallbackConfig.class}) // Import the feature-specific configurations
public class AppConfig {
    // No beans defined directly here; they are now in their respective config classes
}
