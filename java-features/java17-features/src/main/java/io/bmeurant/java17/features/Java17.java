package io.bmeurant.java17.features;

public class Java17 {
    public static void main(String[] args) {
        System.out.println("\n--- Java 17: Sealed Classes and Interfaces (JEP 409) ---");
        SealedTypes.main(args);

        System.out.println("\n--- Java 17: Restore Always-Strict Floating-Point Semantics (JEP 306)  ---");
        StrictFloatingPoint.main(args);
    }
}
