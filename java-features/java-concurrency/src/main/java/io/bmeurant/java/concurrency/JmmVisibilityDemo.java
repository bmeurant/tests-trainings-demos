package io.bmeurant.java.concurrency;

import static java.lang.IO.*;

/**
 * Demonstrates a classic JMM visibility issue (Non-deterministic behavior).
 * The thread 'Reader' might cache the value of 'isReady' and never see the update 
 * made by the 'Writer' thread, leading to an infinite loop.
 */
class JmmVisibilityDemo {

    // IMPORTANT: Without 'volatile', the Reader thread is NOT guaranteed 
    // to see the new value of 'isReady'.
    private boolean isReady = false;
    private int number = 0;

    // A Writer thread that updates shared variables
    class Writer implements Runnable {
        @Override
        public void run() {
            println("Writer Thread: Setting shared variables...");

            // 1. Write the 'number'
            number = 42;

            // 2. Write the flag 'isReady' (Should happen logically AFTER the number write)
            isReady = true;

            // The writes might not be immediately flushed to main memory, 
            // or the compiler/CPU might reorder these two writes.
            println("Writer Thread: Variables set. Waiting...");
        }
    }

    // A Reader thread that waits for the flag
    class Reader implements Runnable {
        @Override
        public void run() {
            println("Reader Thread: Waiting for isReady...");

            // Loop until the Reader sees the change
            while (!isReady) {
                // Yielding to hint the scheduler to give time to other threads, 
                // but this does NOT fix the JMM visibility issue.
                Thread.yield();
            }

            // If the Reader sees isReady=true, it *should* also see number=42.
            // But without JMM guarantees, there might be:
            // 1. Stale read: isReady might be stuck at false (infinite loop).
            // 2. Reordering issue: Reader sees isReady=true, but reads number=0.
            println("Reader Thread: isReady is true. The number is: " + number);
        }
    }

    void main() throws InterruptedException {
        // Start the Reader first (to wait for the flag)
        Thread readerThread = new Thread(new Reader(), "Reader");
        readerThread.start();

        // Give the Reader a small head start
        Thread.sleep(100);

        // Start the Writer
        Thread writerThread = new Thread(new Writer(), "Writer");
        writerThread.start();

        readerThread.join(); // Wait for the reader to finish (if it ever does)
        println("Main: Program finished.");
    }
}
