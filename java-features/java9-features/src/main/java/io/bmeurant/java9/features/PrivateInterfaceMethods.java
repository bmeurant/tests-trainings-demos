package io.bmeurant.java9.features;

public class PrivateInterfaceMethods {

    // Define an interface with default and private methods
    interface Logger {
        // Default method that can be implemented by classes
        default void logInfo(String message) {
            log("INFO", message);
        }

        // Another default method
        default void logError(String message) {
            log("ERROR", message);
        }

        // Static method that can be called directly on the interface
        static void logDebug(String message) {
            // Static methods can call private static methods
            logInternal("DEBUG", message);
        }

        // Private method for common logging logic.
        // It can be called by default methods within this interface.
        private void log(String level, String message) {
            System.out.println("[" + level + "] " + message);
        }

        // Private static method for common static logging logic.
        // It can be called by static methods within this interface.
        private static void logInternal(String level, String message) {
            System.out.println("[STATIC " + level + "] " + message);
        }
    }

    // Implement the interface in a class
    static class MyLogger implements Logger {
        // No need to implement default methods, but can override them if needed.
        // We can directly use logInfo and logError.
    }

    public static void main(String[] args) {
        MyLogger myLogger = new MyLogger();
        myLogger.logInfo("This is an informational message.");
        myLogger.logError("An error occurred!");

        // Call the static method directly on the interface
        Logger.logDebug("This is a debug message from a static method.");

        // The following lines would cause compile-time errors:
        // myLogger.log("WARNING", "Private method cannot be accessed directly.");
        // Logger.log("WARNING", "Private method cannot be accessed directly.");
    }
}
