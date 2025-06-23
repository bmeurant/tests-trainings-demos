// es2020-features/features/stringMatchAll.js
const assert = require('assert');

function runStringMatchAllDemo() {
    console.log('--- ES2020: String.prototype.matchAll() ---');

    const text = `
    Product ID: A123, Price: $10.50
    Product ID: B456, Price: $25.00
    No product here.
    Product ID: C789, Price: $5.75
  `;
    console.log('Original Text:\n', text);

    // Regex to find product ID and price, with named capture groups
    // The 'g' flag is crucial for matchAll()
    const productRegex = /Product ID: (?<id>[A-Z]\d{3}), Price: \$(?<price>\d+\.\d{2})/g;

    // --- 1. Using matchAll() ---
    console.log('\n--- Using matchAll() to get all matches with groups ---');
    const matchesIterator = text.matchAll(productRegex);

    const allProducts = [];
    for (const match of matchesIterator) {
        console.log(`  Found match: "${match[0]}", Index: ${match.index}`);
        console.log(`    Groups: ID=${match.groups.id}, Price=${match.groups.price}`);
        allProducts.push({ ...match.groups }); // Convert to plain object
    }

    const expectedProducts = [
        { id: 'A123', price: '10.50' },
        { id: 'B456', price: '25.00' },
        { id: 'C789', price: '5.75' }
    ];
    assert.deepStrictEqual(allProducts, expectedProducts, 'Assertion Failed: matchAll did not extract all products correctly');
    console.log('Assertion Passed: matchAll extracted all products and their named groups.');

    // --- 2. Comparison with String.prototype.match(regex_with_g_flag) ---
    console.log('\n--- Comparison with String.prototype.match(regex_with_g_flag) ---');
    // When regex has 'g' flag, .match() returns only an array of full matches
    const simpleMatches = text.match(productRegex);
    console.log('`text.match(productRegex)` (with global flag):', simpleMatches);
    // Notice: no group info, no index info
    assert.deepStrictEqual(simpleMatches, [
        'Product ID: A123, Price: $10.50',
        'Product ID: B456, Price: $25.00',
        'Product ID: C789, Price: $5.75'
    ], 'Assertion Failed: match with global flag incorrect');
    console.log('Assertion Passed: .match() with global flag returns only full matches.');

    // --- 3. Comparison with RegExp.prototype.exec() in a loop ---
    console.log('\n--- Comparison with RegExp.prototype.exec() in a loop (old way) ---');
    // Reset the lastIndex of the regex for exec() to work from the beginning
    productRegex.lastIndex = 0;
    let execMatch;
    const oldWayProducts = [];
    while ((execMatch = productRegex.exec(text)) !== null) {
        console.log(`  Old way match: "${execMatch[0]}", Index: ${execMatch.index}`);
        console.log(`    Groups: ID=<${execMatch.groups.id}, Price=${execMatch.groups.price}`);
        oldWayProducts.push({ ...execMatch.groups });
    }
    assert.deepStrictEqual(oldWayProducts, expectedProducts, 'Assertion Failed: Old way (exec in loop) extraction incorrect');
    console.log('Assertion Passed: Old way (exec in loop) works, but matchAll is more convenient.');

    console.log('\n--- All String.prototype.matchAll() demonstrations complete. ---');
}

try {
    runStringMatchAllDemo();
} catch (error) {
    console.error('\n!!! ES2020 String.prototype.matchAll() Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}