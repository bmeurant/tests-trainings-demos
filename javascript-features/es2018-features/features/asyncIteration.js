// es2018/features/asyncIteration.js
const assert = require('assert');

/**
 * A simple async iterable class that yields numbers after a delay.
 * Simulates fetching data chunk by chunk.
 */
class AsyncNumberGenerator {
    constructor(limit) {
        this.limit = limit;
        this.counter = 0; // Use a separate counter for the instance, not shared across iterators
        console.log(`  AsyncNumberGenerator created with limit: ${limit}`);
    }

    // This method makes the class an async iterable
    [Symbol.asyncIterator]() {
        // Each call to [Symbol.asyncIterator] should return a fresh iterator
        // This makes sure multiple `for await...of` loops on the same instance
        // restart from the beginning if that's the desired behavior.
        // For this demo, we want to start from scratch each time.
        let current = 0; // Iterator-specific counter
        const limit = this.limit; // Capture instance limit

        return {
            async next() {
                if (current < limit) {
                    current++;
                    const value = current;
                    console.log(`    Generating value: ${value} (simulating delay...)`);
                    return new Promise(resolve => {
                        setTimeout(() => {
                            resolve({ value: value, done: false });
                        }, 50); // Simulate an async operation delay
                    });
                } else {
                    console.log('    Generator finished.');
                    return Promise.resolve({ value: undefined, done: true });
                }
            }
        };
    }
}

async function runAsyncIterationDemo() {
    console.log('--- ES2018: Asynchronous Iteration (for-await-of) ---');

    // --- Case 1: Iterate over a small number of items ---
    console.log('\n--- Iterating 3 numbers from AsyncNumberGenerator ---');
    const generator1 = new AsyncNumberGenerator(3);
    const results1 = [];
    for await (const num of generator1) {
        results1.push(num);
        console.log(`  Received number: ${num}`);
    }
    console.log('Collected results:', results1);
    assert.deepStrictEqual(results1, [1, 2, 3], 'Assertion Failed: Results for 3 numbers incorrect');
    console.log('Assertion Passed: Successfully iterated 3 numbers.');

    // --- Case 2: Iterate over zero items (empty generator) ---
    console.log('\n--- Iterating 0 numbers (empty generator) ---');
    const generator2 = new AsyncNumberGenerator(0);
    const results2 = [];
    for await (const num of generator2) {
        results2.push(num); // This block should not execute
    }
    console.log('Collected results (empty):', results2);
    assert.deepStrictEqual(results2, [], 'Assertion Failed: Results for 0 numbers incorrect');
    console.log('Assertion Passed: Handled empty generator correctly.');

    // --- Case 3: Demonstrate processing order ---
    console.log('\n--- Demonstrating sequential processing with delays ---');
    const generator3 = new AsyncNumberGenerator(2);
    const processingOrder = [];
    for await (const num of generator3) {
        console.log(`  Processing number ${num} immediately after reception.`);
        processingOrder.push(num);
        // Simulate some synchronous work after receiving each item
        await new Promise(r => setTimeout(r, 20)); // Small delay for visual effect
    }
    console.log('Processing order:', processingOrder);
    assert.deepStrictEqual(processingOrder, [1, 2], 'Assertion Failed: Processing order incorrect');
    console.log('Assertion Passed: Items processed sequentially.');

    console.log('\n--- All Asynchronous Iteration demonstrations complete. ---');
}

// Call the async function
runAsyncIterationDemo().catch(error => {
    console.error('\n!!! ES2018 Asynchronous Iteration Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1); // Exit with a non-zero code to indicate failure
});