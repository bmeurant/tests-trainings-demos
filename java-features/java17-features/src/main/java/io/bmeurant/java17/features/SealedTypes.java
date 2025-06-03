package io.bmeurant.java17.features;

// --- Example 1: Sealed Class Hierarchy ---

// Declare a sealed abstract class Shape, permitting only a specific set of direct subclasses
sealed abstract class Shape permits Circle, Square, Triangle {
    public abstract double area();

    public abstract String description();
}

// Circle is a final class, meaning it cannot be extended further
final class Circle extends Shape {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public String description() {
        return "This is a Circle with radius " + radius;
    }
}

// Square is a non-sealed class, meaning any other class can extend it
non-sealed class Square extends Shape {
    private final double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    public double area() {
        return side * side;
    }

    @Override
    public String description() {
        return "This is a Square with side " + side;
    }
}

// Triangle is also a sealed class, demonstrating nested sealed hierarchies
sealed class Triangle extends Shape permits EquilateralTriangle, RightTriangle {
    private final double base;
    private final double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public double area() {
        return 0.5 * base * height;
    }

    @Override
    public String description() {
        return "This is a generic Triangle (base=" + base + ", height=" + height + ")";
    }
}

final class EquilateralTriangle extends Triangle {
    private final double side;

    public EquilateralTriangle(double side) {
        // A simple way to approximate base and height for an equilateral triangle
        super(side, side * Math.sqrt(3) / 2);
        this.side = side;
    }

    @Override
    public String description() {
        return "This is an Equilateral Triangle with side " + side;
    }
}

final class RightTriangle extends Triangle {
    private final double baseSide;
    private final double heightSide;

    public RightTriangle(double baseSide, double heightSide) {
        super(baseSide, heightSide);
        this.baseSide = baseSide;
        this.heightSide = heightSide;
    }

    @Override
    public String description() {
        return "This is a Right Triangle with base " + baseSide + " and height " + heightSide;
    }
}


// --- Example 2: Sealed Interface Hierarchy ---

// A sealed interface representing a user action
sealed interface UserAction permits Login, Logout, Purchase {
    String getDescription();
}

// Final record implementing UserAction (Records are implicitly final)
record Login(String username) implements UserAction {
    @Override
    public String getDescription() {
        return "User '" + username + "' logged in.";
    }
}

// Non-sealed class implementing UserAction, allowing further extension
non-sealed class Logout implements UserAction {
    private final String username;

    public Logout(String username) {
        this.username = username;
    }

    @Override
    public String getDescription() {
        return "User '" + username + "' logged out.";
    }
}

// Sealed interface extending UserAction, demonstrating nested sealed interfaces
sealed interface Purchase extends UserAction permits OnlinePurchase, StorePurchase {
    double getAmount(); // Abstract method, must be implemented by permitted types
}

// Corrected OnlinePurchase record: implements getAmount()
record OnlinePurchase(String username, double amount, String item) implements Purchase {
    @Override
    public String getDescription() {
        return "User '" + username + "' purchased '" + item + "' online for " + String.format("%.2f", amount);
    }

    @Override
    public double getAmount() {
        return this.amount; // Access the record component directly
    }
}

// Corrected StorePurchase record: implements getAmount()
record StorePurchase(String username, double amount, String storeLocation) implements Purchase {
    @Override
    public String getDescription() {
        return "User '" + username + "' purchased from store at '" + storeLocation + "' for " + String.format("%.2f", amount);
    }

    @Override
    public double getAmount() {
        return this.amount; // Access the record component directly
    }
}


public class SealedTypes {

    public static void main(String[] args) {

        // --- Demo of Sealed Class Hierarchy ---
        System.out.println("\n--- Demoing Sealed Class Hierarchy (Shape) ---");
        Shape circle = new Circle(5.0);
        Shape square = new Square(4.0);
        Shape eqTriangle = new EquilateralTriangle(6.0);
        Shape rightTriangle = new RightTriangle(3.0, 4.0);

        System.out.println("\nProcessing Shapes:");
        processShape(circle);
        processShape(square);
        processShape(eqTriangle);
        processShape(rightTriangle);

        // You cannot extend Shape with an unauthorized class:
        // class UnauthorizedShape extends Shape {} // This would cause a compile-time error!

        // --- Demo of Sealed Interface Hierarchy ---
        System.out.println("\n--- Demoing Sealed Interface Hierarchy (UserAction) ---");
        UserAction login = new Login("bmeurant");
        UserAction logout = new Logout("bmeurant");
        UserAction onlinePurchase = new OnlinePurchase("bmeurant", 99.99, "Java Book");
        UserAction storePurchase = new StorePurchase("bmeurant", 15.50, "Downtown");

        System.out.println(login.getDescription());
        System.out.println(logout.getDescription());

        // --- FIX: Use Pattern Matching for instanceof to safely access getAmount() ---
        // onlinePurchase is declared as UserAction, which does not have getAmount()
        // We need to check if it's a Purchase first.
        if (onlinePurchase instanceof Purchase purchaseAction) {
            System.out.println(purchaseAction.getDescription() + " (Amount: " + purchaseAction.getAmount() + ")");
        } else {
            // Fallback for other UserAction types that are not Purchases
            System.out.println(onlinePurchase.getDescription() + " (Not a purchase, no amount available)");
        }

        if (storePurchase instanceof Purchase purchaseAction) {
            System.out.println(purchaseAction.getDescription() + " (Amount: " + purchaseAction.getAmount() + ")");
        } else {
            System.out.println(storePurchase.getDescription() + " (Not a purchase, no amount available)");
        }

        // You cannot implement UserAction with an unauthorized class:
        // class UnauthorizedAction implements UserAction { public String getDescription() { return ""; } } // Compile-time error!

        // You cannot extend Purchase with an unauthorized interface:
        // interface UnauthorizedPurchase extends Purchase {} // Compile-time error!
    }

    // Helper method for Shape demo
    public static void processShape(Shape shape) {
        System.out.println(shape.description() + ". Area: " + String.format("%.2f", shape.area()));
        // The compiler knows the full set of permitted subclasses, which helps in future enhancements (e.g., Pattern Matching for Switch)
    }
}
