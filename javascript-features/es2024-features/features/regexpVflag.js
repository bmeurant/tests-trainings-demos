// es2024-features/features/regExpVFlagS.js
const assert = require('assert');

function runRegExpVFlagDemo() {
    console.log('--- ES2024: RegExp /v flag (Unicode Sets V Mode) ---');

    // The /v flag provides advanced capabilities for managing Unicode character sets.

    // --- 1. Intersection (&&) of Unicode Sets ---
    console.log('\n--- 1. Intersection (&&) of Unicode Sets ---');

    // Example 1: Find characters that are both a Unicode Number AND a Hexadecimal Digit.
    // Hexadecimal digits include '0'-'9', 'A'-'F', 'a'-'f'. \p{N} is for "Number", \p{Hex_Digit} for "Hexadecimal Digit".
    // The intersection of \p{N} and \p{Hex_Digit} should match only digits '0'-'9'.
    const regexNumberAndHexDigit = /[\p{N}&&\p{Hex_Digit}]/v;
    console.log(`  Searching for '5' (number and hex) with /[\\p{N}&&\\p{Hex_Digit}]]/v: '${'5'.match(regexNumberAndHexDigit)?.[0]}'`);
    assert.strictEqual('5'.match(regexNumberAndHexDigit)?.[0], '5', 'Assertion failed: Should match "5".');
    console.log(`  Searching for 'A' (hex letter, but not a Unicode number) with /[\\p{N}&&\\p{Hex_Digit}]]/v: ${'A'.match(regexNumberAndHexDigit)}`);
    assert.strictEqual('A'.match(regexNumberAndHexDigit), null, 'Assertion failed: Should not match "A".');
    console.log(`  Searching for '⑤' (Unicode number, but not hex) with /[\\p{N}&&\\p{Hex_Digit}]]/v: ${'⑤'.match(regexNumberAndHexDigit)}`);
    assert.strictEqual('⑤'.match(regexNumberAndHexDigit), null, 'Assertion failed: Should not match "⑤".');
    console.log('Assertion passed: Intersection operator works correctly with Unicode properties.');

    // Example 2: Find characters that are Unicode Letters AND belong to the Latin script.
    // This essentially finds Latin letters, demonstrating the syntax for property intersection.
    const regexLetterInLatinScript = /[\p{L}&&\p{Script=Latin}]/v;
    console.log(`\n  Searching for 'é' (Latin letter) with /[\\p{L}&&\\p{Script=Latin}]]/v: '${'é'.match(regexLetterInLatinScript)?.[0]}'`);
    assert.strictEqual('é'.match(regexLetterInLatinScript)?.[0], 'é', 'Assertion failed: Should match "é".');
    console.log(`  Searching for 'Ж' (Cyrillic letter) with /[\\p{L}&&\\p{Script=Latin}]]/v: ${'Ж'.match(regexLetterInLatinScript)}`);
    assert.strictEqual('Ж'.match(regexLetterInLatinScript), null, 'Assertion failed: Should not match "Ж".');
    console.log('Assertion passed: Intersection of Unicode properties works.');


    // --- 2. Difference (--) of Unicode Sets ---
    console.log('\n--- 2. Difference (--) of Unicode Sets ---');

    // Example: Find all Unicode letters EXCEPT those from the Latin script.
    // This will match letters from scripts like Cyrillic, Bengali, etc.
    const regexNonLatinLetter = /[\p{L}--[\p{Script=Latin}]]/v; // Note the nesting for the second operand
    console.log(`  Searching for 'Д' (Cyrillic letter) with /[\\p{L}--[\\p{Script=Latin}]]/v: '${'Д'.match(regexNonLatinLetter)?.[0]}'`);
    assert.strictEqual('Д'.match(regexNonLatinLetter)?.[0], 'Д', 'Assertion failed: Should match "Д".');
    console.log(`  Searching for 'z' (Latin letter) with /[\\p{L}--[\\p{Script=Latin}]]/v: ${'z'.match(regexNonLatinLetter)}`);
    assert.strictEqual('z'.match(regexNonLatinLetter), null, 'Assertion failed: Should not match "z".');
    console.log('Assertion passed: Difference of Unicode sets with properties works.');


    // --- 3. Nesting and Unions of Unicode Properties ---
    console.log('\n--- 3. Nesting and Unions of Unicode Properties ---');

    // Example: Find a character that is a lowercase Latin letter OR a letter from the Cyrillic script.
    // Nesting allows building more complex unions of sets.
    const regexLatinLowercaseOrCyrillicLetter = /[[a-z][\p{Script=Cyrl}]]/v;
    console.log(`\n  Searching for 'a' (lowercase Latin) with /[[a-z][\\p{Script=Cyrl}]]/v: '${'a'.match(regexLatinLowercaseOrCyrillicLetter)?.[0]}'`);
    assert.strictEqual('a'.match(regexLatinLowercaseOrCyrillicLetter)?.[0], 'a', 'Assertion failed: Should match "a".');
    console.log(`  Searching for 'Я' (Cyrillic letter) with /[[a-z][\\p{Script=Cyrl}]]/v: '${'Я'.match(regexLatinLowercaseOrCyrillicLetter)?.[0]}'`);
    assert.strictEqual('Я'.match(regexLatinLowercaseOrCyrillicLetter)?.[0], 'Я', 'Assertion failed: Should match "Я".');
    console.log(`  Searching for 'Z' (uppercase Latin) with /[[a-z][\\p{Script=Cyrl}]]/v: ${'Z'.match(regexLatinLowercaseOrCyrillicLetter)}`);
    assert.strictEqual('Z'.match(regexLatinLowercaseOrCyrillicLetter), null, 'Assertion failed: Should not match "Z".');
    console.log('Assertion passed: Nesting sets for Unicode property unions works.');


    console.log('\n--- All RegExp /v flag demonstrations complete. ---');
}

try {
    runRegExpVFlagDemo();
} catch (error) {
    console.error('\n!!! ES2024 RegExp /v flag Demo FAILED !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}