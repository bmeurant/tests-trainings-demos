// es2018/features/objectSpreadRest.js
// Demonstrating Object Spread and Rest Properties in ES2018

'use strict';

const assert = require('assert');

function runObjectSpreadRestDemo() {
    console.log('--- ES2018: Object Spread/Rest Properties ---');

    console.log('\n--- Object Spread Properties (Merging and Copying) ---');
    const userDefaults = { role: 'guest', isActive: true, notifications: true };
    const userProfile = { name: 'Alice', email: 'alice@example.com' };

    // Merging objects
    const fullUser = { ...userDefaults, ...userProfile, lastLogin: new Date().toISOString() };
    console.log('Merged User (userDefaults + userProfile + lastLogin):', fullUser);
    assert.strictEqual(fullUser.role, 'guest', 'Assertion Failed: role should be guest');
    assert.strictEqual(fullUser.name, 'Alice', 'Assertion Failed: name should be Alice');
    console.log('Assertion Passed: Objects merged correctly.');

    // Overriding properties
    const specificUser = { role: 'admin', ...userDefaults, ...userProfile, notifications: false };
    console.log('User with Overrides (role:admin, notifications:false):', specificUser);
    assert.strictEqual(specificUser.role, 'guest', 'Assertion Failed: role should be guest');
    assert.strictEqual(fullUser.name, 'Alice', 'Assertion Failed: name should be Alice');
    assert.strictEqual(specificUser.notifications, false, 'Assertion Failed: notifications should be false');
    console.log('Assertion Passed: Properties overridden correctly.');

    console.log('\n--- Creating a shallow copy ---');

    const originalObject = { a: 1, b: { c: 2 } };
    const copiedObject = { ...originalObject };
    console.log('Original Object for copy:', originalObject);
    console.log('Copied Object:', copiedObject);
    assert.deepStrictEqual(copiedObject, originalObject, 'Assertion Failed: Objects should be deeply equal after copy');
    // Verify shallow copy: changing nested object in copy affects original
    copiedObject.b.c = 3;
    console.log('Original Object after modifying copied nested object:', originalObject);
    console.log('Copied Object after modifying copied nested object:', copiedObject);
    assert.strictEqual(originalObject.b.c, 3, 'Assertion Failed: Shallow copy not behaving as expected');
    console.log('Assertion Passed: Shallow copy created.');

    console.log('\n--- Object Rest Properties (Extracting and Collecting) ---');
    const product = {
        id: 'prod-001',
        name: 'Laptop',
        price: 1200,
        category: 'Electronics',
        stock: 50,
        description: 'Powerful and lightweight laptop.',
    };
    console.log('Original Product Object:', product);

    // Extract 'id', 'name', and collect the rest into 'details'
    const { id, name, ...details } = product;
    console.log(`Extracted 'id': ${id}`);
    console.log(`Extracted 'name': ${name}`);
    console.log('Collected "details" object:', details);

    assert.strictEqual(id, 'prod-001', 'Assertion Failed: id not extracted correctly');
    assert.strictEqual(name, 'Laptop', 'Assertion Failed: name not extracted correctly');
    assert.deepStrictEqual(details, {
        price: 1200,
        category: 'Electronics',
        stock: 50,
        description: 'Powerful and lightweight laptop.',
    }, 'Assertion Failed: details object not collected correctly');
    console.log('Assertion Passed: Rest properties collected correctly.');

    // Destructuring with fewer extracted properties
    const { price, ...restOfProduct } = product;
    console.log(`Extracted 'price': ${price}`);
    console.log('Collected "restOfProduct" object:', restOfProduct);
    assert.strictEqual(price, 1200, 'Assertion Failed: price not extracted correctly');
    assert.deepStrictEqual(restOfProduct, {
        id: 'prod-001',
        name: 'Laptop',
        category: 'Electronics',
        stock: 50,
        description: 'Powerful and lightweight laptop.',
    }, 'Assertion Failed: restOfProduct object not collected correctly');
    console.log('Assertion Passed: Rest properties worked with partial extraction.');

    console.log('\n--- All Object Spread/Rest Properties demonstrations complete. ---');
}

try {
    runObjectSpreadRestDemo();
} catch (error) {
    console.error('\n!!! ES2018 Object Spread/Rest Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1); // Exit with a non-zero code to indicate failure
}