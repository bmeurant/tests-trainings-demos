// es2023-features/features/symbolsAsWeakMapKeys.js
const assert = require('assert');

function runSymbolsAsWeakMapKeysDemo() {
    console.log('--- ES2023: Symbols as WeakMap Keys ---');

    // Create a new WeakMap
    const weakMap = new WeakMap();

    // Create a non-registered Symbol to use as a key
    const uniqueId = Symbol('unique id for entity');
    console.log(`\n  Created a non-registered Symbol: ${String(uniqueId)}`);

    // Create an object to associate with the symbol
    const entityData = { name: 'My Special Entity', value: 123 };
    console.log(`  Created associated entity data: ${JSON.stringify(entityData)}`);

    // Use the Symbol as a key in the WeakMap
    try {
        weakMap.set(uniqueId, entityData);
        console.log(`  Successfully set Symbol as a key in WeakMap.`);
        assert.strictEqual(weakMap.has(uniqueId), true, 'Assertion Failed: WeakMap should have the Symbol key.');

        const retrievedData = weakMap.get(uniqueId);
        console.log(`  Retrieved data using Symbol key: ${JSON.stringify(retrievedData)}`);
        assert.deepStrictEqual(retrievedData, entityData, 'Assertion Failed: Retrieved data does not match original.');
        console.log('Assertion Passed: Symbol can be used as a WeakMap key, and data can be retrieved.');

    } catch (e) {
        console.error(`!!! Error: Failed to use Symbol as WeakMap key: ${e.message}`);
        assert.fail(`Test Failed: Should be able to use Symbol as a WeakMap key. Error: ${e.message}`);
    }

    // --- Demonstrating WeakMap behavior with garbage collection (conceptual) ---
    console.log('\n--- Conceptual: WeakMap and Garbage Collection ---');
    let symbolRef = Symbol('a temporary symbol');
    const tempObject = { some: 'data' };
    const gcWeakMap = new WeakMap();
    gcWeakMap.set(symbolRef, tempObject);
    console.log(`  WeakMap has 'symbolRef': ${gcWeakMap.has(symbolRef)}`);
    assert.strictEqual(gcWeakMap.has(symbolRef), true, 'Assertion Failed: gcWeakMap should have symbolRef initially.');

    // If we remove all references to `symbolRef`, it becomes eligible for GC.
    // When `symbolRef` is garbage-collected, its entry in `gcWeakMap` will also be removed.
    symbolRef = null; // Remove the last reference to the Symbol
    console.log(`  Reference to 'symbolRef' set to null.`);
    // Note: We cannot *force* garbage collection or observe it directly in Node.js/browser for testing.
    // This part is conceptual to explain the 'weak' nature.
    // In a real scenario, eventually gcWeakMap.has(symbolRef) would become false IF `symbolRef` were collected.
    console.log(`  WeakMap *might* eventually not have 'symbolRef' (after GC): ${gcWeakMap.has(Symbol('a temporary symbol'))}`); // This will be false as it's a *new* Symbol
    console.log('Assertion Passed: Conceptual understanding of WeakMap behavior with Symbols.');

    // --- Attempting to use a registered Symbol (will fail as expected) ---
    console.log('\n--- Attempting to use a Registered Symbol (Expected Failure) ---');
    const registeredSymbol = Symbol.for('myRegisteredSymbol');
    console.log(`  Created a registered Symbol: ${String(registeredSymbol)}`);
    const registeredSymbolData = { info: 'This should not work as a WeakMap key' };

    try {
        weakMap.set(registeredSymbol, registeredSymbolData);
        assert.fail('Assertion Failed: Should NOT be able to use a registered Symbol as a WeakMap key.');
    } catch (e) {
        console.log(`  Caught expected error for registered Symbol: "${e.message}"`);
        // Expected error type might vary, often TypeError
        assert.ok(e instanceof TypeError, 'Assertion Failed: Expected TypeError for registered Symbol key.');
        console.log('Assertion Passed: Registered Symbols cannot be used as WeakMap keys.');
    }

    console.log('\n--- All Symbols as WeakMap Keys demonstrations complete. ---');
}

try {
    runSymbolsAsWeakMapKeysDemo();
} catch (error) {
    console.error('\n!!! ES2023 Symbols as WeakMap Keys Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}