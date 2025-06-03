package io.bmeurant.java16.features;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;

public class StrongEncapsulation {
    public static void main(String[] args) {
        System.out.println("\nAttempting to access an internal JDK API via reflection.");
        System.out.println("This should now throw an InaccessibleObjectException by default in Java 16+.");

        try {
            // We're trying to access a field from 'System.out' which is a PrintStream
            // and then try to make its internal 'textOut' field accessible.
            // 'textOut' is an internal field of PrintStream (which is in java.base/java.io)
            // that is NOT exported by the 'java.base' module.
            // This is a classic example of attempting to access a non-exported field.
            Field textOutField = Class.forName("java.io.PrintStream").getDeclaredField("textOut");

            // Attempting to make it accessible will now throw InaccessibleObjectException
            // if the 'java.io' package of 'java.base' module is not opened to the unnamed module
            // (which is your application's module).
            textOutField.setAccessible(true); // This line is expected to throw the exception

            System.out.println("Successfully accessed internal field (this should NOT happen without --add-opens): " + textOutField.getName());

        } catch (NoSuchFieldException e) {
            System.err.println("Error: Field not found (should exist in PrintStream): " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Class 'java.io.PrintStream' not found: " + e.getMessage());
        } catch (InaccessibleObjectException e) {
            // This is the expected exception in Java 16+ without --add-opens
            System.err.println("\nSUCCESS! Caught expected InaccessibleObjectException:");
            System.err.println("  " + e.getMessage());
            System.err.println("This demonstrates strong encapsulation: You cannot directly access non-exported internals.");
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            System.err.println("Caught an unexpected exception: " + e.getClass().getName() + " - " + e.getMessage());
        }
    }
}
