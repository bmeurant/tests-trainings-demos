// es2024-features/modules/waitAsyncWorker.js
import { parentPort, workerData } from 'worker_threads';

// Get the shared buffer and index from workerData
const sharedArray = workerData.sharedArray;
const index = workerData.index;

console.log(`[Worker] Started. Value at index ${index}: ${sharedArray[index]}`);

// It returns an object with a 'value' property (e.g., "pending", "ok", "not-equal", "timed-out")
// If value is "pending", the *return value itself* of Atomics.waitAsync acts as a Promise that resolves later.
const result = Atomics.waitAsync(sharedArray, index, 0); // Omit timeout for now, defaults to Infinity

if (result.value === "pending") {
    console.log(`[Worker] Atomics.waitAsync is pending. Waiting for notification...`);
    result.then(outcome => {
        // This 'outcome' is the string result like "ok", "not-equal", "timed-out"
        console.log(`[Worker] Atomics.waitAsync resolved with outcome: "${outcome}". Final value: ${sharedArray[index]}`);
        parentPort.postMessage({ status: 'done', value: sharedArray[index] });
    });
} else {
    // This branch is rarely hit unless the initial value matches (sharedArray[index] === 0)
    // which means it didn't need to wait.
    console.log(`[Worker] Atomics.waitAsync returned synchronously with: "${result.value}". Final value: ${sharedArray[index]}`);
    parentPort.postMessage({ status: 'done', value: sharedArray[index] });
}