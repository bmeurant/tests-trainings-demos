package io.bmeurant.java11.features;

import java.util.List;
import java.util.stream.Collectors;

public class StringNewMethods {

    public static void main(String[] args) {

        // --- 1. isBlank() ---
        String blankString1 = "   "; // Contains only whitespace
        String blankString2 = "";    // Empty string
        String nonBlankString = "  Hello  ";
        String mixedString = " \t\n "; // Various whitespace characters

        System.out.println("\n+++ Example 1: isBlank() +++\n");
        System.out.println("'" + blankString1 + "'.isBlank(): " + blankString1.isBlank());     // true
        System.out.println("'" + blankString2 + "'.isBlank(): " + blankString2.isBlank());     // true
        System.out.println("'" + nonBlankString + "'.isBlank(): " + nonBlankString.isBlank()); // false
        System.out.println("'" + mixedString + "'.isBlank(): " + mixedString.isBlank());       // true
        System.out.println("'" + blankString1 + "'.isEmpty(): " + blankString1.isEmpty());     // false (length > 0)

        // --- 2. lines() ---
        String multiLineText = "Line 1\nLine 2\rLine 3\r\nLine 4";
        System.out.println("\n+++ Example 1: lines() +++\n");
        System.out.println("Original multiline text:\n" + multiLineText);
        System.out.println("Lines from text:");
        multiLineText.lines().forEach(line -> System.out.println("  > " + line));

        List<String> collectedLines = multiLineText.lines()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("Collected lines (uppercase): " + collectedLines);


        // --- 3. strip(), stripLeading(), stripTrailing() ---
        String stringWithWhitespace = "  \t Hello World!   \n ";
        System.out.println("\n+++ Example 3: strip(), stripLeading(), stripTrailing() +++\n");
        System.out.println("Original string: '" + stringWithWhitespace + "'");
        System.out.println("'.trim()':        '" + stringWithWhitespace.trim() + "'");          // Removes <= U+0020
        System.out.println("'.strip()':       '" + stringWithWhitespace.strip() + "'");         // Removes all Unicode whitespace
        System.out.println("'.stripLeading()':'" + stringWithWhitespace.stripLeading() + "'");  // Removes leading Unicode whitespace
        System.out.println("'.stripTrailing()':'" + stringWithWhitespace.stripTrailing() + "'");// Removes trailing Unicode whitespace

        // Example with a tricky Unicode whitespace (U+2000 EN SPACE)
        String unicodeWhitespaceString = "\u2000 Hello Unicode \u2000";
        System.out.println("Unicode string: '" + unicodeWhitespaceString + "'");
        System.out.println("'.trim()':        '" + unicodeWhitespaceString.trim() + "'");       // Doesn't remove U+2000
        System.out.println("'.strip()':       '" + unicodeWhitespaceString.strip() + "'");      // Removes U+2000


        // --- 4. repeat(int count) ---
        String textToRepeat = "Java ";
        System.out.println("\n+++ Example 4: repeat(int count)+++\n");
        System.out.println("'" + textToRepeat + "'.repeat(3): '" + textToRepeat.repeat(3) + "'");
        System.out.println("'-'.repeat(20): " + "-".repeat(20));
    }
}
