// es2020-features/features/globalThis.js
const assert = require('assert');

function runGlobalThisDemo() {
    console.log('--- ES2020: globalThis ---');

    // --- 1. Accessing globalThis ---
    console.log('\n--- Accessing globalThis ---');
    console.log('Type of globalThis:', typeof globalThis);
    console.log('Is globalThis defined?', globalThis !== undefined);
    assert.ok(globalThis !== undefined, 'Assertion Failed: globalThis is undefined');
    console.log('Assertion Passed: globalThis is defined.');

    // --- 2. Verifying it refers to the actual global object ---
    console.log('\n--- Verifying it refers to the actual global object ---');
    // In Node.js, globalThis should be 'global'
    // In browsers, globalThis should be 'window' or 'self'
    if (typeof process !== 'undefined' && process.versions && process.versions.node) {
        // Running in Node.js
        console.log('Running in Node.js environment.');
        assert.strictEqual(globalThis, global, 'Assertion Failed: globalThis is not global in Node.js');
        console.log('Assertion Passed: globalThis points to `global` in Node.js.');
    } else if (typeof window !== 'undefined') {
        // Running in browser environment (might need to open this file in a browser for true test)
        console.log('Running in browser-like environment (window detected).');
        assert.strictEqual(globalThis, window, 'Assertion Failed: globalThis is not window in browser');
        console.log('Assertion Passed: globalThis points to `window` in browser.');
    } else if (typeof self !== 'undefined') {
        // Running in Web Worker environment
        console.log('Running in Web Worker-like environment (self detected).');
        assert.strictEqual(globalThis, self, 'Assertion Failed: globalThis is not self in Web Worker');
        console.log('Assertion Passed: globalThis points to `self` in Web Worker.');
    } else {
        console.log('Could not determine specific global object, but globalThis is present.');
    }

    // --- 3. Storing and retrieving global properties ---
    console.log('\n--- Storing and Retrieving Global Properties ---');
    globalThis.myGlobalVariable = 'Hello from globalThis!';
    console.log('Value of myGlobalVariable via globalThis:', globalThis.myGlobalVariable);
    assert.strictEqual(globalThis.myGlobalVariable, 'Hello from globalThis!', 'Assertion Failed: Cannot set/get property on globalThis');
    console.log('Assertion Passed: Properties can be set and retrieved via globalThis.');

    // Also accessible directly if not in strict module scope
    console.log('Value of myGlobalVariable directly:', myGlobalVariable); // This works in Node.js script scope
    assert.strictEqual(myGlobalVariable, 'Hello from globalThis!', 'Assertion Failed: Global variable not directly accessible');
    console.log('Assertion Passed: Global variable directly accessible (in non-strict module scope).');


    console.log('\n--- All globalThis demonstrations complete. ---');
}

try {
    runGlobalThisDemo();
} catch (error) {
    console.error('\n!!! ES2020 globalThis Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}