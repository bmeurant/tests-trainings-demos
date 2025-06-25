// es2025-features/features/promiseTry.js
const assert = require('assert');

function runPromiseTryDemo() {
    console.log('\n--- ES2025: Promise.try() ---');

    // Helper function to simulate an asynchronous operation
    const asyncOperation = (shouldSucceed) => {
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                if (shouldSucceed) {
                    resolve('Async operation successful!');
                } else {
                    reject(new Error('Async operation failed!'));
                }
            }, 50); // Simulate some delay
        });
    };

    // Scenario 1: Synchronous function that returns a value
    console.log('\n--- Scenario 1: Synchronous function returning a value ---');
    Promise.try(() => {
        console.log('  Executing synchronous function...');
        return 'Synchronous value';
    })
        .then(result => {
            console.log(`  Promise.try resolved with: "${result}"`);
            assert.strictEqual(result, 'Synchronous value', 'Assertion failed: Should resolve with synchronous value.');
        })
        .catch(error => {
            console.error('  Unexpected error:', error.message);
            assert.fail('Assertion failed: Should not have caught an error.');
        });

    // Scenario 2: Synchronous function that throws an error
    console.log('\n--- Scenario 2: Synchronous function throwing an error ---');
    Promise.try(() => {
        console.log('  Executing synchronous function that will throw...');
        throw new Error('Synchronous error!');
    })
        .then(result => {
            console.log('  Unexpected resolution:', result);
            assert.fail('Assertion failed: Should not have resolved.');
        })
        .catch(error => {
            console.log(`  Promise.try caught expected error: "${error.message}"`);
            assert.strictEqual(error.message, 'Synchronous error!', 'Assertion failed: Error message mismatch.');
        });

    // Scenario 3: Asynchronous function that resolves
    console.log('\n--- Scenario 3: Asynchronous function that resolves ---');
    Promise.try(() => {
        console.log('  Executing asynchronous function that will resolve...');
        return asyncOperation(true);
    })
        .then(result => {
            console.log(`  Promise.try resolved with: "${result}"`);
            assert.strictEqual(result, 'Async operation successful!', 'Assertion failed: Should resolve with async success.');
        })
        .catch(error => {
            console.error('  Unexpected error:', error.message);
            assert.fail('Assertion failed: Should not have caught an error from successful async operation.');
        });

    // Scenario 4: Asynchronous function that rejects
    console.log('\n--- Scenario 4: Asynchronous function that rejects ---');
    Promise.try(() => {
        console.log('  Executing asynchronous function that will reject...');
        return asyncOperation(false);
    })
        .then(result => {
            console.log('  Unexpected resolution:', result);
            assert.fail('Assertion failed: Should not have resolved.');
        })
        .catch(error => {
            console.log(`  Promise.try caught expected error: "${error.message}"`);
            assert.strictEqual(error.message, 'Async operation failed!', 'Assertion failed: Error message mismatch.');
        });

    console.log('\n--- All Promise.try() demonstrations initiated. Check console output above for results. ---');
}

try {
    runPromiseTryDemo();
} catch (error) {
    console.error('\n!!! ES2025 Promise.try() Demo FAILED !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}