// es2024/features/wellFormedUnicode.js
const assert = require('assert');

function runWellFormedUnicodeDemo() {
    console.log('--- ES2024: Well-Formed Unicode Strings (toWellFormed(), isWellFormed()) ---');

    const wellFormedEmoji = 'Hello 🪆 World'; // Well-formed surrogate pair
    const malFormedString = 'Hello \uD83E World'; // Malformed: missing trailing surrogate

    console.log(`\nOriginal strings:`);
    console.log(`  Well-formed emoji: "${wellFormedEmoji}"`);
    console.log(`  Malformed string: "${malFormedString}" (missing trailing surrogate)`);

    // --- 1. isWellFormed() ---
    console.log('\n--- 1. isWellFormed() ---');
    console.log(`  "${wellFormedEmoji}".isWellFormed(): ${wellFormedEmoji.isWellFormed()}`);
    assert.strictEqual(wellFormedEmoji.isWellFormed(), true, 'Assertion Failed: wellFormedEmoji should be well-formed.');

    console.log(`  "${malFormedString}".isWellFormed(): ${malFormedString.isWellFormed()}`);
    assert.strictEqual(malFormedString.isWellFormed(), false, 'Assertion Failed: malFormedString should NOT be well-formed.');
    console.log('Assertion Passed: isWellFormed() correctly identifies strings.');

    // --- 2. toWellFormed() ---
    console.log('\n--- 2. toWellFormed() ---');
    const fixedMalformed = malFormedString.toWellFormed();
    console.log(`  "${malFormedString}" -> "${fixedMalformed}"`);
    assert.strictEqual(fixedMalformed, 'Hello � World', 'Assertion Failed: malFormedString fixed incorrectly.');
    assert.strictEqual(fixedMalformed.isWellFormed(), true, 'Assertion Failed: Fixed string is not well-formed.');
    console.log('Assertion Passed: toWellFormed() correctly fixes malformed surrogates.');

    console.log('\n--- All Well-Formed Unicode Strings demonstrations complete. ---');
}

try {
    runWellFormedUnicodeDemo();
} catch (error) {
    console.error('\n!!! ES2024 Well-Formed Unicode Strings Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}