// es2021-features/features/aggregateError.js
const assert = require('assert');

async function runAggregateErrorDemo() {
    console.log('--- ES2021: AggregateError ---');

    // --- 1. AggregateError with Promise.any() (as seen above) ---
    console.log('\n--- 1. AggregateError with Promise.any() (Recap) ---');

    function createFailingPromise(id, delayMs) {
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                console.log(`  Failing Promise ${id}: Rejected!`);
                reject(new Error(`Failed with ID ${id}`));
            }, delayMs);
        });
    }

    const failingPromises = [
        createFailingPromise('P1', 50),
        createFailingPromise('P2', 100),
        createFailingPromise('P3', 20)
    ];

    console.log('Running Promise.any() with all failing promises...');
    try {
        await Promise.any(failingPromises)
            .then(result => {
                console.log('This should not be reached: Promise.any should reject if all fail.');
                assert.fail('Assertion Failed: Promise.any resolved unexpectedly.');
            })
            .catch(error => {
                console.log(`Caught expected error from Promise.any(): ${error.name}`);
                assert.ok(error instanceof AggregateError, 'Assertion Failed: Error should be an AggregateError.');
                assert.strictEqual(error.errors.length, 3, 'Assertion Failed: AggregateError should contain 3 errors.');
                console.log('  Errors within AggregateError:');
                error.errors.forEach((err, index) => {
                    console.log(`    [${index}] ${err.message}`);
                });
                assert.ok(error.errors.some(e => e.message === 'Failed with ID P1'), 'Assertion Failed: Missing P1 error.');
                assert.ok(error.errors.some(e => e.message === 'Failed with ID P2'), 'Assertion Failed: Missing P2 error.');
                assert.ok(error.errors.some(e => e.message === 'Failed with ID P3'), 'Assertion Failed: Missing P3 error.');
                console.log('Assertion Passed: Promise.any correctly throws AggregateError with all rejection reasons.');
            });
    } catch (error) {
        console.error('Unexpected error in Promise.any demo:', error);
    }

    // --- 2. Manually creating and throwing an AggregateError ---
    console.log('\n--- 2. Manually creating and throwing an AggregateError ---');

    function processBatch(items) {
        const errors = [];
        const results = [];

        for (const item of items) {
            try {
                if (item % 2 !== 0) { // Simulate an error for odd numbers
                    throw new Error(`Item ${item} is odd, cannot process.`);
                }
                results.push(`Processed ${item}`);
            } catch (e) {
                errors.push(e);
            }
        }

        if (errors.length > 0) {
            throw new AggregateError(errors, 'Batch processing failed due to multiple errors.');
        }

        return results;
    }

    console.log('\nAttempting to process a batch with errors...');
    try {
        const batchResults = processBatch([2, 3, 4, 5, 6]);
        console.log('Batch results (should not be reached):', batchResults);
        assert.fail('Assertion Failed: Batch processing with errors should have thrown AggregateError.');
    } catch (error) {
        console.log(`Caught expected error from batch processing: ${error.name}`);
        assert.ok(error instanceof AggregateError, 'Assertion Failed: Caught error should be an AggregateError.');
        assert.strictEqual(error.message, 'Batch processing failed due to multiple errors.', 'Assertion Failed: AggregateError message incorrect.');
        assert.strictEqual(error.errors.length, 2, 'Assertion Failed: AggregateError should contain 2 errors for odd numbers.');
        console.log('  Errors within manually created AggregateError:');
        error.errors.forEach((err, index) => {
            console.log(`    [${index}] ${err.message}`);
        });
        assert.ok(error.errors.some(e => e.message === 'Item 3 is odd, cannot process.'), 'Assertion Failed: Missing error for item 3.');
        assert.ok(error.errors.some(e => e.message === 'Item 5 is odd, cannot process.'), 'Assertion Failed: Missing error for item 5.');
        console.log('Assertion Passed: Manually created AggregateError functions as expected.');
    }

    console.log('\n--- All AggregateError demonstrations complete. ---');
}

runAggregateErrorDemo().catch(error => {
    console.error('\n!!! ES2021 AggregateError Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
});