package io.bmeurant.java.concurrency;

import static java.lang.IO.*;

/**
 * Demonstrates the creation and naming of traditional Platform Threads.
 * Note: Creating many threads like this can lead to resource exhaustion.
 */
class PlatformThreadDemo {

    private final int NUM_TASKS = 5;

    void main() throws InterruptedException {
        println("--- Starting Platform Threads Demo ---");

        for (int i = 0; i < NUM_TASKS; i++) {
            final int taskId = i;
            // Creation of a traditional OS-backed Thread
            Thread platformThread = new Thread(() -> {
                String threadName = Thread.currentThread().getName();
                println("Platform Task " + taskId + " running on thread: " + threadName);
            }, "my-platform-thread-" + taskId); // Naming the thread

            platformThread.start();
        }

        // Wait a short time for all threads to execute and finish their minimal work
        Thread.sleep(100);
        println("--- Platform Threads Demo Finished ---");
    }
}
