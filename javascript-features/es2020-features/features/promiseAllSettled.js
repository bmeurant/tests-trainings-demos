// es2020-features/features/promiseAllSettled.js
const assert = require('assert');

function createPromise(id, shouldSucceed, delay) {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            if (shouldSucceed) {
                console.log(`  Promise ${id}: Fulfilled!`);
                resolve(`Value from ${id}`);
            } else {
                console.log(`  Promise ${id}: Rejected!`);
                reject(new Error(`Error from ${id}`));
            }
        }, delay);
    });
}

async function runPromiseAllSettledDemo() {
    console.log('--- ES2020: Promise.allSettled() ---');

    // --- 1. Mix of fulfilled and rejected promises ---
    console.log('\n--- Handling a mix of fulfilled and rejected promises ---');
    const promises = [
        createPromise('A', true, 100),  // Fulfilled
        createPromise('B', false, 50), // Rejected
        createPromise('C', true, 150),  // Fulfilled
        createPromise('D', false, 200) // Rejected
    ];

    console.log('Running Promise.allSettled with multiple promises...');
    try {
        const results = await Promise.allSettled(promises);
        console.log('All promises settled. Results:');
        results.forEach((result, index) => {
            console.log(`  Promise ${String.fromCharCode(65 + index)} status: ${result.status}`);
            if (result.status === 'fulfilled') {
                console.log(`    Value: ${result.value}`);
            } else {
                console.log(`    Reason: ${result.reason.message}`);
            }
        });

        // Assertions
        assert.strictEqual(results.length, 4, 'Assertion Failed: Incorrect number of results');
        assert.strictEqual(results[0].status, 'fulfilled', 'Assertion Failed: Promise A not fulfilled');
        assert.strictEqual(results[0].value, 'Value from A', 'Assertion Failed: Promise A value incorrect');
        assert.strictEqual(results[1].status, 'rejected', 'Assertion Failed: Promise B not rejected');
        assert.strictEqual(results[1].reason.message, 'Error from B', 'Assertion Failed: Promise B reason incorrect');
        assert.strictEqual(results[2].status, 'fulfilled', 'Assertion Failed: Promise C not fulfilled');
        assert.strictEqual(results[3].status, 'rejected', 'Assertion Failed: Promise D not rejected');
        console.log('Assertion Passed: Promise.allSettled correctly collected all results.');

    } catch (error) {
        console.error('!!! Unexpected error in Promise.allSettled demo:', error.message);
        assert.fail('Assertion Failed: Promise.allSettled should not throw error if promises settle');
    }

    // --- 2. All fulfilled promises ---
    console.log('\n--- Handling all fulfilled promises ---');
    const allFulfilledPromises = [
        createPromise('X', true, 30),
        createPromise('Y', true, 80)
    ];
    console.log('Running Promise.allSettled with all fulfilled promises...');
    const fulfilledResults = await Promise.allSettled(allFulfilledPromises);
    assert.strictEqual(fulfilledResults[0].status, 'fulfilled', 'Assertion Failed: Promise X not fulfilled');
    assert.strictEqual(fulfilledResults[1].status, 'fulfilled', 'Assertion Failed: Promise Y not fulfilled');
    console.log('Assertion Passed: Correctly handled all fulfilled promises.');


    // --- 3. All rejected promises ---
    console.log('\n--- Handling all rejected promises ---');
    const allRejectedPromises = [
        createPromise('P', false, 60),
        createPromise('Q', false, 10)
    ];
    console.log('Running Promise.allSettled with all rejected promises...');
    const rejectedResults = await Promise.allSettled(allRejectedPromises);
    assert.strictEqual(rejectedResults[0].status, 'rejected', 'Assertion Failed: Promise P not rejected');
    assert.strictEqual(rejectedResults[1].status, 'rejected', 'Assertion Failed: Promise Q not rejected');
    console.log('Assertion Passed: Correctly handled all rejected promises.');

    console.log('\n--- All Promise.allSettled() demonstrations complete. ---');
}

// Call the async function
runPromiseAllSettledDemo().catch(error => {
    console.error('\n!!! ES2020 Promise.allSettled() Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
});