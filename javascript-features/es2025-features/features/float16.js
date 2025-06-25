// es2025-features/features/float16.js
const assert = require('assert');

function runFloat16Demo() {
    console.log('\n--- ES2024: Float16 on TypedArrays, DataView, Math.f16round ---');

    // --- 1. Float16Array ---
    console.log('\n--- 1. Float16Array ---');
    const float16Arr = new Float16Array(4); // Create a Float16Array with 4 elements
    float16Arr[0] = 1.0;
    float16Arr[1] = 0.125; // Exact representation in Float16
    float16Arr[2] = 3.14159; // Will be approximated
    float16Arr[3] = -0.00001; // Small number

    console.log(`Float16Array created: [${float16Arr[0]}, ${float16Arr[1]}, ${float16Arr[2]}, ${float16Arr[3]}]`);
    console.log(`Byte length of Float16Array: ${float16Arr.byteLength} bytes (${float16Arr.length} elements * 2 bytes/element)`);
    assert.strictEqual(float16Arr.byteLength, 8, 'Assertion failed: Float16Array byteLength should be 8.');
    assert.strictEqual(float16Arr.length, 4, 'Assertion failed: Float16Array length should be 4.');

    // Demonstrate approximation: 3.14159 is not exact in Float16
    const approximatedPi = float16Arr[2];
    // A common Float16 representation for PI might be around 3.14 (3.140625 if exact)
    console.log(`Original Pi (Float64): 3.14159`);
    console.log(`Approximated Pi (Float16 from array): ${approximatedPi}`);
    // Check if the value is reasonably close, acknowledging precision loss
    assert(Math.abs(approximatedPi - 3.14159) < 0.001, 'Assertion failed: Pi approximation in Float16 is out of expected range.');
    console.log('Assertion passed: Float16Array creation and basic assignment works.');

    // --- 2. DataView for reading/writing Float16 ---
    console.log('\n--- 2. DataView for reading/writing Float16 ---');
    const buffer = new ArrayBuffer(4); // A 4-byte buffer to store two Float16
    const dataView = new DataView(buffer);

    // Write Float16 values
    const val1 = 12.34;
    const val2 = -5.67;
    dataView.setFloat16(0, val1); // Write at offset 0
    dataView.setFloat16(2, val2); // Write at offset 2 (each Float16 is 2 bytes)

    console.log(`Written ${val1} and ${val2} as Float16 to DataView.`);

    // Read Float16 values
    const readVal1 = dataView.getFloat16(0);
    const readVal2 = dataView.getFloat16(2);

    console.log(`Read Float16 value at offset 0: ${readVal1}`);
    console.log(`Read Float16 value at offset 2: ${readVal2}`);

    // Due to precision loss, check for approximate equality
    assert(Math.abs(readVal1 - val1) < 0.01, 'Assertion failed: DataView getFloat16/setFloat16 readVal1 mismatch.');
    assert(Math.abs(readVal2 - val2) < 0.01, 'Assertion failed: DataView getFloat16/setFloat16 readVal2 mismatch.');
    console.log('Assertion passed: DataView setFloat16/getFloat16 works with expected precision.');

    // --- 3. Math.f16round() ---
    console.log('\n--- 3. Math.f16round() ---');

    // Math.f16round(x) rounds a Float64 number to the nearest Float16 representation.
    // This is useful for explicit control over half-precision conversion.
    const numToRound = 3.1415926535; // A Float64 number
    const roundedF16 = Math.f16round(numToRound);

    console.log(`Original Float64: ${numToRound}`);
    console.log(`Rounded to Float16 using Math.f16round(): ${roundedF16}`);

    // The actual value depends on the exact Float16 representation.
    // For 3.1415926535, a common F16 representation is 3.140625.
    assert(Math.abs(roundedF16 - 3.140625) < 0.000001, 'Assertion failed: Math.f16round() did not produce expected Float16 value.');
    console.log('Assertion passed: Math.f16round() works as expected.');

    // Another example: a number that can be exactly represented
    const exactF16 = 0.5; // Can be exactly represented as Float16
    const roundedExact = Math.f16round(exactF16);
    console.log(`Original Float64 (exact): ${exactF16}`);
    console.log(`Rounded to Float16 using Math.f16round(): ${roundedExact}`);
    assert.strictEqual(roundedExact, exactF16, 'Assertion failed: Exact Float16 representation mismatch.');
    console.log('Assertion passed: Math.f16round() handles exact representations.');

    console.log('\n--- All Float16 demonstrations complete. ---');
}

try {
    runFloat16Demo();
} catch (error) {
    console.error('\n!!! ES2024 Float16 Demo FAILED !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}