package io.bmeurant.java11.features;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class NestBasedAccessControl {

    public static void main(String[] args) {

        // Demonstrate a simple nested class
        OuterClass outer = new OuterClass();
        OuterClass.InnerClass inner = outer.new InnerClass();

        inner.callPrivateMethodOfOuter();
        inner.accessPrivateFieldOfOuter();

        System.out.println("\n+++ Reflection API for Nests +++");

        Class<?> outerClass = OuterClass.class;
        Class<?> innerClass = OuterClass.InnerClass.class;

        System.out.println("\n+++ Example 1: isNestmateOf(Class<?> c) +++\n");

        System.out.println("Is OuterClass a nestmate of InnerClass? " + outerClass.isNestmateOf(innerClass));
        System.out.println("Is InnerClass a nestmate of OuterClass? " + innerClass.isNestmateOf(outerClass));
        System.out.println("Is OuterClass a nestmate of String? " + outerClass.isNestmateOf(String.class));

        System.out.println("\n+++ Example 2: getNestHost() +++\n");

        System.out.println("Nest host of OuterClass: " + outerClass.getNestHost().getName());
        System.out.println("Nest host of InnerClass: " + innerClass.getNestHost().getName());
        // For a top-level class, getNestHost() returns itself. For nested classes, it returns the top-level class.

        System.out.println("\n+++ Example 2: getNestMembers() +++\n");

        // Returns a Set of Class objects that are part of the same nest.
        Set<String> outerNestMembers = Arrays.stream(outerClass.getNestMembers())
                .map(Class::getName)
                .collect(Collectors.toSet());
        System.out.println("Nest members of OuterClass: " + outerNestMembers);

        Set<String> innerNestMembers = Arrays.stream(innerClass.getNestMembers())
                .map(Class::getName)
                .collect(Collectors.toSet());
        System.out.println("Nest members of InnerClass: " + innerNestMembers);
        // Notice how both outer and inner classes report the same set of nest members.

        // --- Impact on private member access via Reflection (without setAccessible(true)) ---
        // Before Java 11, accessing private members of outer class from inner class
        // via reflection without setAccessible(true) would often throw IllegalAccessException.
        // With Nest-Based Access Control, this is now permitted for nestmates.

        try {
            // Get the private method
            Method privateMethod = outerClass.getDeclaredMethod("privateOuterMethod");
            // privateMethod.setAccessible(true); // No longer strictly needed for nestmates in Java 11+
            // if reflecting from a nestmate. However, generally good practice.

            // If we try to invoke it from the OuterClass instance:
            privateMethod.setAccessible(true); // Still required for external access
            privateMethod.invoke(outer);


            // Get the private field
            Field privateField = outerClass.getDeclaredField("privateOuterField");
            // privateField.setAccessible(true); // No longer strictly needed for nestmates in Java 11+
            // if reflecting from a nestmate.

            privateField.setAccessible(true); // Still required for external access
            System.out.println("Accessing private field via reflection (OuterClass): " + privateField.get(outer));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            System.err.println("Error accessing private members via reflection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Inner class demonstration
    static class OuterClass {
        private String privateOuterField = "Outer private field value";

        private void privateOuterMethod() {
            System.out.println("OuterClass: privateOuterMethod called.");
        }

        // A non-static inner class is automatically a nestmate
        class InnerClass {
            public void callPrivateMethodOfOuter() {
                // This direct access is always allowed by Java compiler,
                // but before Java 11, it relied on compiler-generated bridge methods.
                // In Java 11+, it's direct JVM-level access.
                privateOuterMethod();
            }

            public void accessPrivateFieldOfOuter() {
                System.out.println("InnerClass accessing outer private field: " + privateOuterField);
            }
        }

        // Static nested class is also a nestmate
        static class StaticNestedClass {
            // Cannot directly access non-static private members of OuterClass instance.
            // But can access static private members if any.
        }
    }
}
