// es2022-features/features/ergonomicBrandChecks.js
const assert = require('assert');

class Account {
    #balance; // Private field for balance
    #accountNumber; // Another private field

    constructor(initialBalance, accountNumber) {
        this.#balance = initialBalance;
        this.#accountNumber = accountNumber;
    }

    deposit(amount) {
        if (amount > 0) {
            this.#balance += amount;
            console.log(`  Deposited ${amount}. New balance: ${this.#balance}`);
        }
    }

    getBalance() {
        return this.#balance;
    }

    // --- Static method to demonstrate brand check for #balance ---
    // This method has access to the private #balance field's "brand"
    static hasBalance(obj) {
        return #balance in obj;
    }

    // Static method to demonstrate brand check for #accountNumber
    static hasAccountNumber(obj) {
        return #accountNumber in obj;
    }
}

class VIPAccount extends Account {
    #vipCode = 'VIP123'; // A private field specific to VIPAccount

    constructor(initialBalance, accountNumber) {
        super(initialBalance, accountNumber);
    }

    getVipCode() {
        return this.#vipCode;
    }

    // --- Static method to demonstrate brand check for #vipCode ---
    static hasVipCode(obj) {
        return #vipCode in obj;
    }
}


function runErgonomicBrandChecksDemo() {
    console.log('--- ES2022: Ergonomic Brand Checks for Private Fields ---');

    // Create instances
    const myAccount = new Account(100, 'ACC001');
    const vipAccount = new VIPAccount(500, 'V-ACC001');
    const plainObject = { someProp: 'value' };

    // --- 1. Checking for existence of private fields on instances using static methods ---
    console.log('\n--- 1. Checking for existence of private fields on instances using static methods ---');

    // Check if myAccount has #balance via Account.hasBalance static method
    const hasBalance1 = Account.hasBalance(myAccount);
    console.log(`Does myAccount have #balance? ${hasBalance1}`);
    assert.strictEqual(hasBalance1, true, 'Assertion Failed: myAccount should have #balance.');

    // Check if plainObject has #balance via Account.hasBalance static method (should be false)
    const hasBalance2 = Account.hasBalance(plainObject);
    console.log(`Does plainObject have #balance? ${hasBalance2}`);
    assert.strictEqual(hasBalance2, false, 'Assertion Failed: plainObject should NOT have #balance.');

    // Check if vipAccount has #vipCode via VIPAccount.hasVipCode static method
    const hasVipCode1 = VIPAccount.hasVipCode(vipAccount);
    console.log(`Does vipAccount have #vipCode? ${hasVipCode1}`);
    assert.strictEqual(hasVipCode1, true, 'Assertion Failed: vipAccount should have #vipCode.');

    // Check if myAccount (base class) has #vipCode (should be false)
    // We need to use VIPAccount.hasVipCode here as #vipCode is part of VIPAccount's brand
    const hasVipCode2 = VIPAccount.hasVipCode(myAccount);
    console.log(`Does myAccount have #vipCode? ${hasVipCode2}`);
    assert.strictEqual(hasVipCode2, false, 'Assertion Failed: myAccount should NOT have #vipCode.');
    console.log('Assertion Passed: Brand checks with `#field in obj` work correctly within class context.');

    // --- 2. Using brand checks for type guarding / validation in a separate function ---
    console.log('\n--- 2. Using brand checks for type guarding / validation in a separate function ---');

    // To make a general purpose "isAccount" check that can be used outside the Account class,
    // we would typically put this check within a static method of the Account class,
    // or define a separate function that the class "exposes" in some way.
    // Here, we demonstrate how `Account.hasBalance` and `Account.hasAccountNumber` can be used.
    function processGenericObjectAsAccount(obj) {
        if (Account.hasBalance(obj) && Account.hasAccountNumber(obj)) {
            console.log(`  Processing a valid Account-like object.`);
            // IMPORTANT: Even if the brand check passes, direct access to obj.#balance would still fail
            // if this function is not itself inside the Account class.
            // You would typically call public methods on the object, assuming its "brand" implies functionality.
            // For demonstration, we'll cast it to a known type (TypeScript style for concept)
            // or assume the public API is callable.
            // We know `obj` is an instance of Account or a subclass if these pass.
            obj.deposit(50); // Safe to call public methods relying on private fields
            console.log(`  Account balance: ${obj.getBalance()}`);
            return true;
        } else {
            console.log(`  Cannot process: Object does not have required private account fields.`);
            return false;
        }
    }

    console.log('Attempting to process myAccount:');
    assert.strictEqual(processGenericObjectAsAccount(myAccount), true, 'Assertion Failed: myAccount should be processed.');
    assert.strictEqual(myAccount.getBalance(), 150, 'Assertion Failed: myAccount balance after processing incorrect.');

    console.log('\nAttempting to process vipAccount (should also work as it inherits #balance/#accountNumber):');
    assert.strictEqual(processGenericObjectAsAccount(vipAccount), true, 'Assertion Failed: vipAccount should be processed.');
    assert.strictEqual(vipAccount.getBalance(), 550, 'Assertion Failed: vipAccount balance after processing incorrect.');

    console.log('\nAttempting to process plainObject:');
    assert.strictEqual(processGenericObjectAsAccount(plainObject), false, 'Assertion Failed: plainObject should not be processed.');

    // Demonstrating that #field in obj is different from checking public fields
    const objWithPublicField = { balance: 100 };
    console.log(`\nDoes objWithPublicField have #balance (via Account.hasBalance)? ${Account.hasBalance(objWithPublicField)}`);
    assert.strictEqual(Account.hasBalance(objWithPublicField), false, 'Assertion Failed: Public field should not register as private field brand.');
    console.log('Assertion Passed: `#field in obj` specifically checks for private fields, not public ones.');

    console.log('\n--- All Ergonomic Brand Checks demonstrations complete. ---');
}

try {
    runErgonomicBrandChecksDemo();
} catch (error) {
    console.error('\n!!! ES2022 Ergonomic Brand Checks for Private Fields Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}