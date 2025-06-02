package io.bmeurant.java10.features;

import java.util.NoSuchElementException;
import java.util.Optional;

public class OptionalImprovements {
    public static Optional<String> findUserName(long id) {
        if (id == 201) {
            return Optional.of("Charlie");
        } else if (id == 202) {
            return Optional.of("Diana");
        }
        return Optional.empty();
    }

    public static void main(String[] args) {

        System.out.println("\n+++ Example 1: orElseThrow() (Java 10+) +++\n");

        // This is a simpler way to throw NoSuchElementException if Optional is empty.
        // Before Java 10, you would typically use .get() which has the same behavior
        // but .orElseThrow() clearly conveys the intent of throwing an exception
        // if the Optional is empty.

        Optional<String> user201 = findUserName(201);
        try {
            String name = user201.orElseThrow(); // Works if present
            System.out.println("User 201 found with orElseThrow(): " + name);
        } catch (NoSuchElementException e) {
            System.out.println("Error: User 201 not found (should not happen).");
        }

        Optional<String> user203 = findUserName(203);
        try {
            String name = user203.orElseThrow(); // Throws NoSuchElementException
            System.out.println("User 203 found with orElseThrow(): " + name); // This line won't be reached
        } catch (NoSuchElementException e) {
            System.out.println("Error: User 203 not found, caught NoSuchElementException.");
        }

        // Recap: Old way (Java 8) using get() for comparison
        try {
            String name = findUserName(204).get();
            System.out.println("User 204 found with get(): " + name);
        } catch (NoSuchElementException e) {
            System.out.println("Error: User 204 not found, caught NoSuchElementException (using get()).");
        }

        // Recap: Old way (Java 8) handling it safely
        Optional<String> user205 = findUserName(205);
        if (user205.isPresent()) {
            System.out.println("User 205 found (safe way): " + user205.get());
        } else {
            System.out.println("User 205 not found (safe way).");
        }

        // Using orElse() for default value
        String user206Name = findUserName(206).orElse("Default User");
        System.out.println("User 206 (orElse): " + user206Name);
    }
}
