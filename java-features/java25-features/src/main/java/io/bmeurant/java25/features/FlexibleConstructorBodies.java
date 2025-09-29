package io.bmeurant.java25.features;

public class FlexibleConstructorBodies {
    private final String name;
    private final int value;

    // Primary delegated constructor (initializes all final fields)
    public FlexibleConstructorBodies(String name, int value) {
        this.name = name;
        this.value = value;
        System.out.println("Primary constructor finished for: " + name);
    }

    // NEW (JEP 513): Flexible Constructor
    public FlexibleConstructorBodies(String name) {

        // 1. Preliminary statements (Validation and pre-computation are allowed here)
        // Check for null/empty before delegation
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        // Pre-processing the argument before passing it to 'this'
        String processedName = "USER_" + name.toUpperCase();

        // 2. Call to 'this' (Delegation MUST occur once)
        // This delegation initializes ALL final fields (name and value).
        this(processedName, 100);

        // 3. Post-delegation instructions (allowed since Java 1.0)
        System.out.println("Flexible constructor body executed after delegation.");
    }

    public static void main(String[] args) {
        // Use the flexible constructor
        new FlexibleConstructorBodies("Alice");
    }
}
