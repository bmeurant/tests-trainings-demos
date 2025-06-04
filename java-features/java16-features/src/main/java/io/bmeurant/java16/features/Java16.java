package io.bmeurant.java16.features;

public class Java16 {
    public static void main(String[] args) {
        System.out.println("\n--- Java 16: Records (JEP 395) ---");
        Records.main(args);

        System.out.println("\n--- Java 16: Pattern Matching for instanceof (JEP 394) ---");
        PatternMatchingInstanceof.main(args);

        System.out.println("\n--- Java 16: Static Members in Local Classes (JEP 395) ---");
        LocalClassStatics.main(args);

        System.out.println("\n--- Java 16: Strong Encapsulation (JEP 396) ---");
        StrongEncapsulation.main(args);
    }
}
