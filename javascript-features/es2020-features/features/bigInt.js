// es2020-features/features/bigInt.js
const assert = require('assert');

function runBigIntDemo() {
    console.log('--- ES2020: BigInt ---');

    // --- 1. Creating BigInts ---
    console.log('\n--- Creating BigInts ---');
    const largeNumber = Number.MAX_SAFE_INTEGER; // 9007199254740991
    const unsafeNumber = largeNumber + 2;       // imprecise as Number
    const accurateBigInt = BigInt(largeNumber) + 2n;

    console.log(`Unsafe Number: ${unsafeNumber}`);
    console.log(`Accurate BigInt: ${accurateBigInt}`);
    console.log(`BigInt of Unsafe Number: ${BigInt(unsafeNumber)}`);

    assert.notStrictEqual(BigInt(unsafeNumber), accurateBigInt,
        'Assertion Failed: Number preserved too much precision (should not match BigInt result)'
    );
    console.log('Assertion Passed: Number beyond MAX_SAFE_INTEGER is imprecise, BigInt is accurate.');

    const bigIntLiteral = 123456789012345678901234567890n;
    const bigIntFromString = BigInt("98765432109876543210");
    console.log(`BigInt literal: ${bigIntLiteral}`);
    console.log(`BigInt from string: ${bigIntFromString}`);
    assert.strictEqual(typeof bigIntLiteral, 'bigint', 'Assertion Failed: Type of BigInt literal incorrect');
    assert.strictEqual(typeof bigIntFromString, 'bigint', 'Assertion Failed: Type of BigInt from string incorrect');
    console.log('Assertion Passed: BigInt creation methods work as expected.');

    // --- 2. BigInt Arithmetic Operations ---
    console.log('\n--- BigInt Arithmetic Operations ---');
    const a = 10n;
    const b = 3n;

    console.log(`10n + 3n = ${a + b}`);
    assert.strictEqual(a + b, 13n, 'Assertion Failed: BigInt addition incorrect');

    console.log(`10n * 3n = ${a * b}`);
    assert.strictEqual(a * b, 30n, 'Assertion Failed: BigInt multiplication incorrect');

    console.log(`10n / 3n = ${a / b} (result is truncated, no decimals)`);
    assert.strictEqual(a / b, 3n, 'Assertion Failed: BigInt division incorrect'); // Division truncates to whole number
    console.log('Assertion Passed: BigInt arithmetic operations work (division truncates).');

    // --- 3. Mixing BigInt and Number (throws TypeError) ---
    console.log('\n--- Mixing BigInt and Number (TypeError Expected) ---');
    try {
        const mixedResult = 10n + 5; // This will throw a TypeError
        console.log('This line should not be reached.');
        assert.fail('Assertion Failed: Mixed BigInt and Number operation did not throw TypeError');
    } catch (error) {
        console.log(`Expected Error: ${error.name}: ${error.message}`);
        assert.ok(error instanceof TypeError, 'Assertion Failed: Expected TypeError');
        console.log('Assertion Passed: Mixing BigInt and Number correctly throws TypeError.');
    }

    // --- 4. Comparison Operators ---
    console.log('\n--- Comparison Operators ---');
    console.log(`10n > 5n: ${10n > 5n}`);
    assert.strictEqual(10n > 5n, true, 'Assertion Failed: BigInt comparison 1 incorrect');
    console.log(`10n === 10: ${10n === 10}`); // False, different types
    assert.strictEqual(10n === 10, false, 'Assertion Failed: BigInt vs Number strict equality incorrect');
    console.log(`10n == 10: ${10n == 10}`); // True, loose equality
    assert.strictEqual(10n == 10, true, 'Assertion Failed: BigInt vs Number loose equality incorrect');
    console.log('Assertion Passed: Comparison operators work, mindful of type coercion.');

    console.log('\n--- All BigInt demonstrations complete. ---');
}

try {
    runBigIntDemo();
} catch (error) {
    console.error('\n!!! ES2020 BigInt Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}