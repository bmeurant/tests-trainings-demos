// es2019-features/features/arrayFlatMap.js
const assert = require('assert');

function runArrayFlatMapDemo() {
    console.log('--- ES2019: Array.prototype.flatMap() ---');

    // --- 1. Basic usage: transforming and flattening ---
    console.log('\n--- Basic usage: transforming and flattening ---');
    const words = ['hello', 'world', 'JavaScript'];
    console.log('Original Array:', words);

    // Split each word into individual characters
    const characters = words.flatMap(word => word.split(''));
    console.log('Characters from words (flatMap):', characters);
    assert.deepStrictEqual(characters, ['h', 'e', 'l', 'l', 'o', 'w', 'o', 'r', 'l', 'd', 'J', 'a', 'v', 'a', 'S', 'c', 'r', 'i', 'p', 't'], 'Assertion Failed: flatMap for characters incorrect');
    console.log('Assertion Passed: Successfully flattened characters.');

    // For comparison, equivalent with map then flat(1)
    const charactersMapFlat = words.map(word => word.split('')).flat(1);
    assert.deepStrictEqual(characters, charactersMapFlat, 'Assertion Failed: flatMap not equivalent to map().flat(1)');
    console.log('Assertion Passed: flatMap is equivalent to map().flat(1).');

    // --- 2. Filtering and flattening (e.g., removing empty arrays) ---
    console.log('\n--- Filtering and flattening ---');
    const sentences = ['This is a sentence.', '', 'Another one.'];
    console.log('Original Array:', sentences);

    const cleanedWords = sentences.flatMap(sentence =>
        sentence.split(' ').filter(word => word !== '') // Split and remove empty strings
    );
    console.log('Cleaned words:', cleanedWords);
    assert.deepStrictEqual(cleanedWords, ['This', 'is', 'a', 'sentence.', 'Another', 'one.'], 'Assertion Failed: flatMap for cleaning words incorrect');
    console.log('Assertion Passed: Successfully filtered and flattened words.');

    // --- 3. Returning single elements or multiple elements ---
    console.log('\n--- Returning single or multiple elements ---');
    const numbers = [1, 2, 3];
    console.log('Original Array:', numbers);

    // Return each number and its double
    const doubledAndOriginal = numbers.flatMap(num => [num, num * 2]);
    console.log('Original and doubled numbers:', doubledAndOriginal);
    assert.deepStrictEqual(doubledAndOriginal, [1, 2, 2, 4, 3, 6], 'Assertion Failed: flatMap for doubling incorrect');
    console.log('Assertion Passed: Successfully returned multiple elements per input.');

    // Return only even numbers, otherwise nothing
    const evenNumbersOnly = numbers.flatMap(num => (num % 2 === 0 ? [num] : []));
    console.log('Even numbers only:', evenNumbersOnly);
    assert.deepStrictEqual(evenNumbersOnly, [2], 'Assertion Failed: flatMap for even numbers incorrect');
    console.log('Assertion Passed: Successfully filtered with flatMap.');

    console.log('\n--- All Array.prototype.flatMap() demonstrations complete. ---');
}

try {
    runArrayFlatMapDemo();
} catch (error) {
    console.error('\n!!! ES2019 Array.prototype.flatMap() Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}