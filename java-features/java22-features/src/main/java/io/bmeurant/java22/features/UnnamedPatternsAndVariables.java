package io.bmeurant.java22.features;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class UnnamedPatternsAndVariables {
    record Point(int x, int y) {
    }

    record Line(Point start, Point end) {
    }

    record Quad(Line top, Line bottom) {
    }

    public static void main(String[] args) {
        System.out.println("\n+++ Example 1: Unnamed variable in catch block +++");
        // OLD WAY: Declaring a variable even if not used
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("Old Way: Caught an ArithmeticException (variable 'e' is declared but not used).");
        }

        // NEW WAY (Java 22 GA): Using '_' to indicate an unused variable
        try {
            int result = 10 / 0;
        } catch (ArithmeticException _) { // 'e' is not needed, just the fact that it's an ArithmeticException
            System.out.println("New Way: Caught an ArithmeticException (details ignored with '_').");
        }

        System.out.println("\n+++ Example 2: Unnamed lambda parameters +++");

        // OLD WAY: Declaring parameters that are not used
        BiConsumer<String, Integer> biConsumerOldWay = (msg, count) -> {
            System.out.println("Old Way - Message: " + msg + " (parameter 'count' is unused)");
        };
        biConsumerOldWay.accept("Hello from old consumer", 100);

        // NEW WAY (Java 22 GA): Using '_' for unused lambda parameters
        BiConsumer<String, Integer> biConsumerNewWay = (msg, _) -> { // '_' indicates 'count' is intentionally ignored
            System.out.println("New Way - Message: " + msg + " (second parameter ignored with '_')");
        };
        biConsumerNewWay.accept("Hello from new consumer", 200);

        Consumer<String> simpleConsumer = _ -> System.out.println("New Way - Parameter ignored for this simple action.");
        simpleConsumer.accept("Some input");

        System.out.println("\n+++ Example 3: Unnamed patterns in switch expressions/statements +++");

        Object obj1 = new Point(5, 10);
        Object obj2 = new Line(new Point(0, 0), new Point(100, 100));
        Object obj3 = new Quad(new Line(new Point(0, 0), new Point(1, 1)), new Line(new Point(2, 2), new Point(3, 3)));
        Object obj4 = "Some String";

        // OLD WAY: If you only care about the type, you still name the pattern variable
        String oldWayDescription = "Unknown";
        if (obj1 instanceof Point p) { // 'p' is declared even if only the type matters
            oldWayDescription = "Old Way: It's a Point, but I don't care about its coordinates here.";
        }
        System.out.println(oldWayDescription);

        // NEW WAY (Java 22 GA): Using '_' for unnamed patterns
        String newWayDescription1 = switch (obj1) {
            case Point(_, _) -> "New Way: It's a Point (coordinates ignored)."; // Only type matters
            case Line(Point(_, _), Point(_, _)) ->
                    "New Way: It's a Line (all nested point coordinates ignored)."; // Nested unnamed
            case Quad(Line(_, _), Line(_, _)) ->
                    "New Way: It's a Quad (all nested line/point details ignored)."; // Deeper nested unnamed
            case String s -> "New Way: A String: " + s; // Regular named pattern
            case null -> "New Way: It's null!";
            default -> "New Way: Unknown object.";
        };
        System.out.println("Description for obj1: " + newWayDescription1);
        System.out.println("Description for obj2: " + (switch (obj2) {
            case Line(_, _) -> "New Way: It's a Line.";
            default -> "N/A";
        }));
        System.out.println("Description for obj3: " + (switch (obj3) {
            case Quad(_, _) -> "New Way: It's a Quad.";
            default -> "N/A";
        }));
    }
}
