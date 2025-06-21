// es2018/features/promiseFinally.js
const assert = require('assert');

function simulateAsyncTask(shouldSucceed) {
    console.log(`  Starting async task (shouldSucceed: ${shouldSucceed})...`);
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            if (shouldSucceed) {
                console.log('  Async task resolved!');
                resolve('Operation successful');
            } else {
                console.log('  Async task rejected!');
                reject(new Error('Operation failed'));
            }
        }, 100); // Simulate network delay
    });
}

async function runPromiseFinallyDemo() {
    console.log('--- ES2018: Promise.prototype.finally() ---');

    // --- Case 1: Successful Promise ---
    console.log('\n--- Demonstrating finally() with a successful promise ---');
    let status1 = 'pending';
    try {
        await simulateAsyncTask(true)
            .then(result => {
                status1 = `SUCCESS: ${result}`;
                console.log(`  .then block executed: ${status1}`);
            })
            .catch(error => {
                status1 = `ERROR: ${error.message}`;
                console.log(`  .catch block executed: ${status1}`);
            })
            .finally(() => {
                // This block will always run
                status1 += ' (finally executed)';
                console.log('  .finally block executed: Cleaning up resources for successful task...');
            });
        console.log(`Final status for successful task: ${status1}`);
        assert.ok(status1.includes('SUCCESS') && status1.includes('(finally executed)'), 'Assertion Failed: Finally not executed on success');
        console.log('Assertion Passed: finally() executed after successful promise.');
    } catch (e) {
        console.error('Unexpected error in successful promise demo:', e.message);
        assert.fail('Unexpected error in successful promise demo');
    }

    // --- Case 2: Failing Promise ---
    console.log('\n--- Demonstrating finally() with a failing promise ---');
    let status2 = 'pending';
    try {
        await simulateAsyncTask(false)
            .then(result => {
                status2 = `SUCCESS: ${result}`;
                console.log(`  .then block executed: ${status2}`);
            })
            .catch(error => {
                status2 = `ERROR: ${error.message}`;
                console.log(`  .catch block executed: ${status2}`);
                // Re-throw the error to demonstrate finally still runs even if a catch is present
                throw error;
            })
            .finally(() => {
                // This block will always run
                status2 += ' (finally executed)';
                console.log('  .finally block executed: Cleaning up resources for failed task...');
            });
        // This part should not be reached if error is re-thrown and not caught outside
        console.log(`Final status for failed task: ${status2}`);
        assert.fail('Assertion Failed: This line should not be reached as error was re-thrown.');
    } catch (e) {
        console.log(`Caught (re-thrown) error outside chain for demo purposes: ${e.message}`);
        assert.ok(status2.includes('ERROR') && status2.includes('(finally executed)'), 'Assertion Failed: Final status not correct even if re-thrown');
        console.log('Assertion Passed: Error handled and finally() executed.');
    }

    console.log('\n--- All Promise.prototype.finally() demonstrations complete. ---');
}

// Call the async function
runPromiseFinallyDemo().catch(error => {
    console.error('\n!!! ES2018 Promise.finally() Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1); // Exit with a non-zero code to indicate failure
});