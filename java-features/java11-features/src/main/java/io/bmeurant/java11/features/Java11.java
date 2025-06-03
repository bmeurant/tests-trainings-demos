package io.bmeurant.java11.features;

public class Java11 {
    public static void main(String[] args) {
        System.out.println("\n---- Java 11: New String Methods ---");
        StringNewMethods.main(args);

        System.out.println("\n--- Java 11: Optional API Improvements ---");
        OptionalImprovements.main(args);

        System.out.println("\n--- Java 11: Collection.toArray() Improvements ---");
        CollectionToArrayImprovements.main(args);

        System.out.println("\n--- Java 11: Files.readString() & writeString() ---");
        FilesReadWriteString.main(args);

        System.out.println("\n--- Java 11: HTTP Client API (Standard) ---");
        HttpClientAPI.main(args);

        System.out.println("\n--- Java 11: Nest-Based Access Control ---");
        NestBasedAccessControl.main(args);

        System.out.println("\n--- Java 11: Local-Variable Syntax for Lambda Parameters (var) ---");
        LambdaVarParameters.main(args);

    }
}
