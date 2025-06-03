package io.bmeurant.java16.features;

public class PatternMatchingInstanceof {
    public static void main(String[] args) {

        Object obj1 = "Hello Java 16!";
        Object obj2 = 42;
        Object obj3 = null;
        Object obj4 = new StringBuilder("World");

        System.out.println("\n+++ Example 1: Basic String pattern matching +++");
        handleObject(obj1);
        handleObject(obj2);
        handleObject(obj3); // Demonstrates how null is handled

        System.out.println("\n+++ Example 2: Pattern matching with multiple conditions +++");
        processIfStringAndLong(obj1);
        processIfStringAndLong("short");

        System.out.println("\n+++ Example 3: Different type +++");
        processStringBuilder(obj4);
        processStringBuilder(obj2);
    }

    public static void handleObject(Object obj) {
        System.out.println("\nProcessing object: " + obj);
        if (obj instanceof String s) { // Pattern variable 's' is available
            System.out.println("  It's a String! Length: " + s.length());
            System.out.println("  Uppercase: " + s.toUpperCase());
        } else if (obj instanceof Integer i) { // Pattern variable 'i' is available
            System.out.println("  It's an Integer! Value: " + i);
            System.out.println("  Squared: " + (i * i));
        } else if (obj == null) {
            System.out.println("  It's null. instanceof returns false for null.");
        } else {
            System.out.println("  It's another type: " + obj.getClass().getName());
        }
    }

    public static void processIfStringAndLong(Object obj) {
        if (obj instanceof String s && s.length() > 10) { // Combined with other conditions
            System.out.println("\nString is long: " + s);
        } else {
            System.out.println("\nString is not long or not a String.");
        }
    }

    public static void processStringBuilder(Object obj) {
        if (obj instanceof StringBuilder sb) {
            System.out.println("\nStringBuilder found. Original: " + sb + ", Reversed: " + sb.reverse());
        } else {
            System.out.println("\nObject is not a StringBuilder.");
        }
    }
}
