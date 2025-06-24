// es2023-features/features/arrayImmutableMethods.js
const assert = require('assert');

function runArrayImmutableMethodsDemo() {
    console.log('--- ES2023: Non-destructive Array methods (toSorted, toReversed, toSpliced, with) ---');

    const originalNumbers = [3, 1, 4, 1, 5, 9];
    console.log(`Original Numbers: [${originalNumbers}]`);

    // --- 1. toSorted() ---
    console.log('\n--- 1. toSorted() ---');
    const sortedNumbers = originalNumbers.toSorted((a, b) => a - b);
    console.log(`  originalNumbers.toSorted(): [${sortedNumbers}]`);
    console.log(`  Original array after toSorted(): [${originalNumbers}]`); // Should be unchanged
    assert.deepStrictEqual(sortedNumbers, [1, 1, 3, 4, 5, 9], 'Assertion Failed: toSorted result incorrect.');
    assert.deepStrictEqual(originalNumbers, [3, 1, 4, 1, 5, 9], 'Assertion Failed: original array mutated by toSorted.');
    console.log('Assertion Passed: toSorted correctly returns a new sorted array without modifying the original.');

    // --- 2. toReversed() ---
    console.log('\n--- 2. toReversed() ---');
    const reversedNumbers = originalNumbers.toReversed();
    console.log(`  originalNumbers.toReversed(): [${reversedNumbers}]`);
    console.log(`  Original array after toReversed(): [${originalNumbers}]`); // Should be unchanged
    assert.deepStrictEqual(reversedNumbers, [9, 5, 1, 4, 1, 3], 'Assertion Failed: toReversed result incorrect.');
    assert.deepStrictEqual(originalNumbers, [3, 1, 4, 1, 5, 9], 'Assertion Failed: original array mutated by toReversed.');
    console.log('Assertion Passed: toReversed correctly returns a new reversed array without modifying the original.');

    // --- 3. toSpliced() ---
    console.log('\n--- 3. toSpliced() ---');
    const splicedNumbersRemove = originalNumbers.toSpliced(2, 2); // Remove 2 elements from index 2
    console.log(`  originalNumbers.toSpliced(2, 2): [${splicedNumbersRemove}]`);
    assert.deepStrictEqual(splicedNumbersRemove, [3, 1, 5, 9], 'Assertion Failed: toSpliced (remove) result incorrect.');
    assert.deepStrictEqual(originalNumbers, [3, 1, 4, 1, 5, 9], 'Assertion Failed: original array mutated by toSpliced (remove).');

    const splicedNumbersAdd = originalNumbers.toSpliced(2, 0, 7, 8); // Add 7, 8 at index 2
    console.log(`  originalNumbers.toSpliced(2, 0, 7, 8): [${splicedNumbersAdd}]`);
    assert.deepStrictEqual(splicedNumbersAdd, [3, 1, 7, 8, 4, 1, 5, 9], 'Assertion Failed: toSpliced (add) result incorrect.');
    assert.deepStrictEqual(originalNumbers, [3, 1, 4, 1, 5, 9], 'Assertion Failed: original array mutated by toSpliced (add).');

    const splicedNumbersReplace = originalNumbers.toSpliced(2, 1, 77, 82); // Replace 1 element at index 2 with 77
    console.log(`  originalNumbers.toSpliced(2, 1, 77, 82): [${splicedNumbersReplace}]`);
    assert.deepStrictEqual(splicedNumbersReplace, [3, 1, 77, 82, 1, 5, 9], 'Assertion Failed: toSpliced (replace) result incorrect.');
    assert.deepStrictEqual(originalNumbers, [3, 1, 4, 1, 5, 9], 'Assertion Failed: original array mutated by toSpliced (replace).');
    console.log('Assertion Passed: toSpliced correctly returns a new array with modifications without modifying the original.');


    // --- 4. with() ---
    console.log('\n--- 4. with(index, value) ---');
    const originalFruits = ['apple', 'banana', 'cherry', 'date'];
    console.log(`  Original Fruits: [${originalFruits}]`);

    const fruitsWithUpdatedSecond = originalFruits.with(1, 'blueberry'); // Change 'banana' to 'blueberry'
    console.log(`  originalFruits.with(1, 'blueberry'): [${fruitsWithUpdatedSecond}]`);
    assert.deepStrictEqual(fruitsWithUpdatedSecond, ['apple', 'blueberry', 'cherry', 'date'], 'Assertion Failed: with positive index result incorrect.');
    assert.deepStrictEqual(originalFruits, ['apple', 'banana', 'cherry', 'date'], 'Assertion Failed: original array mutated by with (positive).');

    const fruitsWithUpdatedLast = originalFruits.with(-1, 'elderberry'); // Change 'date' to 'elderberry'
    console.log(`  originalFruits.with(-1, 'elderberry'): [${fruitsWithUpdatedLast}]`);
    assert.deepStrictEqual(fruitsWithUpdatedLast, ['apple', 'banana', 'cherry', 'elderberry'], 'Assertion Failed: with negative index result incorrect.');
    assert.deepStrictEqual(originalFruits, ['apple', 'banana', 'cherry', 'date'], 'Assertion Failed: original array mutated by with (negative).');

    // Test with out-of-bounds index (should throw RangeError)
    console.log(`  Attempting originalFruits.with(99, 'grape')...`);
    let rangeErrorCaught = false;
    try {
        originalFruits.with(99, 'grape');
    } catch (e) {
        if (e instanceof RangeError) {
            console.log(`  Caught expected RangeError: ${e.message}`);
            rangeErrorCaught = true;
        } else {
            throw e; // Re-throw unexpected errors
        }
    }
    assert.strictEqual(rangeErrorCaught, true, 'Assertion Failed: with() did not throw RangeError for out-of-bounds index.');
    console.log('Assertion Passed: with correctly returns a new array with a modified element, and throws RangeError for out-of-bounds access.');

    console.log('\n--- All Array Immutable Methods demonstrations complete. ---');
}

try {
    runArrayImmutableMethodsDemo();
} catch (error) {
    console.error('\n!!! ES2023 Array Immutable Methods Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}