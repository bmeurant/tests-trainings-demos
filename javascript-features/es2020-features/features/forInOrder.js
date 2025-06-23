// es2020-features/features/forInOrder.js
const assert = require('assert');

function runForInOrderDemo() {
    console.log('--- ES2020: for-in Order Specification ---');

    // --- 1. Object with mixed numeric and string keys ---
    console.log('\n--- Object with mixed numeric and string keys ---');
    const obj1 = {
        '2': 'value2',
        'alpha': 'valueAlpha',
        '1': 'value1',
        'beta': 'valueBeta',
        '0': 'value0',
    };
    console.log('Original Object:', obj1);

    const keys1 = [];
    for (const key in obj1) {
        keys1.push(key);
    }
    console.log('Keys iterated with for...in:', keys1);

    // Expected order: numeric keys in ascending order, then string keys in insertion order.
    // Note: Insertion order for string keys is generally preserved for simple objects,
    // but the exact definition for `for...in` was previously implementation-dependent
    // for non-numeric string keys. ES2020 formalizes this.
    const expectedKeys1 = ['0', '1', '2', 'alpha', 'beta'];
    assert.deepStrictEqual(keys1, expectedKeys1, 'Assertion Failed: for...in order incorrect for mixed keys');
    console.log('Assertion Passed: for...in order is now predictable.');


    // --- 2. Object with only string keys (insertion order preserved) ---
    console.log('\n--- Object with only string keys ---');
    const obj2 = {
        name: 'John',
        age: 30,
        city: 'New York',
        occupation: 'Engineer'
    };
    console.log('Original Object:', obj2);

    const keys2 = [];
    for (const key in obj2) {
        keys2.push(key);
    }
    console.log('Keys iterated with for...in:', keys2);

    const expectedKeys2 = ['name', 'age', 'city', 'occupation']; // Insertion order
    assert.deepStrictEqual(keys2, expectedKeys2, 'Assertion Failed: for...in order incorrect for string keys');
    console.log('Assertion Passed: for...in preserves insertion order for string keys.');

    // --- 3. Object with inherited properties (still enumerates enumerable inherited properties) ---
    console.log('\n--- Object with inherited properties ---');
    class Parent {
        constructor() {
            this.parentProp = 'parent';
        }
    }
    class Child extends Parent {
        constructor() {
            super();
            this.childProp = 'child';
        }
    }
    const childInstance = new Child();
    childInstance.ownProp = 'own';
    console.log('Child Instance:', childInstance);

    const inheritedKeys = [];
    for (const key in childInstance) {
        inheritedKeys.push(key);
    }
    console.log('Keys iterated with for...in (including inherited):', inheritedKeys);
    // The order might vary slightly across engines for inherited properties *before* ES2020.
    // Now, it's consistent: own numeric, own string (insertion), inherited numeric, inherited string (insertion).
    const expectedInheritedKeys = ['parentProp', 'childProp', 'ownProp']; // Assuming simple insertion order for parent (constructor) and then inherited
    assert.deepStrictEqual(inheritedKeys, expectedInheritedKeys, 'Assertion Failed: for...in order incorrect for inherited properties');
    console.log('Assertion Passed: for...in order for inherited properties is predictable.');

    console.log('\n--- All for-in Order Specification demonstrations complete. ---');
}

try {
    runForInOrderDemo();
} catch (error) {
    console.error('\n!!! ES2020 for-in Order Specification Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}