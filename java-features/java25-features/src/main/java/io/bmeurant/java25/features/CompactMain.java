// Note: No explicit class declaration needed for the entry point.

// NEW: Instance Main Method
// The runtime will create an implicit class instance and invoke this method.
void main(String... args) {
    System.out.println("--- Compact Source File & Instance Main Demo (JEP 512) ---");

    // Calling an instance method (which is now possible and implicit)
    String message = generateMessage(args.length);
    System.out.println(message);

    // The main method itself is an instance method here.
}

String generateMessage(int argCount) {
    if (argCount > 0) {
        return "Received " + argCount + " arguments.";
    } else {
        return "No arguments received.";
    }
}
// The compiler infers a class for this source file.
