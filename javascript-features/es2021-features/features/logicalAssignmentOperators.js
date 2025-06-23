// es2021-features/features/logicalAssignmentOperators.js
const assert = require('assert');

function runLogicalAssignmentOperatorsDemo() {
    console.log('--- ES2021: Logical Assignment Operators (&&=, ||=, ??=) ---');

    // --- 1. Logical AND assignment (&&=) ---
    console.log('\n--- Logical AND assignment (&&=) ---');
    let x = 10;
    let y = 5;
    let z = 0;

    // x &&= y (assigns y to x if x is truthy)
    console.log(`Initial x: ${x}`);
    x &&= y;
    console.log(`x &&= y (x was truthy): x = ${x}`); // x becomes 5
    assert.strictEqual(x, 5, 'Assertion Failed: &&= did not assign when truthy.');

    // z &&= y (does not assign y to z if z is falsy)
    console.log(`Initial z: ${z}`);
    z &&= y;
    console.log(`z &&= y (z was falsy): z = ${z}`); // z remains 0
    assert.strictEqual(z, 0, 'Assertion Failed: &&= assigned when falsy.');
    console.log('Assertion Passed: &&= assigns only if left operand is truthy.');

    // --- 2. Logical OR assignment (||=) ---
    console.log('\n--- Logical OR assignment (||=) ---');
    let config = {
        theme: undefined, // Simulating a missing config
        language: 'en'
    };
    let defaultTheme = 'dark';
    let defaultLang = 'fr';

    // config.theme ||= defaultTheme (assigns defaultTheme if config.theme is falsy)
    console.log(`Initial config.theme: ${config.theme}`);
    config.theme ||= defaultTheme;
    console.log(`config.theme ||= defaultTheme (theme was undefined): config.theme = ${config.theme}`); // theme becomes 'dark'
    assert.strictEqual(config.theme, 'dark', 'Assertion Failed: ||= did not assign when falsy.');

    // config.language ||= defaultLang (does not assign if config.language is truthy)
    console.log(`Initial config.language: ${config.language}`);
    config.language ||= defaultLang;
    console.log(`config.language ||= defaultLang (language was truthy): config.language = ${config.language}`); // language remains 'en'
    assert.strictEqual(config.language, 'en', 'Assertion Failed: ||= assigned when truthy.');
    console.log('Assertion Passed: ||= assigns only if left operand is falsy.');

    // --- 3. Nullish Coalescing assignment (??=) ---
    console.log('\n--- Nullish Coalescing assignment (??=) ---');
    let userSettings = {
        fontSize: null,
        lineHeight: undefined,
        showTips: false, // false is a valid setting
        username: 'Alice'
    };
    const defaultFontSize = '16px';
    const defaultLineHeight = '1.5';
    const defaultShowTips = true;
    const defaultUsername = 'Guest';

    // userSettings.fontSize ??= defaultFontSize (assigns if null)
    console.log(`Initial userSettings.fontSize: ${userSettings.fontSize}`);
    userSettings.fontSize ??= defaultFontSize;
    console.log(`userSettings.fontSize ??= defaultFontSize (null): ${userSettings.fontSize}`); // fontSize becomes '16px'
    assert.strictEqual(userSettings.fontSize, '16px', 'Assertion Failed: ??= did not assign when null.');

    // userSettings.lineHeight ??= defaultLineHeight (assigns if undefined)
    console.log(`Initial userSettings.lineHeight: ${userSettings.lineHeight}`);
    userSettings.lineHeight ??= defaultLineHeight;
    console.log(`userSettings.lineHeight ??= defaultLineHeight (undefined): ${userSettings.lineHeight}`); // lineHeight becomes '1.5'
    assert.strictEqual(userSettings.lineHeight, '1.5', 'Assertion Failed: ??= did not assign when undefined.');

    // userSettings.showTips ??= defaultShowTips (does not assign if false, because false is not null/undefined)
    console.log(`Initial userSettings.showTips: ${userSettings.showTips}`);
    userSettings.showTips ??= defaultShowTips;
    console.log(`userSettings.showTips ??= defaultShowTips (false): ${userSettings.showTips}`); // showTips remains false
    assert.strictEqual(userSettings.showTips, false, 'Assertion Failed: ??= assigned when false.');

    // userSettings.username ??= defaultUsername (does not assign if truthy string)
    console.log(`Initial userSettings.username: ${userSettings.username}`);
    userSettings.username ??= defaultUsername;
    console.log(`userSettings.username ??= defaultUsername (truthy): ${userSettings.username}`); // username remains 'Alice'
    assert.strictEqual(userSettings.username, 'Alice', 'Assertion Failed: ??= assigned when truthy.');

    console.log('Assertion Passed: ??= assigns only if left operand is null or undefined.');

    console.log('\n--- All Logical Assignment Operators demonstrations complete. ---');
}

try {
    runLogicalAssignmentOperatorsDemo();
} catch (error) {
    console.error('\n!!! ES2021 Logical Assignment Operators Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}