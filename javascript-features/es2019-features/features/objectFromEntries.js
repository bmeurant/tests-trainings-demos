// es2019-features/features/objectFromEntries.js
const assert = require('assert');

function runObjectFromEntriesDemo() {
    console.log('--- ES2019: Object.fromEntries() ---');

    // --- 1. Basic usage: converting an array of key-value pairs to an object ---
    console.log('\n--- Converting array of key-value pairs to an object ---');
    const entries = [['name', 'Alice'], ['age', 30], ['city', 'New York']];
    console.log('Array of entries:', entries);
    const userObject = Object.fromEntries(entries);
    console.log('Object from entries:', userObject);
    assert.deepStrictEqual(userObject, { name: 'Alice', age: 30, city: 'New York' }, 'Assertion Failed: Object not created correctly from entries array');
    console.log('Assertion Passed: Object created correctly from array of entries.');

    // --- 2. Round-tripping: Object -> entries -> Object ---
    console.log('\n--- Round-tripping: Object -> entries -> Object ---');
    const originalObj = { a: 1, b: 'hello', c: true };
    console.log('Original Object:', originalObj);

    const entriesFromObj = Object.entries(originalObj);
    console.log('Entries from Object:', entriesFromObj);

    const newObj = Object.fromEntries(entriesFromObj);
    console.log('New Object from entries:', newObj);
    assert.deepStrictEqual(newObj, originalObj, 'Assertion Failed: Round-trip failed');
    console.log('Assertion Passed: Object.entries() and Object.fromEntries() are inverse operations.');

    // --- 3. Converting a Map to an Object ---
    console.log('\n--- Converting a Map to an Object ---');
    const userMap = new Map([
        ['id', 'user-123'],
        ['isAdmin', false],
        ['theme', 'dark']
    ]);
    console.log('Original Map:', userMap);
    const userFromMap = Object.fromEntries(userMap);
    console.log('Object from Map:', userFromMap);
    assert.deepStrictEqual(userFromMap, { id: 'user-123', isAdmin: false, theme: 'dark' }, 'Assertion Failed: Object not created correctly from Map');
    console.log('Assertion Passed: Object created correctly from Map.');

    // --- 4. Using with URLSearchParams (iterable of key-value pairs) ---
    console.log('\n--- Using with URLSearchParams ---');
    const params = new URLSearchParams('name=Bob&age=25&city=London');
    console.log('URLSearchParams:', params.toString());
    const paramsObject = Object.fromEntries(params);
    console.log('Object from URLSearchParams:', paramsObject);
    assert.deepStrictEqual(paramsObject, { name: 'Bob', age: '25', city: 'London' }, 'Assertion Failed: Object not created correctly from URLSearchParams');
    console.log('Assertion Passed: Object created correctly from URLSearchParams.');

    console.log('\n--- All Object.fromEntries() demonstrations complete. ---');
}

try {
    runObjectFromEntriesDemo();
} catch (error) {
    console.error('\n!!! ES2019 Object.fromEntries() Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}