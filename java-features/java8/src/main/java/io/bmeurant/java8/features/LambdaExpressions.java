package io.bmeurant.java8.features;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class LambdaExpressions {

    public static void main(String[] args) {
        System.out.println("\n+++ Example 1: Basic Lambda for a custom functional interface +++\n");
        basicLambdas();

        System.out.println("\n+++ Example 2: Using Lambdas with Java's built-in functional interfaces (java.util.function) +++\n");
        lambdasWithFunctionalInterfaces();

        System.out.println("\n+++ Example 3: Lambdas for sorting collections (Comparator) +++\n");
        lambdasForSorting();

        System.out.println("\n+++ Example 4: Lambdas with existing APIs (e.g., Thread) +++\n");
        lambdasWithAPIs();
    }

    private static void lambdasWithAPIs() {
        // Running a task in a new thread using an anonymous inner class
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread running (anonymous inner class).");
            }
        }).start();

        // Running a task in a new thread using a lambda expression
        new Thread(() -> System.out.println("Thread running (lambda expression).")).start();

        // Example of effectively final variable capture
        final String greeting = "Hello from lambda!"; // 'greeting' is effectively final
        Runnable lambdaRunnable = () -> System.out.println("Thread running (lambda expression + effectively final) - " + greeting);
        new Thread(lambdaRunnable).start();

        // This would cause a compile-time error because 'greeting' is modified after being used in the lambda
        // String mutableGreeting = "Initial";
        // Runnable errorRunnable = () -> System.out.println(mutableGreeting);
        // mutableGreeting = "Modified"; // Error: Variable used in lambda should be final or effectively final
    }

    private static void lambdasForSorting() {
        List<String> names = new ArrayList<>();
        names.add("Alice");
        names.add("Charlie");
        names.add("Bob");

        System.out.println("Original names: " + names + "\n");

        // Sorting using an anonymous inner class (pre-Java 8 style)
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });
        System.out.println("Lambdas for sorting - Sorted (anonymous inner class): " + names);

        // Reset names for next sort example
        names.clear();
        names.add("Alice");
        names.add("Charlie");
        names.add("Bob");

        // Sorting using a lambda expression (Java 8 style)
        Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
        System.out.println("Lambdas for sorting - Sorted (lambda expression): " + names);

        // Reset names for next sort example
        names.clear();
        names.add("Alice");
        names.add("Charlie");
        names.add("Bob");

        // Even shorter for natural order (Method reference)
        Collections.sort(names, String::compareTo);
        System.out.println("Lambdas for sorting - Sorted (method reference: " + names);
    }

    private static void lambdasWithFunctionalInterfaces() {
        // Consumer: Represents an operation that accepts a single input argument and returns no result.
        Consumer<String> greeter = message -> System.out.println("Consumer - Hello, " + message + "!");
        greeter.accept("Java Developer");

        // Predicate: Represents a predicate (boolean-valued function) of one argument.
        Predicate<Integer> isEven = number -> number % 2 == 0;
        System.out.println("Predicate - Is 7 even? " + isEven.test(7));
        System.out.println("Predicate - Is 8 even? " + isEven.test(8));
    }

    private static void basicLambdas() {
        // Define a simple custom functional interface
        // A functional interface is an interface with a single abstract method.
        // The @FunctionalInterface annotation is optional but recommended for clarity and compiler checks.
        @FunctionalInterface
        interface MySimpleCalculator {
            int operate(int a, int b);
        }

        // Implement the interface using a lambda expression for addition
        MySimpleCalculator adder = (a, b) -> a + b;
        System.out.println("Lambda (Addition): 10 + 5 = " + adder.operate(10, 5));

        // Implement the interface using a lambda expression for multiplication
        MySimpleCalculator multiplier = (a, b) -> a * b;
        System.out.println("Lambda (Multiplication): 10 * 5 = " + multiplier.operate(10, 5));
    }
}
