// es2022-features/features/classFields.js
const assert = require('assert');

function runClassFieldsDemo() {
    console.log('--- ES2022: Class Field Declarations (Public and Private) ---');

    // --- 1. Public Class Fields ---
    console.log('\n--- 1. Public Class Fields ---');
    class Person {
        // Public field with a default value
        greeting = 'Hello';
        lastName; // Public field without a default value, will be undefined

        constructor(firstName, lastName) {
            this.firstName = firstName;
            this.lastName = lastName; // Overwrites the default `undefined` for lastName
        }

        getFullName() {
            return `${this.greeting}, my name is ${this.firstName} ${this.lastName}.`;
        }
    }

    const person1 = new Person('Alice', 'Smith');
    console.log(`Person 1: ${person1.getFullName()}`);
    assert.strictEqual(person1.firstName, 'Alice', 'Assertion Failed: firstName incorrect');
    assert.strictEqual(person1.lastName, 'Smith', 'Assertion Failed: lastName incorrect');
    assert.strictEqual(person1.greeting, 'Hello', 'Assertion Failed: greeting default incorrect');

    const person2 = new Person('Bob');
    console.log(`Person 2 (no last name): ${person2.getFullName()}`);
    assert.strictEqual(person2.lastName, undefined, 'Assertion Failed: lastName should be undefined for person2');
    console.log('Assertion Passed: Public class fields work as expected.');

    // --- 2. Private Class Fields (#) ---
    console.log('\n--- 2. Private Class Fields (#) ---');
    class Counter {
        #count = 0; // Private field, initialized to 0

        constructor(initialCount = 0) {
            if (typeof initialCount === 'number' && initialCount >= 0) {
                this.#count = initialCount;
            }
        }

        increment() {
            this.#count++;
            console.log(`  Counter incremented. Current count: ${this.#count}`);
        }

        getCount() {
            return this.#count;
        }

        // Private method (also part of class features, though often grouped with fields)
        #resetCount() {
            this.#count = 0;
            console.log('  Counter reset via private method.');
        }

        // Public method to expose private method functionality
        reset() {
            this.#resetCount();
        }
    }

    const counter = new Counter(5);
    console.log(`Initial counter value: ${counter.getCount()}`);
    assert.strictEqual(counter.getCount(), 5, 'Assertion Failed: Initial private count incorrect.');

    counter.increment(); // Calls public method, which accesses private field
    assert.strictEqual(counter.getCount(), 6, 'Assertion Failed: Count after increment incorrect.');

    // Attempting to access private field directly will result in a SyntaxError/TypeError
    try {
        console.log(`Attempting to access counter.#count directly...`);
        // This line would cause a SyntaxError if parsed in strict mode
        // or a TypeError if accessed via eval/non-spec-compliant ways
        // (Node.js/browsers will typically throw SyntaxError directly at parse time)
        // console.log(counter.#count);
        // For demonstration, we'll just note it.
        console.log('  Direct access to #count is a SyntaxError. (Not executed)');
    } catch (e) {
        console.error(`  Caught error accessing private field directly: ${e.message}`);
        // assert.ok(e instanceof TypeError, 'Assertion Failed: Expected TypeError for private field access.');
    }

    counter.reset(); // Calls public method that calls private method
    assert.strictEqual(counter.getCount(), 0, 'Assertion Failed: Count after reset incorrect.');
    console.log('Assertion Passed: Private class fields and methods provide true encapsulation.');

    // --- 3. Static Class Fields (also part of class features) ---
    console.log('\n--- 3. Static Class Fields ---');
    class Configuration {
        static #DEFAULT_MAX_ITEMS = 100; // Private static field
        static VERSION = '1.0.0'; // Public static field

        static getMaxItems() {
            return Configuration.#DEFAULT_MAX_ITEMS;
        }
    }

    console.log(`Configuration Version: ${Configuration.VERSION}`);
    assert.strictEqual(Configuration.VERSION, '1.0.0', 'Assertion Failed: Public static field incorrect.');
    console.log(`Max Items from Configuration: ${Configuration.getMaxItems()}`);
    assert.strictEqual(Configuration.getMaxItems(), 100, 'Assertion Failed: Private static field accessed via getter incorrect.');

    // Attempting to access private static field directly
    try {
        console.log(`Attempting to access Configuration.#DEFAULT_MAX_ITEMS directly...`);
        // console.log(Configuration.#DEFAULT_MAX_ITEMS);
        console.log('  Direct access to #DEFAULT_MAX_ITEMS is a SyntaxError. (Not executed)');
    } catch (e) {
        console.error(`  Caught error accessing private static field directly: ${e.message}`);
    }
    console.log('Assertion Passed: Static class fields (public/private) work as expected.');

    console.log('\n--- All Class Field Declarations demonstrations complete. ---');
}

try {
    runClassFieldsDemo();
} catch (error) {
    console.error('\n!!! ES2022 Class Field Declarations Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}