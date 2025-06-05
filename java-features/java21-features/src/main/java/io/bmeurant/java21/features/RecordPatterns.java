package io.bmeurant.java21.features;

public class RecordPatterns {
    // Define some simple records
    record Point(int x, int y) {
    }

    record Box(Point topLeft, Point bottomRight) {
    }

    record ColoredPoint(Point p, String color) {
    }

    public static void printCoordinatesOldWay(Object obj) {
        System.out.println("\n+++ Old way (before Record Patterns) for: " + obj + " +++");

        if (obj instanceof Point) {
            Point p = (Point) obj;
            System.out.println("Extracted Point: (" + p.x() + ", " + p.y() + ")");
        } else if (obj instanceof Box) {
            Box b = (Box) obj;
            Point tl = b.topLeft();
            Point br = b.bottomRight();
            System.out.println("Extracted Box: TopLeft (" + tl.x() + ", " + tl.y() + "), BottomRight (" + br.x() + ", " + br.y() + ")");
        } else if (obj instanceof ColoredPoint) {
            ColoredPoint cp = (ColoredPoint) obj;
            Point p = cp.p();
            System.out.println("Extracted ColoredPoint: (" + p.x() + ", " + p.y() + ") with color " + cp.color());
        } else {
            System.out.println("Not a recognized object.");
        }
    }

    public static void printCoordinatesNewWay(Object obj) {
        System.out.println("\n+++ New way (with Record Patterns) for: " + obj + " +++");

        if (obj instanceof Point(int x, int y)) {
            System.out.println("Extracted Point: (" + x + ", " + y + ")");
        } else if (obj instanceof Box(Point tl, Point br)) {
            // Nested record patterns: extract top-left and bottom-right points
            System.out.println("Extracted Box: TopLeft (" + tl.x() + ", " + tl.y() + "), BottomRight (" + br.x() + ", " + br.y() + ")");
        } else if (obj instanceof ColoredPoint(Point(int x, int y), String color)) {
            // Nested record pattern: extracting x, y from the Point inside ColoredPoint
            System.out.println("Extracted ColoredPoint: (" + x + ", " + y + ") with color " + color);
        } else {
            System.out.println("Not a recognized record pattern.");
        }
    }

    public static String describeShapeOldWay(Object obj) {
        System.out.println("\n+++ Old way (switch) for: " + obj + " ---");
        if (obj == null) {
            return "It's null!";
        } else if (obj instanceof Point) {
            Point p = (Point) obj;
            return "It's a Point at (" + p.x() + ", " + p.y() + ")";
        } else if (obj instanceof Box) {
            Box b = (Box) obj;
            Point tl = b.topLeft();
            Point br = b.bottomRight();
            return "It's a Box from (" + tl.x() + ", " + tl.y() + ") to (" + br.x() + ", " + br.y() + ")";
        } else if (obj instanceof ColoredPoint) {
            ColoredPoint cp = (ColoredPoint) obj;
            Point p = cp.p();
            return "It's a " + cp.color() + " colored point at (" + p.x() + ", " + p.y() + ")";
        } else {
            return "Unknown object for shape description.";
        }
    }

    public static String describeShapeNewWay(Object obj) {
        System.out.println("\n+++ New way (switch with Record Patterns) for: " + obj + " +++");
        return switch (obj) {
            case Point(int x, int y) -> "It's a Point at (" + x + ", " + y + ")";
            // Nested record patterns directly in switch
            case Box(Point(int tlX, int tlY), Point(int brX, int brY)) ->
                    "It's a Box from (" + tlX + ", " + tlY + ") to (" + brX + ", " + brY + ")";
            case ColoredPoint(Point p, String c) -> "It's a " + c + " colored point at (" + p.x() + ", " + p.y() + ")";
            case null -> "It's null!";
            default -> "Unknown object for shape description.";
        };
    }

    public static void main(String[] args) {
        Point p1 = new Point(10, 20);
        Point p2 = new Point(5, 15); // Unused, but kept for context
        Box b1 = new Box(p1, new Point(30, 40));
        ColoredPoint cp1 = new ColoredPoint(new Point(1, 1), "Red");

        System.out.println("\n===== Comparaison with instanceof =====");
        printCoordinatesOldWay(p1);
        printCoordinatesNewWay(p1);

        printCoordinatesOldWay(b1);
        printCoordinatesNewWay(b1);

        printCoordinatesOldWay(cp1);
        printCoordinatesNewWay(cp1);

        printCoordinatesOldWay("Hello");
        printCoordinatesNewWay("Hello");

        System.out.println("\n===== Comparaison with switch expression =====");
        System.out.println(describeShapeOldWay(p1));
        System.out.println(describeShapeNewWay(p1));

        System.out.println(describeShapeOldWay(b1));
        System.out.println(describeShapeNewWay(b1));

        System.out.println(describeShapeOldWay(cp1));
        System.out.println(describeShapeNewWay(cp1));

        System.out.println(describeShapeOldWay(null));
        System.out.println(describeShapeNewWay(null));

        System.out.println(describeShapeOldWay(42));
        System.out.println(describeShapeNewWay(42));
    }
}
