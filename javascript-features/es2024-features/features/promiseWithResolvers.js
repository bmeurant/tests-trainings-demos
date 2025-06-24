// es2024-features/features/promiseWithResolvers.js
const assert = require('assert');

async function runPromiseWithResolversDemo() { // Added async to use await for cleaner demo flow
    console.log('--- ES2024: Promise.withResolvers() ---');

    // --- 1. Basic usage: Resolving a promise externally ---
    console.log('\n--- 1. Basic usage: Resolving a promise externally ---');
    // We destructure directly the promise and the resolve/reject functions
    const { promise: resolvedPromise, resolve: doResolve } = Promise.withResolvers();

    console.log('  Resolved Promise created via Promise.withResolvers().');

    // Simulate an async operation that resolves the promise later
    setTimeout(() => {
        doResolve('Data fetched successfully!');
    }, 100);

    try {
        const value = await resolvedPromise; // Await the promise to get its value
        console.log(`  Resolved Promise settled with: "${value}"`);
        assert.strictEqual(value, 'Data fetched successfully!', 'Assertion Failed: Promise resolve value incorrect.');
        console.log('Assertion Passed: Promise resolved externally.');
    } catch (error) {
        assert.fail(`Assertion Failed: Resolved promise rejected unexpectedly: ${error.message}`);
    }

    // --- 2. Basic usage: Rejecting a promise externally ---
    console.log('\n--- 2. Basic usage: Rejecting a promise externally ---');
    const { promise: rejectedPromise, reject: doReject } = Promise.withResolvers();

    console.log('  Rejected Promise created via Promise.withResolvers().');

    // Simulate an async operation that rejects the promise later
    setTimeout(() => {
        doReject(new Error('Operation failed: Network error!'));
    }, 100);

    try {
        await rejectedPromise; // Await the promise, expecting it to throw on rejection
        assert.fail('Assertion Failed: Rejected promise resolved unexpectedly.');
    } catch (error) {
        console.log(`  Rejected Promise settled with error: "${error.message}"`);
        assert.ok(error instanceof Error, 'Assertion Failed: Rejected value is not an Error instance.');
        assert.strictEqual(error.message, 'Operation failed: Network error!', 'Assertion Failed: Promise reject error message incorrect.');
        console.log('Assertion Passed: Promise rejected externally as expected.');
    }

    // --- 3. Combined example: Simulating a request with success or failure ---
    console.log('\n--- 3. Combined example: Simulating a request ---');

    function fetchData(shouldSucceed) {
        const { promise, resolve, reject } = Promise.withResolvers();
        setTimeout(() => {
            if (shouldSucceed) {
                resolve(`Data for "${shouldSucceed}" loaded!`);
            } else {
                reject(new Error(`Failed to load data for "${shouldSucceed}"`));
            }
        }, 50);
        return promise;
    }

    console.log('  Testing successful data fetch...');
    try {
        const successData = await fetchData(true);
        console.log(`    Success: ${successData}`);
        assert.strictEqual(successData, 'Data for "true" loaded!', 'Assertion Failed: Combined success case incorrect.');
    } catch (e) {
        assert.fail(`    Failed unexpectedly: ${e.message}`);
    }

    console.log('  Testing failed data fetch...');
    try {
        await fetchData(false);
        assert.fail('    Expected failure, but succeeded!');
    } catch (error) {
        console.log(`    Failure: ${error.message}`);
        assert.strictEqual(error.message, 'Failed to load data for "false"', 'Assertion Failed: Combined failure case incorrect.');
    }


    console.log('\n--- All Promise.withResolvers() demonstrations complete. ---');
}

// To run the async demo function, we call it and handle potential errors at the top level
runPromiseWithResolversDemo().catch(topLevelError => {
    console.error('\n!!! ES2024 Promise.withResolvers() Demo Failed !!!');
    console.error('Error details:', topLevelError.message);
    process.exit(1);
});