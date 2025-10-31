package io.bmeurant.java.functional;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import static java.lang.IO.println;

/**
 * An example of a custom Functional Interface.
 * The '@FunctionalInterface' annotation is optional but recommended
 * for compile-time checking.
 */
@FunctionalInterface
interface StringOperation {
    String operate(String s); // Single Abstract Method (SAM)

    // Default and static methods are allowed
    default String toUpperCase(String s) {
        return s.toUpperCase();
    }

    static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }
}

public class FunctionalInterfacesDemo {

    void main() {
        // 1. Predicate: Test an input and return a boolean
        Predicate<String> startsWithJ = str -> str.startsWith("Java");
        System.out.println("Does 'Java' start with J? " + startsWithJ.test("Java")); // Output: true

        // 2. Consumer: Consume an input, perform an operation, and return nothing (void)
        Consumer<String> printer = System.out::println;
        printer.accept("Hello Functional Programming!");

        // 3. Function: Accept an input (T) and return a result (R)
        Function<Integer, Integer> doubler = x -> x * 2;
        int result = doubler.apply(10);
        System.out.println("Doubled value: " + result); // Output: 20

        // Using standard interfaces with collections
        List<String> languages = Arrays.asList("Java", "Python", "C++", "JavaScript");

        System.out.println("\nLanguages starting with Java:");
        languages.stream()
                .filter(startsWithJ) // Using the Predicate
                .forEach(printer);   // Using the Consumer

        // 4. Using a custom Functional Interface with a lambda expression
        StringOperation exclamationAppender = text -> text + "!";
        String result1 = exclamationAppender.operate("Hello");
        println("Custom Interface result: " + result1); // Output: Hello!

        // 5. Using a built-in Functional Interface (ToIntFunction<T>)
        ToIntFunction<String> stringLength = String::length; // Method reference
        int length = stringLength.applyAsInt("Functional Java");
        println("Length using ToIntFunction interface: " + length); // Output: 17

        // 6. Using default method from the custom interface
        String upper = exclamationAppender.toUpperCase("java");
        println("Using default method: " + upper); // Output: JAVA

        // 7. Using static method from the custom interface
        String reversed = StringOperation.reverse("coding");
        println("Using static method: " + reversed); // Output: gnidoc
    }
}
