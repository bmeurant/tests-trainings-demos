// es2023-features/features/findLastMethods.js
const assert = require('assert');

function runFindLastMethodsDemo() {
    console.log('--- ES2023: Array.prototype.findLast() and findLastIndex() ---');

    const numbers = [
        { id: 1, value: 10 },
        { id: 2, value: 20 },
        { id: 3, value: 30 },
        { id: 4, value: 20 },
        { id: 5, value: 40 },
        { id: 6, value: 10 }
    ];
    console.log('Original Array:', numbers);

    // --- 1. findLast() ---
    console.log('\n--- 1. findLast() ---');

    // Find the last object with value > 25
    const lastGreaterThan25 = numbers.findLast(obj => obj.value > 25);
    console.log('  Last object with value > 25:', lastGreaterThan25); // Expected: { id: 5, value: 40 }
    assert.deepStrictEqual(lastGreaterThan25, { id: 5, value: 40 }, 'Assertion Failed: findLast for >25 incorrect.');

    // Find the last occurrence of value === 20
    const lastTwenty = numbers.findLast(obj => obj.value === 20);
    console.log(`  Last object with value=20:`, lastTwenty); // Expected: { id: 4, value: 20 }
    assert.strictEqual(lastTwenty.value, 20, 'Assertion Failed: findLast for value=20 incorrect.');
    assert.strictEqual(lastTwenty.id, 4, 'Assertion Failed: lastTwenty id incorrect.');

    // Find an object that doesn't exist
    const notFound = numbers.findLast(obj => obj.value > 100);
    console.log('  Last object with value > 100:', notFound); // Expected: undefined
    assert.strictEqual(notFound, undefined, 'Assertion Failed: findLast for non-existent item incorrect.');
    console.log('Assertion Passed: findLast works correctly.');

    // --- 2. findLastIndex() ---
    console.log('\n--- 2. findLastIndex() ---');

    // Find the index of the last object with value > 25
    const lastIndexGreaterThan25 = numbers.findLastIndex(obj => obj.value > 25);
    console.log('  Index of last object with value > 25:', lastIndexGreaterThan25); // Expected: 4
    assert.strictEqual(lastIndexGreaterThan25, 4, 'Assertion Failed: findLastIndex for >25 incorrect.');

    // Find the index of the last occurrence of value === 20
    // (already done above as lastTwentyIndex)
    // Find index of an object that doesn't exist
    const notFoundIndex = numbers.findLastIndex(obj => obj.value > 100);
    console.log('  Index of last object with value > 100:', notFoundIndex); // Expected: -1
    assert.strictEqual(notFoundIndex, -1, 'Assertion Failed: findLastIndex for non-existent item incorrect.');
    console.log('Assertion Passed: findLastIndex works correctly.');

    // --- 3. Comparison with find() and findIndex() ---
    console.log('\n--- 3. Comparison with find() and findIndex() ---');
    const firstTwentyIndex = numbers.findIndex(obj => obj.value === 20);
    const firstTwenty = numbers[firstTwentyIndex];
    console.log(`  First occurrence of value=20:`, firstTwenty, `at index ${firstTwentyIndex}`); // Expected: { id: 2, value: 20 } at index 1
    assert.strictEqual(firstTwenty.value, 20, 'Assertion Failed: find for value=20 incorrect.');
    assert.strictEqual(firstTwentyIndex, 1, 'Assertion Failed: findIndex for value=20 incorrect.');
    assert.strictEqual(firstTwenty.id, 2, 'Assertion Failed: firstTwenty id incorrect.');

    console.log('\n--- All findLast/findLastIndex demonstrations complete. ---');
}

try {
    runFindLastMethodsDemo();
} catch (error) {
    console.error('\n!!! ES2023 findLast/findLastIndex Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}