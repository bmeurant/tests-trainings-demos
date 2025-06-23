// es2019-features/features/jsonWellFormed.js
const assert = require('assert');

function runJsonWellFormedDemo() {
    console.log('--- ES2019: Well-formed JSON Stringify ---');

    // --- 1. Demonstrating with lone surrogates ---
    console.log('\n--- Handling Lone Surrogates in JSON.stringify() ---');

    // A lone high surrogate code point
    const loneHighSurrogate = '\ud800'; // U+d800 is a high-surrogate code point
    const stringWithLoneHigh = `Hello ${loneHighSurrogate} World`;
    console.log(`String with lone high surrogate: "${stringWithLoneHigh}"`);

    // Before ES2019, this might produce malformed UTF-8 byte sequences.
    // Now, it will be properly escaped.
    const jsonOutputHigh = JSON.stringify(stringWithLoneHigh);
    console.log('Actual output:', jsonOutputHigh);
    console.log('Char codes:', [...jsonOutputHigh].map(c => c.charCodeAt(0).toString(16)));
    console.log(`JSON.stringify output (high): "${jsonOutputHigh}"`);
    assert.strictEqual(jsonOutputHigh, '"Hello \\ud800 World"', 'Assertion Failed: Lone high surrogate not escaped correctly');
    console.log('Assertion Passed: Lone high surrogate correctly escaped.');

    // A lone low surrogate code point
    const loneLowSurrogate = '\udc00'; // U+DC00 is a low-surrogate code point
    const stringWithLoneLow = `Hello ${loneLowSurrogate} World`;
    console.log(`String with lone low surrogate: "${stringWithLoneLow}"`);

    const jsonOutputLow = JSON.stringify(stringWithLoneLow);
    console.log(`JSON.stringify output (low): "${jsonOutputLow}"`);
    assert.strictEqual(jsonOutputLow, '"Hello \\udc00 World"', 'Assertion Failed: Lone low surrogate not escaped correctly');
    console.log('Assertion Passed: Lone low surrogate correctly escaped.');

    // --- 2. Demonstrating with valid surrogate pairs (should remain unchanged) ---
    console.log('\n--- Handling Valid Surrogate Pairs (should remain as characters) ---');
    // U+1F600 GRINNING FACE (emoji) which is a surrogate pair: \uD83D\uDE00
    const emojiChar = '😀';
    const stringWithEmoji = `Smile: ${emojiChar}`;
    console.log(`String with emoji (valid surrogate pair): "${stringWithEmoji}"`);

    const jsonOutputEmoji = JSON.stringify(stringWithEmoji);
    console.log(`JSON.stringify output (emoji): "${jsonOutputEmoji}"`);
    // The emoji character itself should NOT be escaped, as it's a valid pair.
    assert.strictEqual(jsonOutputEmoji, '"Smile: 😀"', 'Assertion Failed: Valid surrogate pair (emoji) was incorrectly escaped');
    console.log('Assertion Passed: Valid emoji (surrogate pair) remains unescaped.');

    console.log('\n--- All Well-formed JSON Stringify demonstrations complete. ---');
}

try {
    runJsonWellFormedDemo();
} catch (error) {
    console.error('\n!!! ES2019 Well-formed JSON Stringify Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}