package io.bmeurant.spring62.features.spel;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class SpelRunner {

    private static final Logger logger = Logger.getLogger(SpelRunner.class.getName());
    private final ExpressionParser parser;

    // ExpressionParser is provided by SpelConfig
    public SpelRunner(ExpressionParser parser) {
        this.parser = parser;
    }

    public void runDemo() {
        logger.info("\n--- SpEL Improvements Demo ---");

        StandardEvaluationContext context = new StandardEvaluationContext();

        // 1. Literal expressions
        System.out.println("Literal string: " + parser.parseExpression("'Hello SpEL 6.2'").getValue());

        // 2. Mathematical expression
        System.out.println("Math calc: " + parser.parseExpression("100 * (2 + 4)").getValue(Integer.class));

        // 3. Safe navigation (Spring 6.1+)
        String nullString = null;
        context.setVariable("str", nullString);
        System.out.println("Safe call: " + parser.parseExpression("#str?.length()").getValue(context));

        // 4. Collection literal
        System.out.println("List: " + parser.parseExpression("{1, 2, 3, 4}").getValue());

        // 5. Accessing fields in a Java object (Spring 6.2 improved record support)
        Person person = new Person("Alice", 42);
        context.setVariable("person", person);
        System.out.println("Record name: " + parser.parseExpression("#person.name").getValue(context));
        System.out.println("Record age: " + parser.parseExpression("#person.age").getValue(context));

        // 6. Enum support (Spring 6.2 improved enum handling)
        context.setVariable("day", DayOfWeek.THURSDAY);
        System.out.println("Enum check: " + parser.parseExpression("#day == T(io.bmeurant.spring62.features.spel.SpelRunner.DayOfWeek).THURSDAY").getValue(context));

        // 7. Accessing methods
        context.setVariable("now", LocalDate.now());
        System.out.println("Year today: " + parser.parseExpression("#now.getYear()").getValue(context));

        // 8. Map projection
        context.setVariable("map", Map.of("a", 1, "b", 2));
        System.out.println("Map keys: " + parser.parseExpression("#map.keySet()").getValue(context));

        // 9. Filter a list
        context.setVariable("list", List.of("spring", "spel", "awesome"));
        System.out.println("Filtered list: " +
                parser.parseExpression("#list.?[#this.startsWith('sp')]").getValue(context));

        // 10. Optionals (Spring 6.2 improved Optional handling)
        Optional<String> opt = Optional.of("Spring");

        context.setVariable("opt", opt);

        ExpressionParser parser = new SpelExpressionParser();
        System.out.println("optional: " +
                parser.parseExpression("#opt.orElse('Default')").getValue(context, String.class));
    }

    public record Person(String name, int age) {
    }

    public enum DayOfWeek {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY
    }
}
