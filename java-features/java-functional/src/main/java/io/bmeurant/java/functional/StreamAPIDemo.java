package io.bmeurant.java.functional;

import java.util.Arrays;
import java.util.List;

import static java.lang.IO.println;

/**
 * Demonstrates the Stream API: filter, map, and reduce operations.
 */
public class StreamAPIDemo {

    void main() {
        var numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Functional processing pipeline
        int sumOfSquaresOfEvenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0) // Intermediate: Keep only even numbers (2, 4, 6, 8, 10)
                .map(n -> n * n)         // Intermediate: Square each number (4, 16, 36, 64, 100)
                .reduce(0, Integer::sum); // Terminal: Sum the results (4 + 16 + 36 + 64 + 100 = 220)

        println("The sum of squares of even numbers is: " + sumOfSquaresOfEvenNumbers);
        // Output: 220

        // Example using collect to gather results into a new List
        var doubledOddNumbers = numbers.stream()
                .filter(n -> n % 2 != 0) // Keep odd numbers
                .map(n -> n * 2)         // Double them
                .toList(); // Collect into a List

        println("Doubled odd numbers: " + doubledOddNumbers);
        // Output: [2, 6, 10, 14, 18]

        List<String> cities = Arrays.asList("Paris", "London", "Tokyo", "Berlin", "New York", "Los Angeles", "Rome");

        // Goal: Filter cities starting with 'L', convert them to uppercase, and collect the result.

        List<String> processedCities = cities.stream()
                // Intermediate Operation 1: Filter (using a Predicate)
                .filter(city -> city.startsWith("L"))

                // Intermediate Operation 2: Map (using a Function)
                .map(String::toUpperCase) // Method reference: equivalent to city -> city.toUpperCase()

                // Intermediate Operation 3: Peek (useful for debugging, shows an element without modifying the stream)
                .peek(c -> System.out.println("Processing: " + c))

                // Terminal Operation: Collect the results into a new List
                .toList();

        System.out.println("\nFinal List of Cities: " + processedCities);
        // Output: Final List of Cities: [LONDON, LOS ANGELES]

        // Another example: Reduce to count characters
        int totalCharacters = cities.stream()
                .filter(city -> city.length() > 4)
                .mapToInt(String::length) // Map to IntStream to sum primitive ints
                .sum(); // Terminal operation: sum all elements

        System.out.println("\nTotal characters in cities longer than 4 letters: " + totalCharacters);
    }
}
