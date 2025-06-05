package io.bmeurant.java21.features;

import java.util.*;

public class SequencedCollections {
    public static void main(String[] args) {
        System.out.println("\n+++ List Operations Comparison +++");

        // OLD WAY (List): Inconsistent access
        System.out.println("\n=== OLD WAY (List) ===");
        List<String> oldList = new LinkedList<>(); // LinkedList has addFirst/Last, but ArrayList doesn't
        oldList.add("A");
        oldList.add("B");
        oldList.add("C");
        System.out.println("Original List: " + oldList);
        System.out.println("First (LinkedList specific): " + ((LinkedList<String>) oldList).getFirst());
        System.out.println("Last (LinkedList specific): " + ((LinkedList<String>) oldList).getLast());
        // No standard way to reverse a view without creating a new list or iterating
        List<String> reversedOldList = new ArrayList<>(oldList);
        Collections.reverse(reversedOldList);
        System.out.println("Reversed (manual copy & reverse): " + reversedOldList);


        // NEW WAY (SequencedCollection): Uniform access
        System.out.println("\n=== NEW WAY (SequencedCollection) ===");
        SequencedCollection<String> newList = new LinkedList<>(); // LinkedList now implements SequencedCollection
        newList.add("A");
        newList.addFirst("Z"); // Uniform method
        newList.addLast("D");  // Uniform method
        System.out.println("Original SequencedCollection: " + newList);
        System.out.println("First element: " + newList.getFirst()); // Uniform method
        System.out.println("Last element: " + newList.getLast());   // Uniform method

        SequencedCollection<String> reversedNewList = newList.reversed(); // New standard method for reversed view
        System.out.println("Reversed view: " + reversedNewList);
        newList.removeFirst(); // Uniform method
        newList.removeLast();  // Uniform method
        System.out.println("After removals: " + newList);


        System.out.println("\n+++ Set Operations Comparison +++");

        // OLD WAY (Set): No standard first/last or reversal for most Sets
        System.out.println("\n=== OLD WAY (Set) ===");
        Set<Integer> oldSet = new LinkedHashSet<>(); // LinkedHashSet maintains insertion order
        oldSet.add(30);
        oldSet.add(10);
        oldSet.add(20);
        System.out.println("Original Set: " + oldSet);
        // Accessing first/last elements is not standard for Set interface, usually done via iterators.
        // No standard way to get a reversed view.

        // NEW WAY (SequencedSet):
        System.out.println("\n=== NEW WAY (SequencedSet) ===");
        SequencedSet<Integer> newSet = new LinkedHashSet<>(); // LinkedHashSet now implements SequencedSet
        newSet.add(30);
        newSet.add(10);
        newSet.add(20);
        System.out.println("Original SequencedSet: " + newSet);
        System.out.println("First element: " + newSet.getFirst()); // New uniform method
        System.out.println("Last element: " + newSet.getLast());   // New uniform method
        SequencedSet<Integer> reversedNewSet = newSet.reversed();
        System.out.println("Reversed view: " + reversedNewSet);


        System.out.println("\n+++ Map Operations Comparison +++");

        // OLD WAY (Map): No standard first/last entry access or reversal for Maps
        System.out.println("\n=== OLD WAY (Map) ===");
        Map<String, String> oldMap = new LinkedHashMap<>(); // LinkedHashMap maintains insertion order
        oldMap.put("C", "Charlie");
        oldMap.put("A", "Alpha");
        oldMap.put("B", "Beta");
        System.out.println("Original Map: " + oldMap);
        // No standard methods for first/last entry or reversal.
        // For TreeMap, one might use firstEntry(), lastEntry(), descendingKeySet() etc., but not for others.

        // NEW WAY (SequencedMap):
        System.out.println("\n=== NEW WAY (SequencedMap) ===");
        SequencedMap<String, String> newMap = new LinkedHashMap<>(); // LinkedHashMap now implements SequencedMap
        newMap.put("C", "Charlie");
        newMap.putFirst("Z", "Zulu"); // New uniform method
        newMap.putLast("D", "Delta"); // New uniform method
        newMap.put("A", "Alpha"); // Will be inserted at the end of existing elements (LinkedHashMap behavior)
        newMap.put("B", "Beta");
        System.out.println("Original SequencedMap: " + newMap);
        System.out.println("First entry: " + newMap.firstEntry()); // New uniform method
        System.out.println("Last entry: " + newMap.lastEntry());   // New uniform method
        SequencedMap<String, String> reversedNewMap = newMap.reversed();
        System.out.println("Reversed view: " + reversedNewMap);
        newMap.pollFirstEntry(); // New uniform method
        newMap.pollLastEntry();  // New uniform method
        System.out.println("After removals: " + newMap);
    }
}
