package io.bmeurant.java8.features;

import java.util.Optional;
import java.util.function.Function;

public class OptionalAPI {

    // A simple method that might return a value or nothing
    public static Optional<String> getUserName(long id) {
        if (id == 101) {
            return Optional.of("Alice");
        } else if (id == 102) {
            return Optional.of("Bob");
        } else {
            return Optional.empty(); // No user found for this ID
        }
    }

    // Another method that might return null, which we wrap with Optional.ofNullable
    public static String getEmailByUserId(long id) {
        if (id == 101) {
            return "alice@example.com";
        } else if (id == 102) {
            return "bob@example.com";
        }
        return null; // This method can return null
    }


    public static void main(String[] args) {
        // --- Creating Optional instances ---
        String valuePresent = "Hello Optional";
        Optional<String> optionalPresent = Optional.of(valuePresent); // Value is present
        System.out.println("\nOptional with value: " + optionalPresent);

        String valueNull = null;
        // Optional<String> optionalError = Optional.of(valueNull); // Throws NullPointerException!
        Optional<String> optionalEmpty = Optional.ofNullable(valueNull); // Handles null gracefully, creates empty Optional
        System.out.println("Optional from null (ofNullable): " + optionalEmpty);

        Optional<String> explicitEmpty = Optional.empty(); // Explicitly empty Optional
        System.out.println("Explicitly empty Optional: " + explicitEmpty);


        // --- Checking for presence and retrieving values ---
        System.out.println("\n--- Checking and Retrieving ---\n");

        Optional<String> user101 = getUserName(101); // Contains "Alice"
        Optional<String> user103 = getUserName(103); // Is empty

        // isPresent() and get() - Use get() carefully!
        if (user101.isPresent()) {
            System.out.println("User 101 found: " + user101.get());
        } else {
            System.out.println("User 101 not found."); // This won't print
        }

        if (user103.isPresent()) {
            System.out.println("User 103 found: " + user103.get());
        } else {
            System.out.println("User 103 not found."); // This will print
        }

        // orElse() - Provides a default value if Optional is empty
        String name101 = user101.orElse("Guest");
        System.out.println("User 101 (orElse): " + name101); // Output: Alice

        String name103 = user103.orElse("Anonymous");
        System.out.println("User 103 (orElse): " + name103); // Output: Anonymous

        // orElseGet() - Provides a default value using a Supplier (lazy evaluation)
        // The lambda (supplier) is only executed if the Optional is empty.
        String email101 = Optional.ofNullable(getEmailByUserId(101))
                .orElseGet(() -> {
                    System.out.println("Calculating default email for user 101...");
                    return "default@example.com";
                });
        System.out.println("Email for 101 (orElseGet): " + email101); // Output: alice@example.com (Supplier not executed)

        String email104 = Optional.ofNullable(getEmailByUserId(104))
                .orElseGet(() -> {
                    System.out.println("Calculating default email for user 104...");
                    return "unknown@example.com";
                });
        System.out.println("Email for 104 (orElseGet): " + email104); // Output: unknown@example.com (Supplier executed)


        // ifPresent() - Executes an action if value is present
        System.out.println("\n--- ifPresent example ---");
        getUserName(102).ifPresent(name -> System.out.println("Hello, " + name + "!")); // Prints "Hello, Bob!"
        getUserName(104).ifPresent(name -> System.out.println("Hello, " + name + "!")); // Nothing prints


        // --- Functional operations with Optional ---
        System.out.println("\n--- Functional Operations (filter, map, flatMap) ---");

        // filter() - filter based on a condition
        Optional<String> longName = getUserName(101) // "Alice"
                .filter(name -> name.length() > 5);
        System.out.println("Name > 5 chars (Alice): " + longName); // Output: Optional.empty (Alice length is 5)

        Optional<String> longerName = getUserName(102) // "Bob" -> (not > 3 chars)
                .filter(name -> name.length() > 3);
        System.out.println("Name > 3 chars (Bob): " + longerName); // Output: Optional[Bob]

        // map() - transform the value if present
        Optional<Integer> nameLength = getUserName(101) // "Alice"
                .map(String::length); // Maps "Alice" to 5
        System.out.println("Length of name 101: " + nameLength.orElse(0)); // Output: 5

        Optional<Integer> emptyNameLength = getUserName(103) // Empty
                .map(String::length); // Does not execute map, remains empty
        System.out.println("Length of name 103: " + emptyNameLength.orElse(0)); // Output: 0

        // flatMap() - when the mapping function itself returns an Optional
        Function<String, Optional<String>> toUppercaseOptional =
                s -> s.isEmpty() ? Optional.empty() : Optional.of(s.toUpperCase());

        Optional<String> processedName = Optional.of("java")
                .flatMap(toUppercaseOptional);
        System.out.println("FlatMapped name: " + processedName.orElse("N/A")); // Output: JAVA

        Optional<String> emptyProcessedName = Optional.<String>empty()
                .flatMap(toUppercaseOptional);
        System.out.println("FlatMapped empty name: " + emptyProcessedName.orElse("N/A")); // Output: N/A

        // Chaining Optional operations
        String userStatus = getUserName(102) // Optional["Bob"]
                .filter(name -> name.startsWith("B")) // Optional["Bob"]
                .map(String::toUpperCase) // Optional["BOB"]
                .orElse("Unknown User"); // "BOB"
        System.out.println("\nUser 102 status: " + userStatus);

        String userStatusNotFound = getUserName(105) // Optional.empty
                .filter(name -> name.startsWith("B")) // Still Optional.empty
                .map(String::toUpperCase) // Still Optional.empty
                .orElse("User Not Found"); // "User Not Found"
        System.out.println("User 105 status: " + userStatusNotFound);
    }
}
