// es2025-features/features/regexpModifiers.js

const assert = require('assert');

function runRegExpPatternModifiersDemo() {
    console.log('\n--- ES2025: RegExp Pattern Modifiers (CommonJS Demo) ---');

    try {
        // --- 1. Case-Insensitivity for a specific part ---
        // Matches 'apple' case-insensitively, but 'Pie' must be case-sensitive.
        console.log('\n--- 1. Partial Case-Insensitivity ---');
        // The 'i' flag here applies only to 'apple' within the (?i:...) group.
        const regex1 = /(?i:apple) Pie/;

        assert.strictEqual(regex1.test('Apple Pie'), true, 'Assertion failed: "Apple Pie" should match.');
        assert.strictEqual(regex1.test('apple Pie'), true, 'Assertion failed: "apple Pie" should match.');
        assert.strictEqual(regex1.test('APPLE Pie'), true, 'Assertion failed: "APPLE Pie" should match.');
        assert.strictEqual(regex1.test('apple pie'), false, 'Assertion failed: "apple pie" should not match (Pie is case-sensitive).');
        assert.strictEqual(regex1.test('Apple pie'), false, 'Assertion failed: "Apple pie" should not match (pie is case-sensitive).');

        console.log(`Regex: ${regex1}`);
        console.log(`"Apple Pie" matches: ${regex1.test('Apple Pie')}`);
        console.log(`"apple Pie" matches: ${regex1.test('apple Pie')}`);
        console.log(`"APPLE Pie" matches: ${regex1.test('APPLE Pie')}`);
        console.log(`"apple pie" matches: ${regex1.test('apple pie')}`);
        console.log(`"Apple pie" matches: ${regex1.test('Apple pie')}`);
        console.log('Assertion passed: Partial case-insensitivity works.');

        // --- 2. Turning flags off ---
        // The global regex has an 'i' flag (implied by the test data), but we turn it off for 'Bar' inside the group using (?-i:...).
        console.log('\n--- 2. Turning Flags Off ---');
        const regex2 = /(?i:Foo)(?-i:Bar)/i; // Global 'i' flag for context, but (?-i:Bar) overrides it.

        assert.strictEqual(regex2.test('fooBar'), true, 'Assertion failed: "fooBar" should match.');
        assert.strictEqual(regex2.test('FooBar'), true, 'Assertion failed: "FooBar" should match.');
        assert.strictEqual(regex2.test('foobar'), false, 'Assertion failed: "foobar" should not match (Bar is case-sensitive due to -i).');
        assert.strictEqual(regex2.test('FOOBAR'), false, 'Assertion failed: "FOOBAR" should not match (Bar is case-sensitive due to -i).');

        console.log(`Regex: ${regex2}`);
        console.log(`"fooBar" matches: ${regex2.test('fooBar')}`);
        console.log(`"FooBar" matches: ${regex2.test('FooBar')}`);
        console.log(`"foobar" matches: ${regex2.test('foobar')}`);
        console.log('Assertion passed: Turning flags off works.');

        console.log('\n--- All RegExp Pattern Modifiers demonstrations complete. ---');

    } catch (error) {
        console.error('\n!!! ES2025 RegExp Pattern Modifiers Demo FAILED UNEXPECTEDLY !!!');
        console.error('Error details:', error.message);
        console.error('\nThis error indicates that your Node.js environment does not yet support the RegExp Pattern Modifiers syntax (`(?<flags>-<flags>:<pattern>)`).');
        console.error('Despite being Stage 4 for ES2025, engine implementation can vary.');
        console.error('Please ensure you are using a very recent version of Node.js (e.g., Node.js 22.x or 24.x, if updates are available) or a modern browser.');
        process.exit(1);
    }
}

// Execute the demo function
runRegExpPatternModifiersDemo();
