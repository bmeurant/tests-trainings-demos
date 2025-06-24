// es2024-features/features/toSortedStable.js
const assert = require('assert');

function runToSortedStableDemo() {
    console.log('--- ES2024: Array.prototype.toSorted() is now guaranteed stable ---');

    const items = [
        { name: 'apple', value: 10, order: 1 },
        { name: 'banana', value: 20, order: 2 },
        { name: 'cherry', value: 10, order: 3 }, // Same value as apple, but later order
        { name: 'elderberry', value: 20, order: 5 } // Same value as banana, but later order
    ];

    console.log('\nOriginal items (note "order" for stability check):');
    items.forEach(item => console.log(`  { name: '${item.name}', value: ${item.value}, order: ${item.order} }`));

    const sortedItems = items.toSorted((a, b) => a.value - b.value);

    console.log('\nItems after toSorted() by "value" (should be stable):');
    sortedItems.forEach(item => console.log(`  { name: '${item.name}', value: ${item.value}, order: ${item.order} }`));

    // Expected stable order: apple (order 1) before cherry (order 3) for value 10
    const expectedStableOrder = [
        { name: 'apple', value: 10, order: 1 },
        { name: 'cherry', value: 10, order: 3 },
        { name: 'banana', value: 20, order: 2 },
        { name: 'elderberry', value: 20, order: 5 }
    ];

    assert.deepStrictEqual(sortedItems, expectedStableOrder, 'Assertion Failed: toSorted() is not stable or result incorrect.');
    console.log('Assertion Passed: toSorted() is stable as expected in ES2024.');

    console.log('\n--- All Array.prototype.toSorted() stability demonstrations complete. ---');
}

try {
    runToSortedStableDemo();
} catch (error) {
    console.error('\n!!! ES2024 Array.prototype.toSorted() Stability Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}