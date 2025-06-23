// es2020-features/features/optionalChaining.js
const assert = require('assert');

function runOptionalChainingDemo() {
    console.log('--- ES2020: Optional Chaining (?.) ---');

    const user = {
        name: 'Alice',
        address: {
            street: '123 Main St',
            city: 'Anytown',
            zip: '12345'
        },
        contact: {
            email: 'alice@example.com'
            // phone is missing
        },
        preferences: null // preferences is explicitly null
    };

    const adminUser = {
        name: 'Bob',
        // address is missing
        contact: {
            email: 'bob@example.com',
            phone: '555-1234'
        }
    };

    // --- 1. Accessing nested properties safely ---
    console.log('\n--- Accessing Nested Properties Safely ---');

    // Accessing a property that exists
    const userCity = user.address?.city;
    console.log(`User's city (exists): ${userCity}`);
    assert.strictEqual(userCity, 'Anytown', 'Assertion Failed: Existing property not accessed');
    console.log('Assertion Passed: Existing property accessed correctly.');

    // Accessing a property where an intermediate property is missing
    const adminCity = adminUser.address?.city;
    console.log(`Admin's city (missing address): ${adminCity}`);
    assert.strictEqual(adminCity, undefined, 'Assertion Failed: Missing intermediate property did not return undefined');
    console.log('Assertion Passed: Missing intermediate property returns undefined.');

    // Accessing a property where an intermediate property is null
    const userTheme = user.preferences?.theme;
    console.log(`User's theme (preferences is null): ${userTheme}`);
    assert.strictEqual(userTheme, undefined, 'Assertion Failed: Null intermediate property did not return undefined');
    console.log('Assertion Passed: Null intermediate property returns undefined.');

    // Accessing a property that is missing deeper in the chain
    const userPhone = user.contact?.phone;
    console.log(`User's phone (missing contact.phone): ${userPhone}`);
    assert.strictEqual(userPhone, undefined, 'Assertion Failed: Missing deep property did not return undefined');
    console.log('Assertion Passed: Missing deep property returns undefined.');

    // --- 2. Optional chaining with function calls ---
    console.log('\n--- Optional Chaining with Function Calls ---');

    const userWithLogger = {
        name: 'Charlie',
        logger: {
            log: (msg) => console.log(`  Log: ${msg}`)
        }
    };

    const userWithoutLogger = {
        name: 'Diana'
    };

    // Call a method that exists
    console.log(`Calling userWithLogger.logger.log():`);
    userWithLogger.logger?.log('User action recorded!');
    // This assertion is tricky as log has side effects. We'll check if it didn't throw.
    console.log('Assertion Passed: Existing method called safely.');

    // Try to call a method that is missing
    console.log(`Calling userWithoutLogger.logger.log():`);
    let didThrow = false;
    try {
        userWithoutLogger.logger?.log('This should not log.');
    } catch (e) {
        didThrow = true;
        console.error(`  Caught unexpected error: ${e.message}`);
    }
    assert.strictEqual(didThrow, false, 'Assertion Failed: Optional chaining on method call threw an error');
    console.log('Assertion Passed: Missing method call returns undefined without error.');

    // --- 3. Optional chaining with array access ---
    console.log('\n--- Optional Chaining with Array Access ---');
    const data = {
        items: ['apple', 'banana'],
        // users array is missing
    };

    const firstItem = data.items?.[0];
    console.log(`First item (exists): ${firstItem}`);
    assert.strictEqual(firstItem, 'apple', 'Assertion Failed: Array item not accessed');
    console.log('Assertion Passed: Array item accessed safely.');

    const firstUser = data.users?.[0];
    console.log(`First user (missing array): ${firstUser}`);
    assert.strictEqual(firstUser, undefined, 'Assertion Failed: Missing array did not return undefined');
    console.log('Assertion Passed: Missing array access returns undefined.');

    console.log('\n--- All Optional Chaining demonstrations complete. ---');
}

try {
    runOptionalChainingDemo();
} catch (error) {
    console.error('\n!!! ES2020 Optional Chaining Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}