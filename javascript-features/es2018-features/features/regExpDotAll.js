// es2018/features/regExpDotAll.js
const assert = require('assert');

function runRegExpDotAllDemo() {
    console.log('--- ES2018: RegExp s (dotAll) Flag ---');

    const multiLineText = `Line 1: Hello
Line 2: World!
Line 3: How are you?`;

    console.log('Original multi-line text:\n', multiLineText);

    // --- 1. Without 's' (dotAll) flag ---
    console.log('\n--- Matching without the "s" (dotAll) flag ---');
    // The dot '.' will NOT match the newline character '\n'
    const regexWithoutDotAll = /Line 1: Hello.*World!/i; // 'i' for case-insensitive
    const matchWithoutDotAll = multiLineText.match(regexWithoutDotAll);

    console.log('Regex: /Line 1: Hello.*World!/i');
    console.log('Match Result (without s flag):', matchWithoutDotAll ? matchWithoutDotAll[0] : 'No match');
    assert.strictEqual(matchWithoutDotAll, null, 'Assertion Failed: Should not match across newline without "s" flag');
    console.log('Assertion Passed: Correctly did not match across newline.');

    // --- 2. With 's' (dotAll) flag ---
    console.log('\n--- Matching with the "s" (dotAll) flag ---');
    // The dot '.' WILL match the newline character '\n'
    const regexWithDotAll = /Line 1: Hello.*World!/is; // 'i' for case-insensitive, 's' for dotAll
    const matchWithDotAll = multiLineText.match(regexWithDotAll);

    console.log('Regex: /Line 1: Hello.*World!/is');
    console.log('Match Result (with s flag):', matchWithDotAll ? matchWithDotAll[0] : 'No match');
    assert.strictEqual(matchWithDotAll ? matchWithDotAll[0] : null, 'Line 1: Hello\nLine 2: World!', 'Assertion Failed: Should match across newline with "s" flag');
    console.log('Assertion Passed: Correctly matched across newline with "s" flag.');

    // --- 3. Example with a single-line text (no difference expected) ---
    console.log('\n--- Matching single-line text (s flag has no effect) ---');
    const singleLineText = 'This is a test. Another part.';
    const singleLineRegexWithDotAll = /This.is.a.test/s;
    const singleLineRegexWithoutDotAll = /This.is.a.test/;

    const singleMatchWithS = singleLineText.match(singleLineRegexWithDotAll);
    const singleMatchWithoutS = singleLineText.match(singleLineRegexWithoutDotAll);

    console.log('Original single-line text:', singleLineText);
    console.log('Match with "s" flag:', singleMatchWithS ? singleMatchWithS[0] : 'No match');
    console.log('Match without "s" flag:', singleMatchWithoutS ? singleMatchWithoutS[0] : 'No match');

    assert.strictEqual(singleMatchWithS ? singleMatchWithS[0] : null, 'This is a test', 'Assertion Failed: Single line match with s flag');
    assert.strictEqual(singleMatchWithoutS ? singleMatchWithoutS[0] : null, 'This is a test', 'Assertion Failed: Single line match without s flag');
    console.log('Assertion Passed: Both flags match single line text correctly.');

    console.log('\n--- All RegExp s (dotAll) Flag demonstrations complete. ---');
}

try {
    runRegExpDotAllDemo();
} catch (error) {
    console.error('\n!!! ES2018 RegExp DotAll Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1); // Exit with a non-zero code to indicate failure
}