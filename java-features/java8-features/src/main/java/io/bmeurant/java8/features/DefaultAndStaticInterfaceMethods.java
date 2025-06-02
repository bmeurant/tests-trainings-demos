package io.bmeurant.java8.features;

public class DefaultAndStaticInterfaceMethods {

    // Define an interface with abstract, default, and static methods
    interface Vehicle {
        // Abstract method (pre-Java 8 style) - must be implemented by concrete classes
        void start();

        // Default method (Java 8+) - provides a default implementation
        default void stop() {
            System.out.println("Vehicle stopped (default behavior).");
        }

        // Another default method
        default void honk() {
            System.out.println("Beep! Beep! (Default honk).");
        }

        // Static method (Java 8+) - belongs to the interface itself, not instances
        static String getVehicleType() {
            return "Generic Land Vehicle";
        }

        // Another static method for utility
        static int calculateFuelEfficiency(int distance, int fuelConsumed) {
            if (fuelConsumed <= 0) {
                return 0; // Avoid division by zero
            }
            return distance / fuelConsumed;
        }
    }

    // Implement the Vehicle interface in a Car class
    static class Car implements Vehicle {
        @Override
        public void start() {
            System.out.println("Car engine started.");
        }

        // Optional: Override the default stop method
        @Override
        public void stop() {
            System.out.println("Car brakes engaged, engine shut down.");
        }

        // We choose not to override honk(), the default implementation will be used.
    }

    // Implement the Vehicle interface in a Motorcycle class, without overriding default methods
    static class Motorcycle implements Vehicle {
        @Override
        public void start() {
            System.out.println("Motorcycle ignition engaged.");
        }
        // stop() and honk() will use the default implementations from the Vehicle interface.
    }

    public static void main(String[] args) {
        System.out.println("\n--- Using Car class (overrides default stop) ---\n");
        Car myCar = new Car();
        myCar.start();  // Calls implemented method
        myCar.stop();   // Calls overridden default method
        myCar.honk();   // Calls default method from interface
        System.out.println("Car type: " + Vehicle.getVehicleType()); // Calls static method
        System.out.println("Car fuel efficiency (100km, 10L): " + Vehicle.calculateFuelEfficiency(100, 10) + " km/L");

        System.out.println("\n--- Using Motorcycle class (uses default implementations) ---\n");
        Motorcycle myMotorcycle = new Motorcycle();
        myMotorcycle.start(); // Calls implemented method
        myMotorcycle.stop();  // Calls default stop method from interface
        myMotorcycle.honk();  // Calls default honk method from interface
        System.out.println("Motorcycle type: " + Vehicle.getVehicleType()); // Calls static method
    }
}
