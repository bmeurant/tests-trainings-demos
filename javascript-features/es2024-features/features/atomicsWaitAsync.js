// es2024-features/features/atomicsWaitAsync.js
const assert = require('assert');
const { Worker } = require('worker_threads');
const path = require('path');

async function runAtomicsWaitAsyncDemo() {
    console.log('--- ES2024: Atomics.waitAsync ---');

    // Create a SharedArrayBuffer
    // SharedArrayBuffer MUST be used for Atomics methods
    const sab = new SharedArrayBuffer(4); // 4 bytes for one Int32
    const sharedArray = new Int32Array(sab); // View on the SharedArrayBuffer

    const index = 0;
    const initialValue = 0;
    const newValue = 1;

    sharedArray[index] = initialValue;
    console.log(`\n[Main] Initial value at index ${index}: ${sharedArray[index]}`);

    // Create a new Worker and pass the SharedArrayBuffer
    const workerPath = path.join(__dirname, '../modules/waitAsyncWorker.mjs');
    const worker = new Worker(workerPath, {
        workerData: {
            sharedArray: sharedArray,
            index: index
        }
    });

    let workerFinished = false;

    // Listen for messages from the worker
    worker.on('message', (msg) => {
        if (msg.status === 'done') {
            console.log(`[Main] Worker reported done. Final value from worker: ${msg.value}`);
            workerFinished = true;
            // Clean up worker
            worker.terminate();
        }
    });

    // After a short delay, update the value and notify the worker
    await new Promise(resolve => setTimeout(resolve, 100)); // Give worker time to call waitAsync

    console.log(`[Main] Changing value at index ${index} to ${newValue} and notifying worker.`);
    sharedArray[index] = newValue; // Change the value
    const numWaiters = Atomics.notify(sharedArray, index, 1); // Notify one waiter
    console.log(`[Main] Notified ${numWaiters} worker(s).`);

    // Wait for the worker to finish
    await new Promise(resolve => {
        const checkInterval = setInterval(() => {
            if (workerFinished) {
                clearInterval(checkInterval);
                resolve();
            }
        }, 50);
    });

    console.log(`[Main] Final value at index ${index}: ${sharedArray[index]}`);
    assert.strictEqual(sharedArray[index], newValue, 'Assertion Failed: SharedArray value should be updated.');
    console.log('Assertion Passed: Atomics.waitAsync demonstration completed successfully.');

    console.log('\n--- All Atomics.waitAsync demonstrations complete. ---');
}

// Run the demo
runAtomicsWaitAsyncDemo().catch(error => {
    console.error('\n!!! ES2024 Atomics.waitAsync Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
});