// es2025-features/features/setRelations.js
const assert = require('assert');

function runSetRelationsDemo() {
    console.log('--- ES2024: Set Relations (isSubsetOf, isSupersetOf, isDisjointFrom) ---');

    const setA = new Set([1, 2, 3, 4, 5]);
    const setB = new Set([2, 3]);
    const setC = new Set([6, 7]);
    const setD = new Set([4, 5, 6]);

    console.log(`\nSet A: [${Array.from(setA)}]`);
    console.log(`Set B: [${Array.from(setB)}]`);
    console.log(`Set C: [${Array.from(setC)}]`);
    console.log(`Set D: [${Array.from(setD)}]`);

    // isSubsetOf()
    console.log('\n--- 1. isSubsetOf() ---');
    console.log(`  Is B a subset of A? ${setB.isSubsetOf(setA)}`); // Expected: true
    assert.strictEqual(setB.isSubsetOf(setA), true, 'Assertion Failed: B should be subset of A.');

    console.log(`  Is A a subset of B? ${setA.isSubsetOf(setB)}`); // Expected: false
    assert.strictEqual(setA.isSubsetOf(setB), false, 'Assertion Failed: A should not be subset of B.');

    console.log(`  Is B a subset of B? ${setB.isSubsetOf(setB)}`); // Expected: true (reflexive)
    assert.strictEqual(setB.isSubsetOf(setB), true, 'Assertion Failed: B should be subset of itself.');
    console.log('Assertion Passed: isSubsetOf() works correctly.');

    // isSupersetOf()
    console.log('\n--- 2. isSupersetOf() ---');
    console.log(`  Is A a superset of B? ${setA.isSupersetOf(setB)}`); // Expected: true
    assert.strictEqual(setA.isSupersetOf(setB), true, 'Assertion Failed: A should be superset of B.');

    console.log(`  Is B a superset of A? ${setB.isSupersetOf(setA)}`); // Expected: false
    assert.strictEqual(setB.isSupersetOf(setA), false, 'Assertion Failed: B should not be superset of A.');
    console.log('Assertion Passed: isSupersetOf() works correctly.');

    // isDisjointFrom()
    console.log('\n--- 3. isDisjointFrom() ---');
    console.log(`  Are A and C disjoint? ${setA.isDisjointFrom(setC)}`); // Expected: true (no common elements)
    assert.strictEqual(setA.isDisjointFrom(setC), true, 'Assertion Failed: A and C should be disjoint.');

    console.log(`  Are A and D disjoint? ${setA.isDisjointFrom(setD)}`); // Expected: false (common: 4, 5)
    assert.strictEqual(setA.isDisjointFrom(setD), false, 'Assertion Failed: A and D should not be disjoint.');
    console.log('Assertion Passed: isDisjointFrom() works correctly.');

    console.log('\n--- All Set Relations demonstrations complete. ---');
}

try {
    runSetRelationsDemo();
} catch (error) {
    console.error('\n!!! ES2024 Set Relations Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}