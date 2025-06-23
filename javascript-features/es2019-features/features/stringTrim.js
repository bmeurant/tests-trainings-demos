// es2019-features/features/stringTrim.js
const assert = require('assert');

function runStringTrimDemo() {
    console.log('--- ES2019: String.prototype.trimStart()/trimEnd() ---');

    const originalString = '   Hello World!   \n';
    console.log(`Original string: "${originalString}" (length: ${originalString.length})`);
    console.log('Note: Spaces include leading/trailing spaces and newline characters.');

    // --- 1. trimStart() ---
    console.log('\n--- Using trimStart() ---');
    const trimmedStart = originalString.trimStart();
    console.log(`Trimmed start: "${trimmedStart}" (length: ${trimmedStart.length})`);
    assert.strictEqual(trimmedStart, 'Hello World!   \n', 'Assertion Failed: trimStart failed');
    console.log('Assertion Passed: Leading whitespace removed correctly.');

    // --- 2. trimEnd() ---
    console.log('\n--- Using trimEnd() ---');
    const trimmedEnd = originalString.trimEnd();
    console.log(`Trimmed end: "${trimmedEnd}" (length: ${trimmedEnd.length})`);
    assert.strictEqual(trimmedEnd, '   Hello World!', 'Assertion Failed: trimEnd failed');
    console.log('Assertion Passed: Trailing whitespace removed correctly.');

    // --- 3. Combined usage ---
    console.log('\n--- Combined usage ---');
    const combinedTrim = originalString.trimStart().trimEnd();
    console.log(`Trimmed start then end: "${combinedTrim}" (length: ${combinedTrim.length})`);
    assert.strictEqual(combinedTrim, 'Hello World!', 'Assertion Failed: Combined trim failed');
    console.log('Assertion Passed: Both leading and trailing whitespace removed.');

    console.log('\n--- All String.prototype.trimStart()/trimEnd() demonstrations complete. ---');
}

try {
    runStringTrimDemo();
} catch (error) {
    console.error('\n!!! ES2019 String.prototype.trimStart()/trimEnd() Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}