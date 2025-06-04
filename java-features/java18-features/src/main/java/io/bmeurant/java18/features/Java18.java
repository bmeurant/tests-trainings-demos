package io.bmeurant.java18.features;

public class Java18 {
    public static void main(String[] args) {
        System.out.println("\n--- Java 18: UTF-8 by Default (JEP 400) ---");
        UTF8ByDefault.main(args);

        System.out.println("\n--- Java 18: Simple Web Server (JEP 408) ---\n");

        System.out.println("\n--- Java 18: Code Snippets in Java API Documentation (JEP 413) ---\n");
        SnippetDoc.main(args);

        System.out.println("\n--- Java 18: Deprecate Finalizers (JEP 421) ---\n");
        FinalizersDeprecated.main(args);
    }
}
