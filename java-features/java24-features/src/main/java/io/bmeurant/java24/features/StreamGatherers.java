package io.bmeurant.java24.features;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;

public class StreamGatherers {
    public static void main(String[] args) {
        // Example 1: Fixed-size windowing (chunking)
        // Gatherer to group elements into fixed-size windows
        System.out.println("\n+++ Example 1: Fixed-size windowing (chunking) with Gatherers.windowFixed +++");
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<List<Integer>> chunks = numbers.stream()
                .gather(Gatherers.windowFixed(3)) // Group by 3 elements
                .collect(Collectors.toList());
        System.out.println("Original numbers: " + numbers);
        System.out.println("Chunks of 3: " + chunks);
        // Expected: [[1, 2, 3], [4, 5, 6], [7, 8, 9], [10]]


        // Example 2: Conditional windowing (custom gatherer simulating windowWhile)
        // This gatherer collects elements into a list as long as a condition is true.
        // When the condition becomes false for an element, it emits the collected list
        // and then emits the element that broke the condition as its own group.
        System.out.println("\n+++Example 2: Conditional windowing (custom gatherer simulating windowWhile) +++");
        List<String> words = List.of("apple", "banana", "cat", "dog", "elephant", "fig", "grape");
        Predicate<String> condition = s -> s.length() < 6;

        // Custom Gatherer for windowWhile behavior
        // S = state type (ArrayList<String> for the current window)
        // T = input type (String)
        // D = output type (List<String>)
        Gatherer<String, ArrayList<String>, List<String>> windowWhileGatherer =
                Gatherer.ofSequential(
                        () -> new ArrayList<String>(), // Initializer: creates the mutable state for the current window
                        (currentWindow, element, downstream) -> { // Integrator: processes each element
                            if (condition.test(element)) {
                                currentWindow.add(element);
                                return true; // Continue processing (keep currentWindow for next element)
                            } else {
                                // Condition failed for 'element'
                                // Emit the current accumulated window if not empty
                                if (!currentWindow.isEmpty()) {
                                    downstream.push(List.copyOf(currentWindow)); // Use push() for Downstream
                                    currentWindow.clear(); // Reset the window for new elements
                                }
                                // Emit the element that broke the condition as a single-element window
                                downstream.push(List.of(element)); // Use push() for Downstream
                                return false; // Signal that this sequence is done, new state for next elements
                            }
                        },
                        (currentWindow, downstream) -> { // Finisher: processes any remaining elements in the last window
                            if (!currentWindow.isEmpty()) {
                                downstream.push(List.copyOf(currentWindow)); // Use push() for Downstream
                            }
                        }
                );

        List<List<String>> groups = words.stream()
                .gather(windowWhileGatherer)
                .collect(Collectors.toList());

        System.out.println("Original words: " + words);
        System.out.println("Groups by length < 6 (custom gatherer): " + groups);
        // Expected: [[apple, banana, cat, dog], [elephant], [fig, grape]]


        // Example 3: Create a custom gatherer for consecutive deduplication
        // (Keep only the first element of series of identical consecutive elements)
        System.out.println("\n+++ Example 3: Custom gatherer for consecutive deduplication +++");
        List<String> itemsWithDuplicates = List.of("A", "A", "B", "B", "B", "C", "A", "A");

        // State for the gatherer: a single-element array to hold the "previous" item.
        // Using an array allows us to modify the reference inside the lambda, which is needed
        // since the state variable 'state' itself is effectively final.
        Gatherer<String, String[], String> consecutiveDeduplicator =
                Gatherer.ofSequential(
                        () -> new String[]{null}, // Initializer: State is a String array of size 1, initially null
                        (state, element, downstream) -> { // Integrator: state is the String array
                            String previousElement = state[0]; // Get the previous element
                            if (previousElement == null || !previousElement.equals(element)) {
                                downstream.push(element); // Use push() for Downstream
                                state[0] = element; // Update the previous element in state
                            }
                            return true; // Always continue processing for this gatherer
                        },
                        (state, downstream) -> { /* Finisher: no action needed for this gatherer */ }
                );

        List<String> distinctConsecutive = itemsWithDuplicates.stream()
                .gather(consecutiveDeduplicator)
                .collect(Collectors.toList());

        System.out.println("Items with consecutive duplicates: " + itemsWithDuplicates);
        System.out.println("Consecutively deduplicated items: " + distinctConsecutive);
        // Expected: [A, B, C, A]
    }
}
