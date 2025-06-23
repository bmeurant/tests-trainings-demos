// es2019-features/features/optionalCatchBinding.js
const assert = require('assert');

function runOptionalCatchBindingDemo() {
    console.log('--- ES2019: Optional Catch Binding ---');

    // --- 1. Catch block without an error variable ---
    console.log('\n--- Catch block without error variable ---');
    let status1 = 'initial';
    try {
        console.log('  Attempting to throw an error...');
        throw new Error('Something went wrong!');
    } catch { // No 'error' variable here
        status1 = 'caught error (no variable)';
        console.log('  Error caught successfully. (No need to bind the error object)');
    }
    assert.strictEqual(status1, 'caught error (no variable)', 'Assertion Failed: Catch block without binding did not execute');
    console.log('Assertion Passed: Catch block executed without explicitly binding error.');

    // --- 2. Catch block with an error variable (for comparison) ---
    console.log('\n--- Catch block with error variable (for comparison) ---');
    let status2 = 'initial';
    let caughtError = null;
    try {
        console.log('  Attempting to throw another error...');
        throw new TypeError('Invalid type!');
    } catch (e) { // 'e' variable is bound
        status2 = 'caught error (with variable)';
        caughtError = e;
        console.log(`  Error caught successfully. Type: ${e.name}, Message: ${e.message}`);
    }
    assert.strictEqual(status2, 'caught error (with variable)', 'Assertion Failed: Catch block with binding did not execute');
    assert.strictEqual(caughtError instanceof TypeError, true, 'Assertion Failed: Caught error is not TypeError');
    console.log('Assertion Passed: Catch block executed with error bound to a variable.');

    // --- 3. Function demonstrating throwing and catching without binding ---
    console.log('\n--- Function demonstrating optional catch ---');
    function performRiskyOperation(shouldFail) {
        try {
            if (shouldFail) {
                console.log('    Operation failing...');
                throw new Error('Deliberate failure');
            }
            console.log('    Operation succeeded.');
            return 'Success';
        } catch {
            console.log('    Operation failed and caught. Cleanup performed.');
            return 'Failed';
        }
    }

    const result1 = performRiskyOperation(false);
    console.log(`  Result of successful operation: ${result1}`);
    assert.strictEqual(result1, 'Success', 'Assertion Failed: Function with optional catch did not return Success');
    console.log('  Assertion Passed: Function handled success case.');

    const result2 = performRiskyOperation(true);
    console.log(`  Result of failing operation: ${result2}`);
    assert.strictEqual(result2, 'Failed', 'Assertion Failed: Function with optional catch did not return Failed');
    console.log('  Assertion Passed: Function handled failure case with optional catch.');

    console.log('\n--- All Optional Catch Binding demonstrations complete. ---');
}

try {
    runOptionalCatchBindingDemo();
} catch (error) {
    console.error('\n!!! ES2019 Optional Catch Binding Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}