package io.bmeurant.java10.features;

public class Java10 {
    public static void main(String[] args) {
        System.out.println("\n--- Java 10: Local Variable Type Inference (var) (JEP 286) ---");
        LocalVariableTypeInference.main(args);

        System.out.println("\n--- Java 10: Optional API Improvements ---");
        OptionalImprovements.main(args);
    }
}
