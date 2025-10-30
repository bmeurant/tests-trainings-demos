package io.bmeurant.java.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static java.lang.IO.*;

/**
 * Example demonstrating the use and benefits of Virtual Threads (Java 21+).
 */
class VirtualThreadDemo {

    private final int NUM_VIRTUAL_THREADS = 1_000_000; // Create 1 million "threads" easily

    void main() {
        println("--- Starting Virtual Threads Demo ---");

        long startTime = System.currentTimeMillis();

        // 1. The preferred way: Executors.newVirtualThreadPerTaskExecutor()
        // This creates a new Virtual Thread for *every* submitted task.
        // It's AutoCloseable, so we use a try-with-resources block to ensure it's shut down.
        try (ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, NUM_VIRTUAL_THREADS).forEach(i -> {
                virtualExecutor.submit(() -> {
                    String threadName = Thread.currentThread().getName();
                    // Check if the current thread is a virtual thread
                    boolean isVirtual = Thread.currentThread().isVirtual();

                    // We won't print for all 1M tasks, just a few for demonstration
                    if (i < 5) {
                        println("Virtual Task " + i +
                                " running on thread: " + threadName +
                                " (Is Virtual: " + isVirtual + ")");
                    }

                    // Simulate a blocking I/O operation (e.g., waiting for a database query)
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException _) {
                        Thread.currentThread().interrupt();
                    }
                });
            });
        } // The executor is automatically shut down and awaited here.

        long endTime = System.currentTimeMillis();
        println(String.format("Finished %d Virtual Threads in %d ms.",
                NUM_VIRTUAL_THREADS, (endTime - startTime)));

        println("--- Virtual Threads Demo Finished ---");
    }
}
