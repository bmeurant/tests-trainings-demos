// es2019-features/features/arrayFlat.js
const assert = require('assert');

function runArrayFlatDemo() {
    console.log('--- ES2019: Array.prototype.flat() ---');

    // --- 1. Flattening one level deep (default behavior) ---
    console.log('\n--- Flattening one level deep ---');
    const nestedArray1 = [1, [2, 3], 4, [5, [6, 7]]];
    console.log('Original Array:', nestedArray1);
    const flattenedOnce = nestedArray1.flat();
    console.log('Flattened (1 level):', flattenedOnce);
    assert.deepStrictEqual(flattenedOnce, [1, 2, 3, 4, 5, [6, 7]], 'Assertion Failed: Should flatten 1 level');
    console.log('Assertion Passed: Successfully flattened one level.');

    // --- 2. Flattening multiple levels deep ---
    console.log('\n--- Flattening multiple levels deep (depth = 2) ---');
    const nestedArray2 = [1, [2, 3], 4, [5, [6, 7, [8]]]];
    console.log('Original Array:', nestedArray2);
    const flattenedTwice = nestedArray2.flat(2);
    console.log('Flattened (2 levels):', flattenedTwice);
    assert.deepStrictEqual(flattenedTwice, [1, 2, 3, 4, 5, 6, 7, [8]], 'Assertion Failed: Should flatten 2 levels');
    console.log('Assertion Passed: Successfully flattened two levels.');

    // --- 3. Flattening all levels (Infinity) ---
    console.log('\n--- Flattening all levels (Infinity) ---');
    const deeplyNestedArray = [1, [2, [3, [4, [5, 6], 7], 8], 9], 10];
    console.log('Original Array:', deeplyNestedArray);
    const flattenedAll = deeplyNestedArray.flat(Infinity);
    console.log('Flattened (Infinity):', flattenedAll);
    assert.deepStrictEqual(flattenedAll, [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], 'Assertion Failed: Should flatten all levels');
    console.log('Assertion Passed: Successfully flattened all levels.');

    // --- 4. Handling empty slots in arrays ---
    console.log('\n--- Handling empty slots ---');
    const arrayWithEmptySlots = [1, , 3, [4, , 6]];
    console.log('Original Array:', arrayWithEmptySlots);
    const flattenedWithEmptySlots = arrayWithEmptySlots.flat();
    console.log('Flattened with empty slots:', flattenedWithEmptySlots);
    assert.deepStrictEqual(flattenedWithEmptySlots, [1, 3, 4, 6], 'Assertion Failed: Should remove empty slots during flattening');
    console.log('Assertion Passed: Empty slots removed (unless inside a sub-array that is not flattened).');


    console.log('\n--- All Array.prototype.flat() demonstrations complete. ---');
}

try {
    runArrayFlatDemo();
} catch (error) {
    console.error('\n!!! ES2019 Array.prototype.flat() Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}