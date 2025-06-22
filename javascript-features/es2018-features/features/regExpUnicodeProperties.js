// es2018/features/regExpUnicodeProperties.js
const assert = require('assert');

function runRegExpUnicodePropertiesDemo() {
    console.log('--- ES2018: RegExp Unicode Property Escapes ---');

    // --- 1. Matching characters by Unicode Script property ---
    console.log('\n--- Matching by Unicode Script (e.g., Greek, Cyrillic) ---');
    const text1 = 'Hello World! Γεια σας Κόσμε! Привет мир!'; // English, Greek, Russian
    console.log('Original text:', text1);

    // Match Greek characters
    // Requires 'u' flag for Unicode support
    const greekRegex = /\p{Script=Greek}/gu; // 'g' for global, 'u' for unicode properties
    const greekLetters = [...text1.matchAll(greekRegex)].map(m => m[0]);
    console.log('Greek characters found:', greekLetters);
    assert.deepStrictEqual(greekLetters, ['Γ', 'ε', 'ι', 'α', 'σ', 'α', 'ς', 'Κ', 'ό', 'σ', 'μ', 'ε'], 'Assertion Failed: Greek characters not matched correctly');
    console.log('Assertion Passed: Correctly matched Greek characters.');

    // Match Cyrillic characters
    const cyrillicRegex = /\p{Script=Cyrillic}/gu;
    const cyrillicLetters = [...text1.matchAll(cyrillicRegex)].map(m => m[0]);
    console.log('Cyrillic characters found:', cyrillicLetters);
    assert.deepStrictEqual(cyrillicLetters, ['П', 'р', 'и', 'в', 'е', 'т', 'м', 'и', 'р'], 'Assertion Failed: Cyrillic characters not matched correctly');
    console.log('Assertion Passed: Correctly matched Cyrillic characters.');

    // --- 2. Matching Emojis by Unicode General_Category property ---
    console.log('\n--- Matching Emojis by Unicode General_Category or Emoji property ---');
    const text2 = 'Hello 👋! I like pizza 🍕 and cats 🐱. What about you? 🤔';
    console.log('Original text:', text2);

    // Match Emojis (using general category)
    // Note: \p{Emoji} is also common and more direct for emojis.
    const emojiRegex = /\p{Emoji}/gu;
    const emojis = [...text2.matchAll(emojiRegex)].map(m => m[0]);
    console.log('Emojis found:', emojis);
    assert.deepStrictEqual(emojis, ['👋', '🍕', '🐱', '🤔'], 'Assertion Failed: Emojis not matched correctly');
    console.log('Assertion Passed: Correctly matched Emojis.');

    // --- 3. Negated properties \P{...} ---
    console.log('\n--- Negated properties: \\P{...} ---');
    const text3 = '123 ABC αβγ 🐱';
    console.log('Original text:', text3);

    // Match anything that is NOT a digit
    const nonDigitRegex = /\P{Nd}/gu; // General_Category: Nd (Number, Decimal Digit)
    const nonDigits = [...text3.matchAll(nonDigitRegex)].map(m => m[0]);
    console.log('Non-digits found:', nonDigits);
    assert.deepStrictEqual(nonDigits, [' ', 'A', 'B', 'C', ' ', 'α', 'β', 'γ', ' ', '🐱'], 'Assertion Failed: Non-digits not matched correctly');
    console.log('Assertion Passed: Correctly matched non-digits.');

    console.log('\n--- All RegExp Unicode Property Escapes demonstrations complete. ---');
}

try {
    runRegExpUnicodePropertiesDemo();
} catch (error) {
    console.error('\n!!! ES2018 RegExp Unicode Property Escapes Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1); // Exit with a non-zero code to indicate failure
}