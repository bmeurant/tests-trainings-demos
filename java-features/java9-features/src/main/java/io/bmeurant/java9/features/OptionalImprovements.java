package io.bmeurant.java9.features;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionalImprovements {

    public static Optional<String> findUserById(int id) {
        if (id == 1) {
            return Optional.of("Alice");
        } else if (id == 2) {
            return Optional.of("Bob");
        }
        return Optional.empty();
    }

    public static void main(String[] args) {

        System.out.println("--- Example 1: ifPresentOrElse example ---");
        Optional<String> user1 = findUserById(1);
        user1.ifPresentOrElse(
                name -> System.out.println("User found: " + name),
                () -> System.out.println("User not found for ID 1.")
        );

        Optional<String> user3 = findUserById(3);
        user3.ifPresentOrElse(
                name -> System.out.println("User found: " + name),
                () -> System.out.println("User not found for ID 3.")
        );

        System.out.println("\n--- Example 2: or example ---");
        Optional<String> result1 = findUserById(1)
                .or(() -> Optional.of("GuestUser")); // 'or' won't be called as user1 is present
        System.out.println("Result for ID 1: " + result1.get());

        Optional<String> result4 = findUserById(4)
                .or(() -> Optional.of("DefaultUser")); // 'or' will be called as user4 is empty
        System.out.println("Result for ID 4: " + result4.get());

        Optional<String> result5 = findUserById(5)
                .or(Optional::empty); // If empty, return another empty Optional
        System.out.println("Result for ID 5 (is present?): " + result5.isPresent());

        System.out.println("\n--- Example 3: stream() example ---");

        // Convert an Optional to a Stream and collect elements
        String collectedUsers = Stream.of(findUserById(1), findUserById(3), findUserById(2))
                .flatMap(Optional::stream) // FlatMap Optional to Stream
                .collect(Collectors.joining(", "));
        System.out.println("Collected users from Optional streams: " + collectedUsers);

        // Using stream() directly on an Optional
        Optional.of("Hello").stream().forEach(s -> System.out.println("Stream from present Optional: " + s));
        Optional.empty().stream().forEach(s -> System.out.println("This won't print for empty Optional."));
    }
}
