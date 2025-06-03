package io.bmeurant.java11.features;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class CollectionToArrayImprovements {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        System.out.println("\nOriginal List: " + names);

        // --- Traditional way (Java 8 and earlier) ---
        // Requires passing an array of the desired type and size (often 0)
        String[] namesArrayOldWay = names.toArray(new String[0]);
        System.out.println("\nOld way (toArray(T[] a)): " + Arrays.toString(namesArrayOldWay));

        // If the array passed is too small, a new one of the correct size is created.
        // If it's too large, the extra elements are null.

        // --- New way (Java 11+) using IntFunction (often Method Reference) ---
        // Much cleaner and more readable
        String[] namesArrayNewWay = names.toArray(String[]::new); // Pass a method reference to a constructor that takes an int
        System.out.println("New way (toArray(IntFunction)): " + Arrays.toString(namesArrayNewWay));

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        Integer[] numbersArray = numbers.toArray(Integer[]::new);
        System.out.println("Numbers array (new way): " + Arrays.toString(numbersArray));

        // This new method simplifies code when working with Streams as well:
        Stream<String> streamOfNames = names.stream();
        String[] namesFromStream = streamOfNames.toArray(String[]::new);
        System.out.println("Names from stream to array (new way): " + Arrays.toString(namesFromStream));
    }
}
