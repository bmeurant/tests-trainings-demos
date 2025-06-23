// es2020-features/features/dynamicImport.js
const assert = require('assert');

async function runDynamicImportDemo() {
    console.log('--- ES2020: Dynamic import() ---');
    console.log('Initial script execution.');

    // --- 1. Basic dynamic import ---
    console.log('\n--- Basic Dynamic Import ---');
    console.log('Attempting to dynamically import myModule.mjs...');
    let moduleContent;
    try {
        // The import() function returns a Promise
        moduleContent = await import('../modules/myModule.mjs'); // Relative path
        console.log('Module imported successfully!');
        console.log('Named export "greeting":', moduleContent.greeting);
        console.log('Named export "sayHello("World")":', moduleContent.sayHello('World'));
        console.log('Default export "moduleContent.default":', moduleContent.default);

        assert.strictEqual(moduleContent.greeting, 'Hello from myModule!', 'Assertion Failed: Named export greeting incorrect');
        assert.strictEqual(moduleContent.sayHello('Test'), 'Hello from myModule! Your name is Test!', 'Assertion Failed: Named export sayHello incorrect');
        assert.deepStrictEqual(moduleContent.default, { message: 'This is the default export.', version: '1.0' }, 'Assertion Failed: Default export incorrect');
        console.log('Assertion Passed: Named and default exports accessed correctly from dynamically imported module.');

    } catch (error) {
        console.error('!!! Error during dynamic import:', error.message);
        assert.fail('Assertion Failed: Dynamic import failed unexpectedly');
    }

    // --- 2. Conditional dynamic import ---
    console.log('\n--- Conditional Dynamic Import ---');
    const loadAdminModule = true; // Imagine this comes from user role or config
    let adminModuleMessage = 'Admin module not loaded.';

    if (loadAdminModule) {
        console.log('  Condition met: Dynamically importing adminModule (simulated)...');
        try {
            // In a real app, this would be a separate admin-specific module
            const adminModule = await import('../modules/myModule.mjs'); // Using myModule again for demo simplicity
            adminModuleMessage = `  Admin module loaded. Message: ${adminModule.default.message}`;
        } catch (error) {
            adminModuleMessage = `  Failed to load admin module: ${error.message}`;
        }
    }
    console.log(adminModuleMessage);
    assert.ok(adminModuleMessage.includes('This is the default export.'), 'Assertion Failed: Conditional import message incorrect');
    console.log('Assertion Passed: Conditional import handled.');

    // --- 3. Error handling with dynamic import ---
    console.log('\n--- Error Handling with Dynamic Import ---');
    console.log('Attempting to import a non-existent module...');
    let importErrorHandled = false;
    try {
        await import('../modules/nonExistentModule.js'); // This file does not exist
        console.log('This line should not be reached.');
    } catch (error) {
        console.log(`  Caught expected error for non-existent module: ${error.message}`);
        importErrorHandled = true;
        assert.ok(error.message.includes('Cannot find module'), 'Assertion Failed: Error message not as expected for missing module');
    }
    assert.strictEqual(importErrorHandled, true, 'Assertion Failed: Error for non-existent module not caught');
    console.log('Assertion Passed: Error handling for dynamic import works.');


    console.log('\n--- All Dynamic import() demonstrations complete. ---');
}

// Call the async function
runDynamicImportDemo().catch(error => {
    console.error('\n!!! ES2020 Dynamic import() Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
});