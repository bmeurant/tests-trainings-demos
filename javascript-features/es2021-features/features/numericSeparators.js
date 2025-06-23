// es2021-features/features/numericSeparators.js
const assert = require('assert');

function runNumericSeparatorsDemo() {
    console.log('--- ES2021: Numeric Separators (e.g., 1_000_000) ---');

    // --- 1. Integer numbers ---
    console.log('\n--- 1. Integer numbers ---');
    const million = 1_000_000;
    const billion = 1_000_000_000;
    console.log(`1_000_000 = ${million}`);
    console.log(`1_000_000_000 = ${billion}`);
    assert.strictEqual(million, 1000000, 'Assertion Failed: Million incorrect');
    assert.strictEqual(billion, 1000000000, 'Assertion Failed: Billion incorrect');
    console.log('Assertion Passed: Numeric separators work for integers.');

    // --- 2. Decimal numbers ---
    console.log('\n--- 2. Decimal numbers ---');
    const pi = 3.141_592_653;
    const speedOfLight = 299_792_458.0;
    console.log(`3.141_592_653 = ${pi}`);
    console.log(`299_792_458.0 = ${speedOfLight}`);
    assert.strictEqual(pi, 3.141592653, 'Assertion Failed: Pi incorrect');
    assert.strictEqual(speedOfLight, 299792458, 'Assertion Failed: Speed of light incorrect');
    console.log('Assertion Passed: Numeric separators work for decimals.');

    // --- 3. BigInts (already saw this, but shows compatibility) ---
    console.log('\n--- 3. BigInts (compatibility) ---');
    const largeId = 9_876_543_210_123_456_789n;
    console.log(`9_876_543_210_123_456_789n = ${largeId}`);
    assert.strictEqual(largeId, 9876543210123456789n, 'Assertion Failed: BigInt with separator incorrect');
    console.log('Assertion Passed: Numeric separators work with BigInts.');

    // --- 4. Different bases (binary, octal, hexadecimal) ---
    console.log('\n--- 4. Different bases ---');
    const binary = 0b1010_0001_1110_1001; // 41449 in decimal
    const octal = 0o7_5_5_3_2; // 31578 in decimal
    const hex = 0xFF_EE_DD_CC; // 4293844428 in decimal
    console.log(`0b1010_0001_1110_1001 = ${binary}`);
    console.log(`0o7_5_5_3_2 = ${octal}`);
    console.log(`0xFF_EE_DD_CC = ${hex}`);
    assert.strictEqual(binary, 41449, 'Assertion Failed: Binary incorrect');
    assert.strictEqual(octal, 31578, 'Assertion Failed: Octal incorrect');
    assert.strictEqual(hex, 4293844428, 'Assertion Failed: Hex incorrect');
    console.log('Assertion Passed: Numeric separators work with different number bases.');

    // --- 5. Invalid usage (syntax errors) ---
    console.log('\n--- 5. Invalid usage (syntax errors - will not run) ---');
    // Uncommenting these lines would cause syntax errors:
    // const invalid1 = 1_00; // Cannot end with _
    // const invalid2 = 0x_12; // Cannot start with _ after prefix
    // const invalid3 = 1__000; // Cannot have multiple underscores
    console.log('  (Demonstrating these would cause a syntax error before script execution).');
    console.log('  Valid separators only appear between digits.');

    console.log('\n--- All Numeric Separators demonstrations complete. ---');
}

try {
    runNumericSeparatorsDemo();
} catch (error) {
    console.error('\n!!! ES2021 Numeric Separators Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}