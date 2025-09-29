package io.bmeurant.java25.features;

public class Java25 {
    public static void main(String[] args) throws Exception {
        System.out.println("\n--- Java 25: Scoped Values (JEP 506) ---");
        ScopedValues.main(args);

        System.out.println("\n--- Java 25: Module Import Declaration (JEP 511) ---");
        ModuleImportDeclaration.main(args);
    }
}
