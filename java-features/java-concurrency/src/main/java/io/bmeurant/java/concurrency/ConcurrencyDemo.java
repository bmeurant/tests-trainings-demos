package io.bmeurant.java.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.IO.*;

/**
 * Basic example illustrating concurrency using an ExecutorService.
 */
class ConcurrencyDemo {

    void main() {
        // 1. Define a pool of threads (a fixed-size thread pool)
        // This pool limits the number of simultaneously running OS threads.
        int numThreads = 3;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        println("Starting concurrent tasks...");

        // 2. Submit multiple tasks (Runnables) to the executor
        IntStream.range(0, 10).forEach(i -> executor.submit(new Task(i)));

        // 3. Shut down the executor and wait for all tasks to complete
        shutdownAndAwaitTermination(executor);

        println("All tasks completed.");
    }

    /**
     * A simple task implementing the Runnable interface.
     */
    class Task implements Runnable {
        private final int taskId;

        public Task(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public void run() {
            // The thread running this task is one from the pool.
            String threadName = Thread.currentThread().getName();
            println("Task " + taskId + " is running on thread: " + threadName);
            try {
                // Simulate work with a sleep
                TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 500));
            } catch (InterruptedException _) {
                Thread.currentThread().interrupt();
            }
            println("Task " + taskId + " completed on thread: " + threadName);
        }
    }

    /**
     * Utility method to properly shut down an ExecutorService.
     */
    private void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException _) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}
