// es2025-features/features/setOperations.js
const assert = require('assert');

function runSetOperationsDemo() {
    console.log('--- ES2024: Set Operations (union, intersection, difference, symmetricDifference) ---');

    const setA = new Set([1, 2, 3, 4]);
    const setB = new Set([3, 4, 5, 6]);
    const setC = new Set([1, 2]);

    console.log(`\nSet A: [${Array.from(setA)}]`);
    console.log(`Set B: [${Array.from(setB)}]`);
    console.log(`Set C: [${Array.from(setC)}]`);

    // --- 1. union() ---
    console.log('\n--- 1. union() ---');
    const unionAB = setA.union(setB);
    console.log(`  A union B: [${Array.from(unionAB)}]`);
    assert.deepStrictEqual(Array.from(unionAB).sort(), [1, 2, 3, 4, 5, 6].sort(), 'Assertion Failed: unionAB incorrect.');
    console.log('Assertion Passed: union() works.');

    // --- 2. intersection() ---
    console.log('\n--- 2. intersection() ---');
    const intersectionAB = setA.intersection(setB);
    console.log(`  A intersection B: [${Array.from(intersectionAB)}]`);
    assert.deepStrictEqual(Array.from(intersectionAB).sort(), [3, 4].sort(), 'Assertion Failed: intersectionAB incorrect.');
    console.log('Assertion Passed: intersection() works.');

    // --- 3. difference() ---
    console.log('\n--- 3. difference() ---');
    const differenceAB = setA.difference(setB);
    console.log(`  A difference B: [${Array.from(differenceAB)}]`);
    assert.deepStrictEqual(Array.from(differenceAB).sort(), [1, 2].sort(), 'Assertion Failed: differenceAB incorrect.');

    const differenceBA = setB.difference(setA);
    console.log(`  B difference A: [${Array.from(differenceBA)}]`);
    assert.deepStrictEqual(Array.from(differenceBA).sort(), [5, 6].sort(), 'Assertion Failed: differenceBA incorrect.');
    console.log('Assertion Passed: difference() works.');

    // --- 4. symmetricDifference() ---
    console.log('\n--- 4. symmetricDifference() ---');
    const symmetricDifferenceAB = setA.symmetricDifference(setB);
    console.log(`  A symmetricDifference B: [${Array.from(symmetricDifferenceAB)}]`);
    assert.deepStrictEqual(Array.from(symmetricDifferenceAB).sort(), [1, 2, 5, 6].sort(), 'Assertion Failed: symmetricDifferenceAB incorrect.');
    console.log('Assertion Passed: symmetricDifference() works.');

    console.log('\n--- All Set Operations demonstrations complete. ---');
}

try {
    runSetOperationsDemo();
} catch (error) {
    console.error('\n!!! ES2024 Set Operations Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}