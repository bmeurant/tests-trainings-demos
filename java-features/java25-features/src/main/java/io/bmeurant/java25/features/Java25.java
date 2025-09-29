package io.bmeurant.java25.features;

public class Java25 {
    public static void main(String[] args) throws Exception {
        System.out.println("\n--- Java 25: Scoped Values (JEP 506) ---\n");
        ScopedValues.main(args);

        System.out.println("\n--- Java 25: Module Import Declaration (JEP 511) ---\n");
        ModuleImportDeclaration.main(args);

        System.out.println("\n--- Java 25: Compact Source Files and Instance Main Methods (JEP 512) ---\n");
        System.out.println("Run CompactMain file:  java -classpath target/classes CompactMain \"Hello\"");
    }
}
