// es2018-features/features/regExpLookbehind.js
const assert = require('assert');

function runRegExpLookbehindDemo() {
    console.log('--- ES2018: RegExp Lookbehind Assertions ---');

    // --- 1. Positive Lookbehind (?<=pattern) ---
    console.log('\n--- Positive Lookbehind: (?<=pattern) ---');
    const text1 = 'Prices: $10.50, $20.00, ¥500, but not 30 USD.';
    console.log('Original text:', text1);

    // Match numbers preceded by a dollar sign, but don't include the '$' in the match itself
    // Using global flag and matchAll to find all occurrences
    const dollarPriceRegex = /(?<=\$)(\d+\.\d{2})/g; // Matches XX.YY that comes after '$'
    const matches1 = [...text1.matchAll(dollarPriceRegex)].map(m => m[0]);
    console.log('Finding dollar prices:', matches1);
    assert.deepStrictEqual(matches1, ['10.50', '20.00'], 'Assertion Failed: Dollar prices incorrect');
    console.log('Assertion Passed: Prices correctly matched using positive lookbehind.');

    const text2 = 'Just 10.50 without a currency symbol.';
    console.log('\nOriginal text:', text2);
    const matches2 = [...text2.matchAll(dollarPriceRegex)].map(m => m[0]);
    console.log('Finding dollar prices (should find none):', matches2);
    assert.deepStrictEqual(matches2, [], 'Assertion Failed: Should not find price without dollar sign');
    console.log('Assertion Passed: No match found without dollar sign.');


    // --- 2. Negative Lookbehind (?<!pattern) ---
    console.log('\n--- Negative Lookbehind: (?<!pattern) ---');
    const text3 = 'I like apple pie. My favorite fruit is apple. Apple trees are common.';
    console.log('Original text:', text3);

    // Match 'apple' or 'Apple' only if it's NOT preceded by 'My favorite fruit is '
    const appleRegex = /(?<!My favorite fruit is )[aA]pple/g;
    const matches3 = [...text3.matchAll(appleRegex)].map(m => m[0]);
    console.log('Finding "apple" not preceded by "My favorite fruit is ":', matches3);
    assert.deepStrictEqual(matches3, ['apple', 'Apple'], 'Assertion Failed: Negative lookbehind incorrect'); // The first and third 'apple'
    console.log('Assertion Passed: "apple" correctly matched based on negative lookbehind.');

    const text4 = 'Only My favorite fruit is apple.';
    console.log('\nOriginal text:', text4);
    const matches4 = [...text4.matchAll(appleRegex)].map(m => m[0]);
    console.log('Finding "apple" not preceded by "My favorite fruit is " (should find none):', matches4);
    assert.deepStrictEqual(matches4, [], 'Assertion Failed: Should not find "apple" preceded by "My favorite fruit is "');
    console.log('Assertion Passed: No "apple" found when preceded by the specified phrase.');

    console.log('\n--- All RegExp Lookbehind Assertions demonstrations complete. ---');
}

try {
    runRegExpLookbehindDemo();
} catch (error) {
    console.error('\n!!! ES2018 RegExp Lookbehind Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1); // Exit with a non-zero code to indicate failure
}