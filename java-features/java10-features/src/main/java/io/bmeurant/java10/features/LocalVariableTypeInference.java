package io.bmeurant.java10.features;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LocalVariableTypeInference {

    public static void main(String[] args) {

        System.out.println("\n+++ Example 1: Basic usage with primitive types  +++\n");

        var message = "Hello, Java 10!"; // Type inferred as String
        System.out.println("Message (type inferred as String): " + message);

        var number = 123; // Type inferred as int
        System.out.println("Number (type inferred as int): " + number);

        var pi = 3.14159; // Type inferred as double
        System.out.println("Pi (type inferred as double): " + pi);


        System.out.println("\n+++ Example 2: With collections (reducing verbosity)  +++\n");

        // Traditional way (Java 9 and before):
        List<String> traditionalList = new ArrayList<>();
        traditionalList.add("Apple");
        traditionalList.add("Banana");
        System.out.println("Traditional List: " + traditionalList);

        // Using var (Java 10+):
        var fruits = new ArrayList<String>(); // Type inferred as ArrayList<String>
        fruits.add("Orange");
        fruits.add("Grape");
        System.out.println("Fruits (type inferred as ArrayList<String>): " + fruits);

        var fruitMap = new HashMap<String, Integer>(); // Type inferred as HashMap<String, Integer>
        fruitMap.put("Apple", 1);
        fruitMap.put("Orange", 2);
        System.out.println("Fruit Map (type inferred as HashMap<String, Integer>): " + fruitMap);

        System.out.println("\n+++ Example 3: With complex types or anonymous classes +++\n");

        // This is where 'var' shines by avoiding long type names
        var longTypeNameVariable = new HashMap<String, List<Set<Integer>>>();
        longTypeNameVariable.put("key", new ArrayList<>());
        longTypeNameVariable.get("key").add(Set.of(1, 2, 3));
        System.out.println("Complex type with var: " + longTypeNameVariable);

        // With anonymous inner classes
        var runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("This is an anonymous runnable.");
            }
        };
        new Thread(runnable).start(); // Type inferred as anonymous class type

        System.out.println("\n+++ Example 4: In loops and try-with-resources +++\n");
        for (var fruit : fruits) { // Type inferred as String
            System.out.println("Iterating fruit: " + fruit);
        }

        // In try-with-resources (Java 9+ for local variable syntax, var in Java 10+)
        try (var reader = new BufferedReader(
                new InputStreamReader(
                        LocalVariableTypeInference.class.getClassLoader().getResourceAsStream("sample.txt")
                ))) {
            // For this to run, create a 'resources' folder in 'src/main/'
            // and a 'sample.txt' file inside it.
            // Or simply change the path to a non-existent file to see the catch block.
            var line = reader.readLine(); // Type inferred as String
            System.out.println("Read line from file: " + line);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            System.out.println("Please ensure 'sample.txt' is in your 'src/main/resources' folder.");
        } catch (NullPointerException e) { // Catches if getResourceAsStream returns null (file not found)
            System.out.println("Error: The resource file 'sample.txt' was not found on the classpath.");
            System.out.println("Please ensure 'sample.txt' is in your 'src/main/resources' folder.");
        }

        System.out.println("\n+++ Example 5: Streams with var +++\n");
        var filteredFruits = fruits.stream() // Stream<String>
                .filter(f -> f.length() > 5)
                .collect(Collectors.toList()); // List<String>
        System.out.println("Filtered fruits (length > 5): " + filteredFruits);


        // --- Limitations of 'var' (will cause compile errors if uncommented) ---
        // var uninitialized; // Error: Cannot infer type for local variable initialized to 'null' or with no initializer
        // var array = {1, 2, 3}; // Error: Array initializer not allowed for 'var'

        // var cannot be used for method parameters:
        // public void process(var data) {}

        // var cannot be used for return types:
        // public var getData() { return "data"; }

        // var cannot be used for fields:
        // private var myField = "value";
    }
}
