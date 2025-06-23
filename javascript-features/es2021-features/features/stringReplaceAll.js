// es2021-features/features/stringReplaceAll.js
const assert = require('assert');

function runStringReplaceAllDemo() {
    console.log('--- ES2021: String.prototype.replaceAll() ---');

    const originalText = 'The quick brown fox jumps over the lazy fox.';
    console.log(`Original Text: "${originalText}"`);

    // --- 1. Basic usage: Replace all occurrences of a string ---
    console.log('\n--- Basic Usage: Replace all "fox" with "dog" ---');
    const newText1 = originalText.replaceAll('fox', 'dog');
    console.log(`Replaced Text: "${newText1}"`);
    assert.strictEqual(newText1, 'The quick brown dog jumps over the lazy dog.', 'Assertion Failed: replaceAll did not replace all occurrences of string.');
    console.log('Assertion Passed: replaceAll correctly replaced all occurrences of a string.');

    // --- 2. Replacement with a replacement string (using capture groups with $) ---
    console.log('\n--- Replacement with replacement string using capture groups ---');
    const tags = '<h1>Title</h1> <p>Paragraph</p>';
    const replacedTags = tags.replaceAll(/<(\/?)([a-z0-9]+)>/g, '<$1div>');
    console.log(`Original Tags: "${tags}"`);
    console.log(`Replaced Tags: "${replacedTags}"`);
    assert.strictEqual(replacedTags, '<div>Title</div> <div>Paragraph</div>', 'Assertion Failed: replaceAll with regex and replacement string failed.');
    console.log('Assertion Passed: replaceAll with regex and replacement string works.');

    // --- 3. Using a replacer function ---
    console.log('\n--- Using a replacer function ---');
    const numbers = 'Number 1, Number 2, Number 3';
    const doubledNumbers = numbers.replaceAll(/Number (\d)/g, (match, num) => {
        const doubled = parseInt(num) * 2;
        return `Value ${doubled}`;
    });
    console.log(`Original Numbers: "${numbers}"`);
    console.log(`Doubled Numbers: "${doubledNumbers}"`);
    assert.strictEqual(doubledNumbers, 'Value 2, Value 4, Value 6', 'Assertion Failed: replaceAll with replacer function failed.');
    console.log('Assertion Passed: replaceAll with replacer function works.');

    // --- 4. Comparison with String.prototype.replace() ---
    console.log('\n--- Comparison with String.prototype.replace() ---');
    const singleReplace = originalText.replace('fox', 'dog');
    console.log(`Original Text with .replace('fox', 'dog'): "${singleReplace}"`);
    assert.strictEqual(singleReplace, 'The quick brown dog jumps over the lazy fox.', 'Assertion Failed: replace did not replace only first occurrence.');
    console.log('Assertion Passed: .replace() only replaces the first occurrence by default.');

    const regexGlobalReplace = originalText.replace(/fox/g, 'dog');
    console.log(`Original Text with .replace(/fox/g, 'dog'): "${regexGlobalReplace}"`);
    assert.strictEqual(regexGlobalReplace, 'The quick brown dog jumps over the lazy dog.', 'Assertion Failed: replace with global regex failed.');
    console.log('Assertion Passed: .replace() with global regex works, but replaceAll is more convenient for strings.');

    console.log('\n--- All String.prototype.replaceAll() demonstrations complete. ---');
}

try {
    runStringReplaceAllDemo();
} catch (error) {
    console.error('\n!!! ES2021 String.prototype.replaceAll() Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}