// es2020-features/features/nullishCoalescing.js
const assert = require('assert');

function runNullishCoalescingDemo() {
    console.log('--- ES2020: Nullish Coalescing Operator (??) ---');

    // --- 1. Basic usage with null and undefined ---
    console.log('\n--- Basic Usage with null/undefined ---');
    const nullValue = null;
    const undefinedValue = undefined;
    const defaultString = 'Default String';

    console.log(`nullValue ?? defaultString: ${nullValue ?? defaultString}`);
    assert.strictEqual(nullValue ?? defaultString, defaultString, 'Assertion Failed: null not coalesced');

    console.log(`undefinedValue ?? defaultString: ${undefinedValue ?? defaultString}`);
    assert.strictEqual(undefinedValue ?? defaultString, defaultString, 'Assertion Failed: undefined not coalesced');
    console.log('Assertion Passed: Correctly coalesces for null and undefined.');

    // --- 2. Distinction from Logical OR (||) ---
    console.log('\n--- Distinction from Logical OR (||) ---');
    const zero = 0;
    const emptyString = '';
    const falseBoolean = false;

    // With ?? (preserves 0, '', false)
    console.log(`zero ?? defaultString: ${zero ?? defaultString}`);
    assert.strictEqual(zero ?? defaultString, 0, 'Assertion Failed: 0 not preserved with ??');

    console.log(`emptyString ?? defaultString: "${emptyString ?? defaultString}"`);
    assert.strictEqual(emptyString ?? defaultString, '', 'Assertion Failed: Empty string not preserved with ??');

    console.log(`falseBoolean ?? defaultString: ${falseBoolean ?? defaultString}`);
    assert.strictEqual(falseBoolean ?? defaultString, false, 'Assertion Failed: false not preserved with ??');
    console.log('Assertion Passed: ?? preserves 0, empty string, and false.');

    // With || (does NOT preserve 0, '', false - treats as falsy)
    console.log(`\n--- For Comparison: Logical OR (||) ---`);
    console.log(`zero || defaultString: ${zero || defaultString}`);
    assert.strictEqual(zero || defaultString, defaultString, 'Assertion Failed: 0 not coalesced with ||');

    console.log(`emptyString || defaultString: ${emptyString || defaultString}`);
    assert.strictEqual(emptyString || defaultString, defaultString, 'Assertion Failed: Empty string not coalesced with ||');

    console.log(`falseBoolean || defaultString: ${falseBoolean || defaultString}`);
    assert.strictEqual(falseBoolean || defaultString, defaultString, 'Assertion Failed: false not coalesced with ||');
    console.log('Assertion Passed: || treats 0, empty string, and false as falsy.');

    // --- 3. Chaining with optional chaining ---
    console.log('\n--- Chaining with Optional Chaining ---');
    const userConfig = {
        darkMode: null,
        language: 'en',
        settings: undefined
    };

    // Get dark mode setting, default to true if null/undefined
    const actualDarkMode = userConfig.darkMode ?? true;
    console.log(`User dark mode (null): ${actualDarkMode}`);
    assert.strictEqual(actualDarkMode, true, 'Assertion Failed: Null darkMode not handled with ??');
    console.log('Assertion Passed: Null darkMode handled correctly.');

    // Get notifications setting, which might be missing. Default to false.
    const actualNotifications = userConfig.notifications ?? false;
    console.log(`User notifications (missing): ${actualNotifications}`);
    assert.strictEqual(actualNotifications, false, 'Assertion Failed: Missing notifications not handled with ??');
    console.log('Assertion Passed: Missing notifications handled correctly.');

    // Access deeply nested and provide a default
    const themeColor = userConfig.settings?.theme?.color ?? 'blue';
    console.log(`User theme color (deeply nested, missing): ${themeColor}`);
    assert.strictEqual(themeColor, 'blue', 'Assertion Failed: Deeply nested missing property not handled');
    console.log('Assertion Passed: Deeply nested missing property handled.');

    console.log('\n--- All Nullish Coalescing Operator demonstrations complete. ---');
}

try {
    runNullishCoalescingDemo();
} catch (error) {
    console.error('\n!!! ES2020 Nullish Coalescing Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}