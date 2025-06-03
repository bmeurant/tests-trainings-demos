package io.bmeurant.java16.features;

public class LocalClassStatics {
    public static void main(String[] args) {
        // A method where a local class is defined
        demoLocalClassWithStatics();
    }

    public static void demoLocalClassWithStatics() {
        System.out.println("\nInside demoLocalClassWithStatics method:");

        // Before Java 16, this inner class could not have static members (except for final constants)
        class LocalCalculator {
            // Now, static fields are allowed
            public static final String VERSION = "1.0";
            private static int instanceCount = 0;

            // Now, static methods are allowed
            public static int add(int a, int b) {
                instanceCount++; // Static method can interact with static fields
                return a + b;
            }

            public static int subtract(int a, int b) {
                return a - b;
            }

            // A non-static method for completeness
            public int multiply(int a, int b) {
                return a * b;
            }

            // Nested static enum (implicitly static)
            public static enum OperationType {
                ADD, SUBTRACT, MULTIPLY
            }

            // Nested static interface (implicitly static)
            public static interface MathOperation {
                int operate(int x, int y);
            }
        }

        System.out.println("\n+++ Example 1: Using static members of the local class +++\n");
        System.out.println("LocalCalculator Version: " + LocalCalculator.VERSION);
        int sum = LocalCalculator.add(10, 5);
        System.out.println("10 + 5 = " + sum);
        System.out.println("LocalCalculator instances created (via static add): " + LocalCalculator.instanceCount);

        System.out.println("\n+++ Example 2: Using another static method +++\n");
        int difference = LocalCalculator.subtract(10, 5);
        System.out.println("10 - 5 = " + difference);

        System.out.println("\n+++ Example 3: Using the nested static enum +++\n");
        System.out.println("Operation Type: " + LocalCalculator.OperationType.ADD);

        System.out.println("\n+++ Example 4: Using the nested static interface +++\n");
        LocalCalculator.MathOperation addition = (x, y) -> x + y;
        System.out.println("Using MathOperation (lambda): 7 + 3 = " + addition.operate(7, 3));

        System.out.println("\n+++ Example 5: Using a non-static method +++\n");
        LocalCalculator calculator = new LocalCalculator();
        System.out.println("10 * 5 = " + calculator.multiply(10, 5));
    }
}
