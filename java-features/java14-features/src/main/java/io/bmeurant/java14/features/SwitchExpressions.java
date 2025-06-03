package io.bmeurant.java14.features;

public class SwitchExpressions {

    public static void main(String[] args) {

        System.out.println("\n+++ Example 1: Basic switch expression with '->' +++\n");
        // Directly returns a value. No 'break' needed.
        String dayCategory = getDayCategory("MONDAY");
        System.out.println("Category for MONDAY: " + dayCategory);

        dayCategory = getDayCategory("SATURDAY");
        System.out.println("Category for SATURDAY: " + dayCategory);

        dayCategory = getDayCategory("InvalidDay");
        System.out.println("Category for InvalidDay: " + dayCategory);

        System.out.println("\n+++ Example 2: Using 'yield' for multi-line blocks +++\n");
        // The code block requires 'yield' to produce a value.
        int lengthIndicator = getLengthIndicator("Banana");
        System.out.println("Length indicator for 'Banana': " + lengthIndicator);

        lengthIndicator = getLengthIndicator("Apple");
        System.out.println("Length indicator for 'Apple': " + lengthIndicator);

        lengthIndicator = getLengthIndicator("Kiwi");
        System.out.println("Length indicator for 'Kiwi': " + lengthIndicator);
    }

    // Method using a switch expression to return a value
    public static String getDayCategory(String day) {
        return switch (day) {
            case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> "Weekday";
            case "SATURDAY", "SUNDAY" -> "Weekend";
            default -> "Unknown"; // 'default' is mandatory for exhaustiveness if not all cases are covered.
        };
    }

    // Method using a switch expression with code blocks and 'yield'
    public static int getLengthIndicator(String word) {
        return switch (word.length()) {
            case 1, 2, 3 -> { // For short words
                System.out.println("Short word detected: " + word);
                yield 1; // Produces the value 1
            }
            case 4, 5, 6 -> { // For medium length words
                System.out.println("Medium word detected: " + word);
                yield 2; // Produces the value 2
            }
            default -> { // For long or unrecognized words
                System.out.println("Long or unrecognized word: " + word);
                yield 3; // Produces the value 3
            }
        };
    }
}