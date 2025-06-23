// es2019-features/features/functionToStringRevision.js
const assert = require('assert');

function runFunctionToStringRevisionDemo() {
    console.log('--- ES2019: Function.prototype.toString() Revision ---');

    // --- 1. Function declaration ---
    console.log('\n--- Function Declaration ---');
    function /* this is a comment */ myFunction(a, b) {
        // another comment
        const sum = a + b; // inline comment
        return sum;
    }
    const funcString1 = myFunction.toString();
    console.log('Original function:\n', myFunction.toString());

    // Check if comments and extra spaces are preserved
    assert.ok(funcString1.includes('/* this is a comment */'), 'Assertion Failed: Comments in function declaration not preserved');
    assert.ok(funcString1.includes('// another comment'), 'Assertion Failed: Line comments in function declaration not preserved');
    assert.ok(funcString1.includes('// inline comment'), 'Assertion Failed: Inline comments in function declaration not preserved');
    console.log('Assertion Passed: Comments and whitespace preserved for function declaration.');

    // --- 2. Arrow function ---
    console.log('\n--- Arrow Function ---');
    const myArrowFunction = (x, y) => /* params comment */ {
        let result = x * y; // calc result
        return result;
    };
    const funcString2 = myArrowFunction.toString();
    console.log('Original arrow function:\n', myArrowFunction.toString());
    assert.ok(funcString2.includes('/* params comment */'), 'Assertion Failed: Comments in arrow function not preserved');
    assert.ok(funcString2.includes('// calc result'), 'Assertion Failed: Comments in arrow function body not preserved');
    console.log('Assertion Passed: Comments and whitespace preserved for arrow function.');

    // --- 3. Class method ---
    console.log('\n--- Class Method ---');
    class MyClass {
        // constructor comment
        constructor(name) {
            this.name = name;
        }

        greet(/* param */ target) { // method comment
            return `Hello, ${target}! My name is ${this.name}.`;
        }
    }
    const funcString3 = MyClass.prototype.greet.toString();
    console.log('Original class method:\n', MyClass.prototype.greet.toString());
    assert.ok(funcString3.includes('/* param */'), 'Assertion Failed: Comments in class method params not preserved');
    assert.ok(funcString3.includes('// method comment'), 'Assertion Failed: Comments in class method declaration not preserved');
    console.log('Assertion Passed: Comments and whitespace preserved for class method.');

    // --- 4. Built-in functions (still return native code string) ---
    console.log('\n--- Built-in Functions (should still be "[native code]") ---');
    const builtInFuncString = Array.prototype.map.toString();
    console.log('`toString()` output for built-in function (Array.prototype.map):\n', builtInFuncString);
    assert.strictEqual(builtInFuncString, 'function map() { [native code] }', 'Assertion Failed: Built-in function toString changed');
    console.log('Assertion Passed: Built-in function still returns "[native code]".');

    console.log('\n--- All Function.prototype.toString() Revision demonstrations complete. ---');
}

try {
    runFunctionToStringRevisionDemo();
} catch (error) {
    console.error('\n!!! ES2019 Function.prototype.toString() Revision Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}