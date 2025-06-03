package io.bmeurant.java17.features;

public class StrictFloatingPoint {
    // A class to demonstrate calculations
    static class Calculator {
        public double calculate(double a, double b, double c) {
            // This calculation's precision will now be strictly adhered to by default
            // across different JVMs running on different hardware.
            return (a * b) / c;
        }
    }

    public static void main(String[] args) {
        System.out.println("\nThis feature ensures floating-point calculations are strictly reproducible by default.");

        Calculator calc = new Calculator();
        double val1 = 0.1;
        double val2 = 0.2;
        double val3 = 0.3;

        // Perform some calculations. The key is that the results will be identical
        // regardless of the underlying processor's extended precision capabilities
        // unless explicitly configured otherwise via JVM options.
        double result1 = calc.calculate(val1, val2, val3);
        double result2 = calc.calculate(0.0000000000000001, 0.0000000000000002, 0.0000000000000003);

        System.out.println("\nCalculation 1: (0.1 * 0.2) / 0.3 = " + result1);
        System.out.println("Calculation 2: (1e-17 * 2e-17) / 3e-17 = " + result2);

        System.out.println("\nThe strict floating-point semantics guarantee these results are precisely reproducible.");
        System.out.println("No 'strictfp' keyword is needed for this behavior by default in Java 17+.");
    }
}
