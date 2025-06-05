package io.bmeurant.java21.features;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class VirtualThreads {
    private static void performBlockingOperation(String threadType, int taskId) {
        try {
            // Simulate a blocking I/O operation like a network call or database query
            // This is where Virtual Threads excel, as they unmount when blocked.
            Thread.sleep(Duration.ofMillis(10));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Uncomment the line below to see each task completing, but it will be very verbose for many tasks.
        // System.out.println(threadType + " Task " + taskId + " completed by " + Thread.currentThread());
    }

    public static void main(String[] args) {
        final int NUMBER_OF_TASKS = 50_000; // Try increasing this significantly, e.g., 100_000 or 1,000_000

        System.out.println("Running " + NUMBER_OF_TASKS + " tasks, each simulating a 10ms blocking operation.");

        // --- OLD WAY: Using Fixed Thread Pool (Platform Threads) ---
        // Simulates traditional server applications with a limited pool of OS threads.
        // If NUMBER_OF_TASKS is much larger than the pool size, tasks will queue up,
        // leading to longer execution times or even resource exhaustion.
        System.out.println("\n+++ Using Platform Threads (Fixed Thread Pool of 50 threads) +++");
        long startTimePlatform = System.nanoTime();
        try (ExecutorService platformExecutor = Executors.newFixedThreadPool(50)) { // Limited pool size
            IntStream.range(0, NUMBER_OF_TASKS).forEach(i ->
                    platformExecutor.submit(() -> performBlockingOperation("Platform Thread", i))
            );
        } // executor.close() waits for all tasks to complete and shuts down the executor
        long endTimePlatform = System.nanoTime();
        long durationPlatformMs = TimeUnit.NANOSECONDS.toMillis(endTimePlatform - startTimePlatform);
        System.out.println("Platform Threads finished " + NUMBER_OF_TASKS + " tasks in " + durationPlatformMs + " ms.");
        System.out.println("Note: For high NUMBER_OF_TASKS, this can be significantly slower or lead to resource issues.");


        // --- NEW WAY: Using Virtual Threads (JEP 444) ---
        // Virtual threads are cheap and plentiful, allowing for millions of concurrent tasks
        // without exhausting OS resources. Each task gets its own virtual thread.
        System.out.println("\n+++ Using Virtual Threads (newVirtualThreadPerTaskExecutor) +++");
        long startTimeVirtual = System.nanoTime();
        try (ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, NUMBER_OF_TASKS).forEach(i ->
                    virtualExecutor.submit(() -> performBlockingOperation("Virtual Thread", i))
            );
        } // executor.close() waits for all tasks to complete and shuts down the executor
        long endTimeVirtual = System.nanoTime();
        long durationVirtualMs = TimeUnit.NANOSECONDS.toMillis(endTimeVirtual - startTimeVirtual);
        System.out.println("Virtual Threads finished " + NUMBER_OF_TASKS + " tasks in " + durationVirtualMs + " ms.");

        System.out.println("\n+++ Summary +++");
        System.out.println("Platform Threads (Fixed Pool) took: " + durationPlatformMs + " ms.");
        System.out.println("Virtual Threads (Per Task) took:    " + durationVirtualMs + " ms.");
        System.out.println("Virtual Threads are significantly faster for I/O bound tasks and scale much better.");
        System.out.println("Virtual Threads are now GA (Generally Available) in Java 21!");
    }
}
