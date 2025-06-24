// es2024-features/features/objectMapGroupBy.js
const assert = require('assert');

function runGroupByDemo() {
    console.log('--- ES2024: Object.groupBy() and Map.groupBy() ---');

    const products = [
        { name: 'Laptop', category: 'Electronics', price: 1200 },
        { name: 'Mouse', category: 'Electronics', price: 25 },
        { name: 'Book', category: 'Books', price: 200 }, // Changed price to fit 'Medium' category
        { name: 'Pen', category: 'Stationery', price: 2 },
        { name: 'Smartphone', category: 'Electronics', price: 800 },
        { name: 'Tablet', category: 'Electronics', price: 450 } // Added a product to fit 'Medium' category
    ];

    console.log('\nOriginal Products Data:');
    products.forEach(p => console.log(`  - ${p.name} (Category: ${p.category}, Price: ${p.price})`));

    // --- 1. Object.groupBy() ---
    console.log('\n--- 1. Using Object.groupBy() to group by category ---');
    const productsByCategoryObject = Object.groupBy(products, product => product.category);
    console.log('  Grouped by Category (Object):', productsByCategoryObject);

    assert.ok(typeof productsByCategoryObject === 'object' && productsByCategoryObject !== null && !Array.isArray(productsByCategoryObject), 'Assertion Failed: Result should be a plain object.');
    assert.strictEqual(Object.keys(productsByCategoryObject).length, 3, 'Assertion Failed: Incorrect number of categories.');
    assert.strictEqual(productsByCategoryObject['Electronics'].length, 4, 'Assertion Failed: "Electronics" group size incorrect.');
    assert.strictEqual(productsByCategoryObject['Books'][0].name, 'Book', 'Assertion Failed: "Books" group content incorrect.');
    console.log('Assertion Passed: Object.groupBy() works correctly.');

    // --- 2. Map.groupBy() ---
    console.log('\n--- 2. Using Map.groupBy() to group by price range ---');
    // Example: group by price range (cheap, medium, expensive)
    const productsByPriceRangeMap = Map.groupBy(products, product => {
        if (product.price < 50) {
            return 'Cheap';
        } else if (product.price < 500) { // Price between 50 and 499
            return 'Medium';
        } else { // Price 500 or more
            return 'Expensive';
        }
    });
    console.log('  Grouped by Price Range (Map):', productsByPriceRangeMap);

    assert.ok(productsByPriceRangeMap instanceof Map, 'Assertion Failed: Result should be a Map.');
    // Now, with modified data:
    // Cheap: Mouse (25), Pen (2) => 2 items
    // Medium: Book (200), Tablet (450) => 2 items
    // Expensive: Laptop (1200), Smartphone (800) => 2 items
    assert.strictEqual(productsByPriceRangeMap.size, 3, 'Assertion Failed: Incorrect number of price ranges.');
    assert.strictEqual(productsByPriceRangeMap.get('Cheap').length, 2, 'Assertion Failed: "Cheap" group size incorrect.');
    assert.strictEqual(productsByPriceRangeMap.get('Medium').length, 2, 'Assertion Failed: "Medium" group size incorrect.');
    assert.strictEqual(productsByPriceRangeMap.get('Expensive').length, 2, 'Assertion Failed: "Expensive" group size incorrect.');
    console.log('Assertion Passed: Map.groupBy() works correctly with updated data.');

    console.log('\n--- All groupBy demonstrations complete. ---');
}

try {
    runGroupByDemo();
} catch (error) {
    console.error('\n!!! ES2024 groupBy Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}