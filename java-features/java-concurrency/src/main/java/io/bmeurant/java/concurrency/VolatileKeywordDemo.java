package io.bmeurant.java.concurrency;

import static java.lang.IO.*;

/**
 * Corrected example using the 'volatile' keyword to fix the JMM visibility issue.
 * Volatile guarantees that changes to 'isReady' are immediately visible 
 * across all threads and ensures proper ordering (Happens-Before).
 */
class VolatileKeywordDemo {

    // Fix: Adding 'volatile' ensures that all threads read and write this variable 
    // directly from main memory and establishes a Happens-Before relationship.
    private volatile boolean isReady = false;
    private int number = 0;

    // A Writer thread that updates shared variables
    class Writer implements Runnable {
        @Override
        public void run() {
            println("Writer Thread: Setting shared variables...");

            // 1. Write the 'number'
            // This write happens-before the write to the volatile field.
            number = 42;

            // 2. Write the volatile flag 'isReady'
            isReady = true;

            // The volatile write flushes all previous memory operations (including number = 42)
            // to main memory.
            println("Writer Thread: Variables set. isReady is now true.");
        }
    }

    // A Reader thread that waits for the flag
    class Reader implements Runnable {
        @Override
        public void run() {
            println("Reader Thread: Waiting for isReady...");

            // Loop until the Reader sees the change
            while (!isReady) {
                Thread.yield();
            }

            // The volatile read of 'isReady' forces the thread to refresh its local cache 
            // from main memory, making the change to 'number' also visible.
            println("Reader Thread: isReady is true. The number is: " + number);
        }
    }

    void main() throws InterruptedException {
        // Start the Reader first
        Thread readerThread = new Thread(new Reader(), "Reader");
        readerThread.start();

        // Give the Reader a small head start
        Thread.sleep(100);

        // Start the Writer
        Thread writerThread = new Thread(new Writer(), "Writer");
        writerThread.start();

        // Wait for both threads to finish
        writerThread.join();
        readerThread.join();
        println("Main: Program finished reliably.");
    }
}
