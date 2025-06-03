package io.bmeurant.java11.features;


import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class LambdaVarParameters {

    public static void main(String[] args) {

        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        System.out.println("\n+++ Example 1: Basic usage: var for consistency or when type is obvious +++\n");
        // Before Java 11: (name) -> name.length() > 3
        Predicate<String> longNamePredicate = (var name) -> name.length() > 4;
        System.out.println("Names with length > 4:");
        names.stream()
                .filter(longNamePredicate)
                .forEach(System.out::println);

        // With multiple parameters
        BiConsumer<String, Integer> printInfo = (var text, var count) -> {
            System.out.println("Text: " + text + ", Count: " + count);
        };
        printInfo.accept("Item A", 10);
        printInfo.accept("Item B", 20);

        System.out.println("\n+++ Example 2: Enabling annotations with var +++\n");

        // Imagine @NonNull is a real annotation for this example.
        // Before Java 11, you'd need: (String name) -> ... if you wanted to annotate 'name'.
        // Now you can: (var name) -> ...
        // (Note: @NotNull is commented out as it requires a specific library dependency)
        Predicate<String> notNullPredicate = (@NotNull var s) -> s != null;
        System.out.println("Is 'Hello' not null? " + notNullPredicate.test("Hello"));
        System.out.println("Is null not null? " + notNullPredicate.test(null));


        // --- 3. Mixing var with inferred type (NOT ALLOWED) ---
        // The following line would cause a compile-time error:
        // BiConsumer<String, Integer> invalidLambda = (var s, int i) -> {}; // Error: Cannot mix 'var' and explicit types

        // The following would also be an error because 'var' needs an initializer (lambda context is different)
        // Function<String, String> invalidLambda2 = (var s) -> { var x; return s; }; // 'var x' without initializer is not allowed
    }
}
