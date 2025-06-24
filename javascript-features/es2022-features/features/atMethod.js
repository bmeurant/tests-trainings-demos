// es2022-features/features/atMethod.js
const assert = require('assert');

function runAtMethodDemo() {
    console.log('--- ES2022: .at() method for Array, String, TypedArray ---');

    const myArray = [10, 20, 30, 40, 50];
    const myString = 'Hello World';
    const myTypedArray = new Uint8Array([1, 2, 3, 4, 5]);

    console.log(`\nmyArray: [${myArray}]`);
    console.log(`myString: "${myString}"`);
    console.log(`myTypedArray: [${myTypedArray}]`);

    // --- 1. Positive indices ---
    console.log('\n--- 1. Positive indices ---');
    console.log(`myArray.at(0): ${myArray.at(0)}`);
    assert.strictEqual(myArray.at(0), 10, 'Assertion Failed: Array positive index 0 incorrect.');
    console.log(`myString.at(1): ${myString.at(1)}`);
    assert.strictEqual(myString.at(1), 'e', 'Assertion Failed: String positive index 1 incorrect.');
    console.log(`myTypedArray.at(2): ${myTypedArray.at(2)}`);
    assert.strictEqual(myTypedArray.at(2), 3, 'Assertion Failed: TypedArray positive index 2 incorrect.');
    console.log('Assertion Passed: .at() works for positive indices (like [] access).');

    // --- 2. Negative indices ---
    console.log('\n--- 2. Negative indices ---');
    console.log(`myArray.at(-1): ${myArray.at(-1)} (last element)`);
    assert.strictEqual(myArray.at(-1), 50, 'Assertion Failed: Array negative index -1 incorrect.');
    console.log(`myArray.at(-2): ${myArray.at(-2)} (second to last)`);
    assert.strictEqual(myArray.at(-2), 40, 'Assertion Failed: Array negative index -2 incorrect.');

    console.log(`myString.at(-1): ${myString.at(-1)} (last character)`);
    assert.strictEqual(myString.at(-1), 'd', 'Assertion Failed: String negative index -1 incorrect.');
    console.log(`myString.at(-2): ${myString.at(-2)} (second to last)`);
    assert.strictEqual(myString.at(-2), 'l', 'Assertion Failed: String negative index -2 incorrect.');

    console.log(`myTypedArray.at(-1): ${myTypedArray.at(-1)} (last element)`);
    assert.strictEqual(myTypedArray.at(-1), 5, 'Assertion Failed: TypedArray negative index -1 incorrect.');
    console.log(`myTypedArray.at(-3): ${myTypedArray.at(-3)} (third to last)`);
    assert.strictEqual(myTypedArray.at(-3), 3, 'Assertion Failed: TypedArray negative index -3 incorrect.');
    console.log('Assertion Passed: .at() correctly handles negative indices.');

    // --- 3. Out of bounds indices ---
    console.log('\n--- 3. Out of bounds indices ---');
    console.log(`myArray.at(99): ${myArray.at(99)}`);
    assert.strictEqual(myArray.at(99), undefined, 'Assertion Failed: Array out of bounds positive index incorrect.');
    console.log(`myArray.at(-99): ${myArray.at(-99)}`);
    assert.strictEqual(myArray.at(-99), undefined, 'Assertion Failed: Array out of bounds negative index incorrect.');

    console.log(`myString.at(99): ${myString.at(99)}`);
    assert.strictEqual(myString.at(99), undefined, 'Assertion Failed: String out of bounds positive index incorrect.');
    console.log(`myString.at(-99): ${myString.at(-99)}`);
    assert.strictEqual(myString.at(-99), undefined, 'Assertion Failed: String out of bounds negative index incorrect.');

    console.log('Assertion Passed: .at() returns undefined for out-of-bounds indices.');

    console.log('\n--- All .at() method demonstrations complete. ---');
}

try {
    runAtMethodDemo();
} catch (error) {
    console.error('\n!!! ES2022 .at() method Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}