package io.bmeurant.java25.features;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScopedValues {
    // Final ScopedValue declaration
    private static final ScopedValue<String> REQUEST_ID = ScopedValue.newInstance();

    // Logger method
    private static void logMessage(String message) {
        if (REQUEST_ID.isBound()) {
            System.out.println("[" + REQUEST_ID.get() + "] " + message);
        } else {
            System.out.println("[NO_ID] " + message);
        }
    }

    public static void main(String[] args) {
        logMessage("Starting application.");

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> ScopedValue.where(REQUEST_ID, "TXN-9001").run(() -> {
                logMessage("Processing request.");
                logMessage("Thread: " + Thread.currentThread());
            }));

            executor.submit(() -> ScopedValue.where(REQUEST_ID, "TXN-9002").run(() -> {
                logMessage("Processing request.");
                logMessage("Thread: " + Thread.currentThread());
            }));

            // Optional delay to ensure output appears before main exits
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        logMessage("Application ended.");
    }
}
