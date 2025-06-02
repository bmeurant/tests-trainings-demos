package io.bmeurant.java.features.java8;

import java.util.*;
import java.util.stream.Collectors;

public class StreamAPI {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve", "Frank");
        System.out.println("\n>>>>>> Original list of names: " + names);

        List<Integer> numbers = Arrays.asList(5, 2, 8, 2, 5, 1, 9);
        System.out.println("\n>>>>>> Original list of numbers: " + numbers);

        System.out.println("\n+++ Example 1: Filtering and ForEach +++\n");
        filteringAndForEach(names);

        System.out.println("\n+++ Example 2: Mapping and Collecting to a new List +++\n");
        mappingAndCollecting(names);

        System.out.println("\n+++ Example 3: Counting elements +++\n");
        counting(names);

        System.out.println("\n+++ Example 4: Distinct and Sorted +++\n");
        distinctAndSorted(numbers);

        System.out.println("\n+++ Example 5: Reduction (summing elements) +++\n");
        reduction(numbers);

        System.out.println("\n+++ Example 6: Grouping elements (Collectors.groupingBy) +++\n");
        grouping();

        System.out.println("\n+++ Example 7: Parallel Stream +++\n");
        parallelStream(numbers);

        System.out.println("\n+++ Example 8: Converting to Set +++\n");
        convertToSet(numbers);
    }

    private static void convertToSet(List<Integer> numbers) {
        Set<Integer> uniqueNumbers = numbers.stream()
                .collect(Collectors.toSet());
        System.out.println("Unique numbers as a Set: " + uniqueNumbers);
    }

    private static void parallelStream(List<Integer> numbers) {
        // For large datasets, parallel streams can leverage multiple CPU cores.
        // Be careful with mutable state and ensure operations are stateless and non-interfering.
        long startTimeSequential = System.nanoTime();
        long sequentialSum = numbers.stream()
                .mapToLong(Integer::longValue)
                .sum();
        long endTimeSequential = System.nanoTime();
        System.out.println("Sequential sum: " + sequentialSum + " (Time: " + (endTimeSequential - startTimeSequential) + " ns)");

        long startTimeParallel = System.nanoTime();
        long parallelSum = numbers.parallelStream() // Use parallelStream() for parallel processing
                .mapToLong(Integer::longValue)
                .sum();
        long endTimeParallel = System.nanoTime();
        System.out.println("Parallel sum: " + parallelSum + " (Time: " + (endTimeParallel - startTimeParallel) + " ns)");

        // Note: For small lists like this, parallel stream overhead might make it slower.
        // Parallel streams are beneficial for large datasets and CPU-bound operations.
    }

    private static void grouping() {
        List<String> fruits = Arrays.asList("apple", "banana", "apricot", "berry", "cherry");
        System.out.println(">>>>>> Original list of fruits: " + fruits);

        Map<Character, List<String>> fruitsByFirstLetter =
                fruits.stream()
                        .collect(Collectors.groupingBy(s -> s.charAt(0))); // Terminal operation: group elements
        System.out.println("\nFruits grouped by first letter: " + fruitsByFirstLetter);
    }

    private static void reduction(List<Integer> numbers) {
        Optional<Integer> sum = numbers.stream()
                .reduce((a, b) -> a + b); // Terminal operation: reduce elements to a single result
        sum.ifPresent(s -> System.out.println("Total Sum (using Optional): " + s));

        // Alternative with initial value for sum (avoids Optional)
        int totalSum = numbers.stream()
                .reduce(0, (a, b) -> a + b);
        System.out.println("Total sum (using initial value): " + totalSum);
    }

    private static void distinctAndSorted(List<Integer> numbers) {
        List<Integer> distinctSortedNumbers = numbers.stream()
                .distinct() // Intermediate operation: remove duplicates
                .sorted()   // Intermediate operation: sort elements (natural order)
                .collect(Collectors.toList());
        System.out.println("Distinct and sorted numbers: " + distinctSortedNumbers);
    }

    private static void counting(List<String> names) {
        long countNamesWithE = names.stream()
                .filter(name -> name.contains("e"))
                .count(); // Terminal operation: count elements
        System.out.println("Number of names containing 'e': " + countNamesWithE);
    }

    private static void mappingAndCollecting(List<String> names) {
        // Convert names to uppercase and collect them into a new list
        List<String> upperCaseNames = names.stream()
                .map(String::toUpperCase) // Intermediate operation: transform elements
                .collect(Collectors.toList()); // Terminal operation: collect into a List
        System.out.println("Uppercase names: " + upperCaseNames);
    }

    private static void filteringAndForEach(List<String> names) {
        // Filter names starting with 'A' and print them
        System.out.println("Names starting with 'A':");
        names.stream() // Convert the list to a stream
                .filter(name -> name.startsWith("A")) // Intermediate operation: filter elements
                .forEach(System.out::println); // Terminal operation: print each element
    }
}
