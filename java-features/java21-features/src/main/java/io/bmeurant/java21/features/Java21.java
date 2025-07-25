package io.bmeurant.java21.features;

public class Java21 {
    public static void main(String[] args) {
        System.out.println("\n--- Java 21: Virtual Threads (JEP 444) ---");
        VirtualThreads.main(args);

        System.out.println("\n--- Java 21: Virtual Threads (JEP 411) ---");
        PatternMatchingForSwitch.main(args);

        System.out.println("\n--- Java 21: Record Patterns Comparison (JEP 440) ---");
        RecordPatterns.main(args);

        System.out.println("\n--- Java 21: Sequenced Collections Comparison (JEP 431) ---");
        SequencedCollections.main(args);

        System.out.println("\n--- Java 21: Key Encapsulation Mechanism API (JEP 435) - GA ---");
        KeyEncapsulationMechanismAPI.main(args);
    }
}
