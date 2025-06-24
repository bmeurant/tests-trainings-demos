// es2022-features/features/objectHasOwn.js
const assert = require('assert');

function runObjectHasOwnDemo() {
    console.log('--- ES2022: Object.hasOwn() ---');

    const user = {
        name: 'Alice',
        age: 30,
        city: 'New York'
    };

    // --- 1. Basic usage: Checking direct properties ---
    console.log('\n--- 1. Basic usage: Checking direct properties ---');
    console.log(`Does user have 'name'? ${Object.hasOwn(user, 'name')}`);
    assert.strictEqual(Object.hasOwn(user, 'name'), true, 'Assertion Failed: user should have own property "name"');

    console.log(`Does user have 'email'? ${Object.hasOwn(user, 'email')}`);
    assert.strictEqual(Object.hasOwn(user, 'email'), false, 'Assertion Failed: user should not have own property "email"');
    console.log('Assertion Passed: Object.hasOwn works for direct properties.');

    // --- 2. Handling inherited properties ---
    console.log('\n--- 2. Handling inherited properties ---');
    const proto = {
        inheritedProp: 'I am inherited'
    };
    const objWithInheritance = Object.create(proto);
    objWithInheritance.ownProp = 'I am own';

    console.log(`Does objWithInheritance have 'ownProp'? ${Object.hasOwn(objWithInheritance, 'ownProp')}`);
    assert.strictEqual(Object.hasOwn(objWithInheritance, 'ownProp'), true, 'Assertion Failed: objWithInheritance should have own property "ownProp"');

    console.log(`Does objWithInheritance have 'inheritedProp'? ${Object.hasOwn(objWithInheritance, 'inheritedProp')}`);
    assert.strictEqual(Object.hasOwn(objWithInheritance, 'inheritedProp'), false, 'Assertion Failed: objWithInheritance should not have own property "inheritedProp"');
    console.log('Assertion Passed: Object.hasOwn correctly ignores inherited properties.');

    // --- 3. Comparison with hasOwnProperty ---
    console.log('\n--- 3. Comparison with hasOwnProperty ---');

    // Scenario A: Default behavior (both work)
    console.log(`user.hasOwnProperty('name'): ${user.hasOwnProperty('name')}`);
    assert.strictEqual(user.hasOwnProperty('name'), true, 'Assertion Failed: hasOwnProperty on user "name" incorrect');

    console.log(`user.hasOwnProperty('toString'): ${user.hasOwnProperty('toString')}`);
    assert.strictEqual(user.hasOwnProperty('toString'), false, 'Assertion Failed: hasOwnProperty on user "toString" incorrect'); // Inherited
    console.log('Assertion Passed: hasOwnProperty works for normal objects.');

    // Scenario B: Object without prototype (e.g., Object.create(null))
    const objNoProto = Object.create(null);
    objNoProto.data = 'important';

    console.log(`Does objNoProto have 'data' via Object.hasOwn? ${Object.hasOwn(objNoProto, 'data')}`);
    assert.strictEqual(Object.hasOwn(objNoProto, 'data'), true, 'Assertion Failed: Object.hasOwn on objNoProto "data" incorrect');

    // Will throw an error because objNoProto does not inherit from Object.prototype
    console.log(`Attempting objNoProto.hasOwnProperty('data')...`);
    let hasOwnPropertyError = false;
    try {
        // This line will throw an error: `objNoProto.hasOwnProperty` is not a function
        objNoProto.hasOwnProperty('data');
    } catch (e) {
        hasOwnPropertyError = true;
        console.log(`  Caught expected error: ${e.message}`);
        assert.ok(e instanceof TypeError, 'Assertion Failed: Expected TypeError for hasOwnProperty on null proto object');
    }
    assert.strictEqual(hasOwnPropertyError, true, 'Assertion Failed: hasOwnProperty on null proto object did not throw error');
    console.log('Assertion Passed: Object.hasOwn is safer for objects created with null prototype.');

    // Scenario C: hasOwnProperty is shadowed
    const objShadowed = {
        prop: 'value',
        hasOwnProperty: 'I am a string, not a function!' // Shadowing the method
    };
    console.log(`Does objShadowed have 'prop' via Object.hasOwn? ${Object.hasOwn(objShadowed, 'prop')}`);
    assert.strictEqual(Object.hasOwn(objShadowed, 'prop'), true, 'Assertion Failed: Object.hasOwn on objShadowed "prop" incorrect');

    console.log(`Attempting objShadowed.hasOwnProperty('prop')...`);
    let shadowedHasOwnPropertyError = false;
    try {
        // This line will throw a TypeError: objShadowed.hasOwnProperty is not a function
        objShadowed.hasOwnProperty('prop');
    } catch (e) {
        shadowedHasOwnPropertyError = true;
        console.log(`  Caught expected error: ${e.message}`);
        assert.ok(e instanceof TypeError, 'Assertion Failed: Expected TypeError for shadowed hasOwnProperty');
    }
    assert.strictEqual(shadowedHasOwnPropertyError, true, 'Assertion Failed: Shadowed hasOwnProperty did not throw error');
    console.log('Assertion Passed: Object.hasOwn is safer when hasOwnProperty is shadowed.');

    console.log('\n--- All Object.hasOwn() demonstrations complete. ---');
}

try {
    runObjectHasOwnDemo();
} catch (error) {
    console.error('\n!!! ES2022 Object.hasOwn() Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}