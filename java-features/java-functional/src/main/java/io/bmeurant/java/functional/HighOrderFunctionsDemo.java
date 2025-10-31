package io.bmeurant.java.functional;

import java.util.List;
import java.util.function.Predicate;

import static java.lang.IO.println;

/**
 * Demonstrates Higher-Order Functions by accepting a Predicate (a function)
 * to filter elements from a list.
 */
public class HighOrderFunctionsDemo {

    /**
     * A Higher-Order Function that filters a list based on a given predicate.
     * @param numbers The list of integers to filter.
     * @param condition The predicate (function) to apply for filtering.
     * @return A new list containing elements that satisfy the condition.
     */
    public List<Integer> filterList(List<Integer> numbers, Predicate<Integer> condition) {
        // Use the Stream API for functional filtering
        return numbers.stream()
                .filter(condition) // 'condition' is the passed function
                .toList();
    }

    void main() {
        var hofDemo = new HighOrderFunctionsDemo();
        var numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Define a function (Predicate) to check for even numbers
        Predicate<Integer> isEven = number -> number % 2 == 0;

        // Call the Higher-Order Function, passing the 'isEven' function as an argument
        var evenNumbers = hofDemo.filterList(numbers, isEven);

        // Use 'println' from the simplified main (implicitly java.lang.IO.println)
        println("Original list: " + numbers);
        println("Filtered even numbers: " + evenNumbers);

        // Example using an inline lambda
        var oddNumbers = hofDemo.filterList(numbers, number -> number % 2 != 0);
        println("Filtered odd numbers: " + oddNumbers);
    }
}
