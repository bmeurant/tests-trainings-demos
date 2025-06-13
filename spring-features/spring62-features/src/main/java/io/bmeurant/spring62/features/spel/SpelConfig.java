package io.bmeurant.spring62.features.spel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

@Configuration
public class SpelConfig {

    @Bean
    public ExpressionParser spelExpressionParser() {
        return new SpelExpressionParser();
    }
}
