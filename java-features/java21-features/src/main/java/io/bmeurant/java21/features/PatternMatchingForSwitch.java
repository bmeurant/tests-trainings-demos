package io.bmeurant.java21.features;

public class PatternMatchingForSwitch {

    // Define a sealed interface and records for demonstration
    sealed interface Shape permits Circle, Rectangle {
    }

    record Circle(double radius) implements Shape {
    }

    record Rectangle(double width, double height) implements Shape {
    }

    record Triangle(double side1, double side2, double side3) {
    } // Not sealed, just a regular record

    public static String describeObjectOldWay(Object obj) {
        System.out.println("+++ OLD WAY (pre-Java 21: if-else if with instanceof and explicit casts) +++");
        if (obj == null) {
            return "It's null!";
        } else if (obj instanceof Integer) {
            Integer i = (Integer) obj; // Explicit cast
            return "An Integer: " + i;
        } else if (obj instanceof String) {
            String s = (String) obj; // Explicit cast
            if (s.length() > 5) { // Additional condition
                return "Long String: " + s;
            } else {
                return "Short String: " + s;
            }
        } else if (obj instanceof Circle) {
            Circle c = (Circle) obj; // Explicit cast
            return "A Circle with radius: " + c.radius();
        } else if (obj instanceof Rectangle) {
            Rectangle r = (Rectangle) obj; // Explicit cast
            return "A Rectangle with dimensions: " + r.width() + "x" + r.height();
        } else {
            return "Unknown object type: " + obj.getClass().getName();
        }
    }

    public static String describeObjectNewWay(Object obj) {
        System.out.println("+++ NEW WAY (Java 21: Pattern Matching for Switch) +++");
        // Pattern Matching for Switch is now GA in Java 21! No --enable-preview needed.
        return switch (obj) {
            case Integer i when i > 0 -> "Positive Integer: " + i; // Guarded pattern with 'when'
            case Integer i -> "Non-positive Integer: " + i;
            case String s when s.length() > 5 -> "Long String: " + s; // Another guarded pattern with 'when'
            case String s -> "Short String: " + s;
            case Circle c -> "A Circle with radius: " + c.radius(); // Direct pattern variable 'c'
            case Rectangle r ->
                    "A Rectangle with dimensions: " + r.width() + "x" + r.height(); // Direct pattern variable 'r'
            case null -> "It's null!"; // Explicit null case
            default -> "Unknown object type: " + obj.getClass().getName();
        };
    }

    public static void main(String[] args) {
        Object[] testObjects = {
                10,
                -5,
                "hello world",
                "hi",
                new Circle(7.5),
                new Rectangle(12.0, 8.0),
                null
        };

        for (Object obj : testObjects) {
            System.out.println("\nProcessing object: " + (obj == null ? "null" : obj.getClass().getSimpleName()));
            System.out.println(describeObjectOldWay(obj));
            System.out.println(describeObjectNewWay(obj));
            System.out.println("----------------------------------------");
        }

        System.out.println("\nPattern Matching for Switch (Java 21) offers much cleaner, safer, and more concise code!");
    }
}
