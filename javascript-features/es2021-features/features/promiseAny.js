// es2021-features/features/promiseAny.js
const assert = require('assert');

// Helper function to create a promise that resolves or rejects after a delay
function createPromise(id, shouldSucceed, delayMs) {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            if (shouldSucceed) {
                console.log(`  Promise ${id}: Fulfilled with value 'Success ${id}'`);
                resolve(`Success ${id}`);
            } else {
                console.log(`  Promise ${id}: Rejected with error 'Error ${id}'`);
                reject(new Error(`Error ${id}`));
            }
        }, delayMs);
    });
}

async function runPromiseAnyDemo() {
    console.log('--- ES2021: Promise.any() ---');

    // --- 1. Scenario: First promise resolves ---
    console.log('\n--- Scenario 1: First promise resolves ---');
    const promises1 = [
        createPromise('A', false, 25), // Rejects after 25ms
        createPromise('B', true, 50),  // Resolves after 50ms (wins)
        createPromise('C', true, 150)  // Resolves after 150ms
    ];

    try {
        console.log('Waiting for Promise.any() (Scenario 1)...');
        const result1 = await Promise.any(promises1);
        console.log(`Result of Promise.any() (Scenario 1): ${result1}`);
        assert.strictEqual(result1, 'Success B', 'Assertion Failed: Promise.any should return value of first fulfilled promise.');
        console.log('Assertion Passed: Promise.any returned the value of the first fulfilled promise.');
    } catch (error) {
        console.error('!!! Scenario 1 unexpectedly failed:', error.message);
        assert.fail('Assertion Failed: Promise.any should not reject if any promise fulfills.');
    }

    // --- 2. Scenario: All promises reject (leads to AggregateError) ---
    console.log('\n--- Scenario 2: All promises reject (leads to AggregateError) ---');
    const promises2 = [
        createPromise('X', false, 100), // Rejects after 100ms
        createPromise('Y', false, 50),  // Rejects after 50ms
        createPromise('Z', false, 150)  // Rejects after 150ms
    ];

    try {
        console.log('Waiting for Promise.any() (Scenario 2)...');
        await Promise.any(promises2);
        console.log('This line should not be reached.');
        assert.fail('Assertion Failed: Promise.any should have rejected when all promises reject.');
    } catch (error) {
        console.log(`Caught expected error for Promise.any() (Scenario 2): ${error.name}`);
        assert.ok(error instanceof AggregateError, 'Assertion Failed: Error should be an AggregateError.');
        assert.strictEqual(error.errors.length, 3, 'Assertion Failed: AggregateError should contain 3 errors.');
        assert.ok(error.errors.some(e => e.message === 'Error X'), 'Assertion Failed: AggregateError missing Error X.');
        assert.ok(error.errors.some(e => e.message === 'Error Y'), 'Assertion Failed: AggregateError missing Error Y.');
        assert.ok(error.errors.some(e => e.message === 'Error Z'), 'Assertion Failed: AggregateError missing Error Z.');
        console.log('Assertion Passed: Promise.any correctly rejected with AggregateError when all promises rejected.');
    }

    // --- 3. Scenario: Empty iterable ---
    console.log('\n--- Scenario 3: Empty iterable ---');
    try {
        console.log('Waiting for Promise.any() (Scenario 3 - empty array)...');
        await Promise.any([]);
        console.log('This line should not be reached.');
        assert.fail('Assertion Failed: Promise.any should reject for an empty iterable.');
    } catch (error) {
        console.log(`Caught expected error for Promise.any() (Scenario 3): ${error.name}`);
        assert.ok(error instanceof AggregateError, 'Assertion Failed: Error should be an AggregateError for empty iterable.');
        assert.strictEqual(error.errors.length, 0, 'Assertion Failed: AggregateError should be empty for empty iterable.');
        console.log('Assertion Passed: Promise.any correctly rejected for an empty iterable.');
    }

    console.log('\n--- All Promise.any() demonstrations complete. ---');
}

// Call the async function
runPromiseAnyDemo().catch(error => {
    console.error('\n!!! ES2021 Promise.any() Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
});