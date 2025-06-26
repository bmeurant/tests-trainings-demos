// es2025-features/features/syncIteratorHelpers.js
const assert = require('assert');

// A simple custom iterable object to demonstrate iterator helpers
function* numberGenerator(limit) {
    let i = 0;
    while (i < limit) {
        yield i++;
    }
}

function runIteratorHelpersDemo() {
    console.log('\n--- ES2025: Synchronous Iterator Helpers ---');

    // --- 1. .map() ---
    console.log('\n--- 1. .map() ---');
    // Map each number to its square
    const squaredNumbersIterator = numberGenerator(10).map(n => n * n);
    const squaredNumbers = [...squaredNumbersIterator]; // Use spread to consume the iterator
    console.log(`Squared numbers (using .map()): [${squaredNumbers.join(', ')}]`);
    assert.deepStrictEqual(squaredNumbers, [0, 1, 4, 9, 16, 25, 36, 49, 64, 81], 'Assertion failed: .map() result mismatch.');
    console.log('Assertion passed: .map() works as expected.');

    // --- 2. .filter() ---
    console.log('\n--- 2. .filter() ---');
    const evenNumbersIterator = numberGenerator(10).filter(n => n % 2 === 0);
    const evenNumbers = [...evenNumbersIterator];
    console.log(`Even numbers (using .filter()): [${evenNumbers.join(', ')}]`);
    assert.deepStrictEqual(evenNumbers, [0, 2, 4, 6, 8], 'Assertion failed: .filter() result mismatch.');
    console.log('Assertion passed: .filter() works as expected.');

    // --- 3. Chaining operations: .filter().map().take() ---
    console.log('\n--- 3. Chaining operations: .filter().map().take() ---');
    // Get the first 3 squared even numbers
    const first3SquaredEven = numberGenerator(10)
        .filter(n => n % 2 === 0)       // Get even numbers
        .map(n => n * n)                // Square them
        .take(3)                        // Take the first 3
        .toArray();                     // Convert to Array

    console.log(`First 3 squared even numbers (0-9): [${first3SquaredEven.join(', ')}]`);
    assert.deepStrictEqual(first3SquaredEven, [0, 4, 16], 'Assertion failed: Chained operations result mismatch.');
    console.log('Assertion passed: Chaining .filter().map().take().toArray() works.');

    // --- 4. .drop() ---
    console.log('\n--- 4. .drop() ---');
    const droppedNumbersIterator = numberGenerator(10).drop(5); // Drop first 5 elements
    const droppedNumbers = [...droppedNumbersIterator];
    console.log(`Numbers after dropping first 5: [${droppedNumbers.join(', ')}]`);
    assert.deepStrictEqual(droppedNumbers, [5, 6, 7, 8, 9], 'Assertion failed: .drop() result mismatch.');
    console.log('Assertion passed: .drop() works as expected.');

    // --- 5. .find() ---
    console.log('\n--- 5. .find() ---');
    const foundNumber = numberGenerator(10).find(n => n > 7); // Find first number greater than 7
    console.log(`First number greater than 7 (using .find()): ${foundNumber}`);
    assert.strictEqual(foundNumber, 8, 'Assertion failed: .find() result mismatch.');

    const notFoundNumber = numberGenerator(5).find(n => n > 10); // Find number not present
    console.log(`Number greater than 10 in 0-4 (using .find()): ${notFoundNumber}`);
    assert.strictEqual(notFoundNumber, undefined, 'Assertion failed: .find() for non-existent item mismatch.');
    console.log('Assertion passed: .find() works as expected.');

    // --- 6. .forEach() ---
    console.log('\n--- 6. .forEach() ---');
    let sum = 0;
    numberGenerator(5).forEach(n => sum += n); // Sum numbers 0-4
    console.log(`Sum of numbers 0-4 (using .forEach()): ${sum}`);
    assert.strictEqual(sum, 10, 'Assertion failed: .forEach() sum mismatch.');
    console.log('Assertion passed: .forEach() works as expected.');

    console.log('\n--- All Synchronous Iterator Helpers demonstrations complete. ---');
}

try {
    runIteratorHelpersDemo();
} catch (error) {
    console.error('\n!!! ES2025 Synchronous Iterator Helpers Demo FAILED !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}