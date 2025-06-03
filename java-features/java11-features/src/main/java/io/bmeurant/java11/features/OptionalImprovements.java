package io.bmeurant.java11.features;

import java.util.Optional;

public class OptionalImprovements {

    public static Optional<String> getUserNameById(int id) {
        if (id == 1) {
            return Optional.of("Emma");
        }
        return Optional.empty();
    }

    public static void main(String[] args) {
        Optional<String> userFound = getUserNameById(1);
        Optional<String> userNotFound = getUserNameById(2);

        // --- Example: isEmpty() (Java 11+) ---
        System.out.println("\n+++ isEmpty() +++\n");
        System.out.println("userFound.isEmpty(): " + userFound.isEmpty());       // false
        System.out.println("userNotFound.isEmpty(): " + userNotFound.isEmpty()); // true

        // For comparison (Java 8 way):
        System.out.println("userFound.isPresent(): " + userFound.isPresent());     // true
        System.out.println("!userNotFound.isPresent(): " + !userNotFound.isPresent()); // true (equivalent to isEmpty)

        if (userFound.isEmpty()) {
            System.out.println("User 1 is not found."); // This won't print
        } else {
            System.out.println("User 1 is found: " + userFound.get());
        }

        if (userNotFound.isEmpty()) {
            System.out.println("User 2 is not found."); // This will print
        } else {
            System.out.println("User 2 is found: " + userNotFound.get());
        }
    }
}
