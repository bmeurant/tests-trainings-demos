// es2022-features/features/errorCause.js
const assert = require('assert');

function runErrorCauseDemo() {
    console.log('--- ES2022: Error.cause ---');

    // --- 1. Basic error chaining ---
    console.log('\n--- 1. Basic error chaining ---');
    try {
        try {
            throw new Error('Original network error: Connection timed out.');
        } catch (networkError) {
            // Wrap the network error in a more descriptive error
            const userFacingError = new Error('Failed to fetch user data.', { cause: networkError });
            throw userFacingError;
        }
    } catch (finalError) {
        console.log(`Caught final error: ${finalError.message}`);
        assert.strictEqual(finalError.message, 'Failed to fetch user data.', 'Assertion Failed: Final error message incorrect.');

        if (finalError.cause) {
            console.log(`  Cause of error: ${finalError.cause.message}`);
            assert.strictEqual(finalError.cause.message, 'Original network error: Connection timed out.', 'Assertion Failed: Error cause message incorrect.');
            assert.ok(finalError.cause instanceof Error, 'Assertion Failed: Cause is not an Error instance.');
        } else {
            assert.fail('Assertion Failed: Error.cause was not set.');
        }
        console.log('Assertion Passed: Error.cause successfully chains errors.');
    }

    // --- 2. Chaining with different error types ---
    console.log('\n--- 2. Chaining with different error types ---');
    try {
        try {
            // Simulate a validation error
            const validationError = new TypeError('Invalid input: Expected a number, got string.');
            throw validationError;
        } catch (inputError) {
            // Wrap it in a service-specific error
            const serviceError = new Error('Processing failed during data conversion.', { cause: inputError });
            throw serviceError;
        }
    } catch (topLevelError) {
        console.log(`Caught top-level error: ${topLevelError.message}`);
        assert.strictEqual(topLevelError.message, 'Processing failed during data conversion.', 'Assertion Failed: Top-level error message incorrect.');

        if (topLevelError.cause) {
            console.log(`  Cause of top-level error: ${topLevelError.cause.message}`);
            assert.strictEqual(topLevelError.cause.message, 'Invalid input: Expected a number, got string.', 'Assertion Failed: Error cause message for different type incorrect.');
            assert.ok(topLevelError.cause instanceof TypeError, 'Assertion Failed: Cause is not a TypeError instance.');
        } else {
            assert.fail('Assertion Failed: Error.cause was not set for different error type.');
        }
        console.log('Assertion Passed: Error.cause works with different error types.');
    }

    // --- 3. Absence of cause when not provided ---
    console.log('\n--- 3. Absence of cause when not provided ---');
    const simpleError = new Error('A simple error.');
    console.log(`Simple error: ${simpleError.message}`);
    assert.strictEqual(simpleError.cause, undefined, 'Assertion Failed: Error.cause should be undefined when not provided.');
    console.log('Assertion Passed: Error.cause is undefined if not explicitly set.');

    console.log('\n--- All Error.cause demonstrations complete. ---');
}

try {
    runErrorCauseDemo();
} catch (error) {
    console.error('\n!!! ES2022 Error.cause Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}