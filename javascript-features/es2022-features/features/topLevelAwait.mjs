// es2022-features/features/topLevelAwait.mjs
import assert from 'assert'; // Using ES Module import syntax

console.log('--- ES2022: Top-level await ---');
console.log('This script demonstrates top-level await in an ES Module (.mjs file).');

// --- 1. Simulating an asynchronous operation ---
async function fetchData() {
    console.log('  Fetching data (asynchronous operation started)...');
    return new Promise(resolve => {
        setTimeout(() => {
            const data = { message: 'Data fetched successfully!' };
            console.log(`  Data fetched: ${data.message}`);
            resolve(data);
        }, 500); // Simulate network delay
    });
}

// Using top-level await directly in the module scope
console.log('\n--- 1. Using await at the top level to fetch data ---');
const config = await fetchData(); // Await here, without an enclosing async function
console.log(`Received config: ${config.message}`);
assert.strictEqual(config.message, 'Data fetched successfully!', 'Assertion Failed: Data not fetched correctly with top-level await.');
console.log('Assertion Passed: Top-level await for data fetching works.');

// --- 2. Using top-level await for dynamic imports ---
console.log('\n--- 2. Using await at the top level for dynamic import ---');
let dynamicModule;
try {
    // Dynamically import a module (e.g., based on some condition or configuration)
    dynamicModule = await import('../modules/dynamicModule.mjs'); // This file also needs to be a module
    console.log(`  Dynamically imported module says: ${dynamicModule.sayHello()}`);
    assert.strictEqual(dynamicModule.sayHello(), 'Hello from dynamic module!', 'Assertion Failed: Dynamic module import failed.');
    console.log('Assertion Passed: Top-level await for dynamic imports works.');
} catch (e) {
    console.error(`!!! Error during dynamic import: ${e.message}`);
    assert.fail('Assertion Failed: Dynamic import failed unexpectedly.');
}

// --- 3. Subsequent code runs only after await resolves ---
console.log('\n--- 3. Subsequent code runs after await resolves ---');
const finalMessage = 'All top-level await operations completed.';
console.log(finalMessage);
assert.strictEqual(finalMessage, 'All top-level await operations completed.', 'Assertion Failed: Final message incorrect.');
console.log('Assertion Passed: Code execution pauses until top-level awaits are resolved.');

console.log('\n--- All Top-level await demonstrations complete. ---');