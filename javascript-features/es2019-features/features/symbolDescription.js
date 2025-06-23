// es2019-features/features/symbolDescription.js
const assert = require('assert');

function runSymbolDescriptionDemo() {
    console.log('--- ES2019: Symbol.prototype.description ---');

    // --- 1. Creating Symbols with descriptions ---
    console.log('\n--- Creating Symbols with descriptions ---');
    const mySymbol1 = Symbol('uniqueId');
    const mySymbol2 = Symbol('anotherKey');
    const mySymbol3 = Symbol(); // No description

    console.log('Symbol 1:', mySymbol1);
    console.log('Symbol 2:', mySymbol2);
    console.log('Symbol 3 (no description):', mySymbol3);

    // --- 2. Accessing description via .description property ---
    console.log('\n--- Accessing description via .description property ---');
    console.log(`Description of Symbol 1: "${mySymbol1.description}"`);
    console.log(`Description of Symbol 2: "${mySymbol2.description}"`);
    console.log(`Description of Symbol 3: "${mySymbol3.description}"`);

    assert.strictEqual(mySymbol1.description, 'uniqueId', 'Assertion Failed: Description of mySymbol1 incorrect');
    assert.strictEqual(mySymbol2.description, 'anotherKey', 'Assertion Failed: Description of mySymbol2 incorrect');
    assert.strictEqual(mySymbol3.description, undefined, 'Assertion Failed: Description of mySymbol3 should be undefined');
    console.log('Assertion Passed: Symbol descriptions accessed correctly.');

    // --- 3. Old way (for comparison) ---
    console.log('\n--- Old way (for comparison, converting to string) ---');
    const oldWayDescription = String(mySymbol1).slice(7, -1); // Extracts 'uniqueId' from 'Symbol(uniqueId)'
    console.log(`Description of Symbol 1 (old way): "${oldWayDescription}"`);
    assert.strictEqual(oldWayDescription, 'uniqueId', 'Assertion Failed: Old way description extraction incorrect');
    console.log('Assertion Passed: Old way also extracts description, but less elegantly.');

    console.log('\n--- All Symbol.prototype.description demonstrations complete. ---');
}

try {
    runSymbolDescriptionDemo();
} catch (error) {
    console.error('\n!!! ES2019 Symbol.prototype.description Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}