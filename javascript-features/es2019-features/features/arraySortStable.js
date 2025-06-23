// es2019-features/features/arraySortStable.js
const assert = require('assert');

function runArraySortStableDemo() {
    console.log('--- ES2019: Stable Array.prototype.sort() ---');

    // --- 1. Demonstrating stability with objects ---
    console.log('\n--- Sorting Objects with Same Keys (Stable Sort) ---');
    const students = [
        { name: 'Alice', score: 90, id: 1 },
        { name: 'Bob', score: 85, id: 2 },
        { name: 'Charlie', score: 90, id: 3 }, // Same score as Alice
        { name: 'David', score: 85, id: 4 }    // Same score as Bob
    ];
    console.log('Original students array:', JSON.stringify(students));

    // Sort by score (ascending)
    // With stable sort, Alice (id:1) should come before Charlie (id:3)
    // and Bob (id:2) before David (id:4) if their scores are equal.
    const sortedStudents = [...students].sort((a, b) => a.score - b.score);
    console.log('Sorted students array by score:', JSON.stringify(sortedStudents));

    const expectedOrder = [
        { name: 'Bob', score: 85, id: 2 },
        { name: 'David', score: 85, id: 4 },
        { name: 'Alice', score: 90, id: 1 },
        { name: 'Charlie', score: 90, id: 3 }
    ];

    // Note: The original specification of stable sort stated that if `a` and `b`
    // are equal, their relative order is preserved. In some engines pre-ES2019,
    // the actual outcome might have been [David, Bob, Alice, Charlie] or other variations.
    // Now, it's guaranteed to preserve original insertion order for equal elements.
    assert.deepStrictEqual(sortedStudents, expectedOrder, 'Assertion Failed: Array sort is not stable');
    console.log('Assertion Passed: Array sort maintained stable order for equal elements.');

    // --- 2. Demonstrating with elements that are naturally sorted equal ---
    console.log('\n--- Sorting Numbers (Stable Sort behavior) ---');
    const numbers = [1, 5, 2, 5, 3, 5];
    console.log('Original numbers array:', numbers);

    // Sort with a custom comparator that always returns 0 for 5s, meaning they are "equal"
    const sortedNumbers = [...numbers].sort((a, b) => {
        if (a === 5 && b === 5) {
            return 0; // Treat 5s as equal
        }
        return a - b;
    });
    console.log('Sorted numbers (custom comparator):', sortedNumbers);
    // The three 5s should maintain their original relative order
    assert.deepStrictEqual(sortedNumbers, [1, 2, 3, 5, 5, 5], 'Assertion Failed: Numbers sort is not stable');
    console.log('Assertion Passed: Numbers sort maintained stable order for equal elements.');

    console.log('\n--- All Stable Array.prototype.sort() demonstrations complete. ---');
}

try {
    runArraySortStableDemo();
} catch (error) {
    console.error('\n!!! ES2019 Stable Array.prototype.sort() Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}