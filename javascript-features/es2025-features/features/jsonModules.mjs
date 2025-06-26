// es2025-features/features/json-modules/jsonModules.mjs
import { strict as assert } from 'assert';

// Static import of the JSON module, using 'assert'.
// The JSON object is directly assigned to 'appConfig'.
import appConfig from '../modules/json-config.json' with { type: 'json' };

function runJsonModulesDemo() {
    console.log('\n--- ES2025: JSON Modules ---');

    try {
        console.log(`JSON module successfully imported:`);
        console.log(`App Name: ${appConfig.appName}`);
        console.log(`Version: ${appConfig.version}`);
        console.log(`Dark Mode: ${appConfig.settings.darkMode ? 'Enabled' : 'Disabled'}`);
        console.log(`Features: ${appConfig.features.join(', ')}`);

        // Assertions to verify the imported data
        assert.strictEqual(appConfig.appName, 'My ES2025 App', 'Assertion failed: appName mismatch.');
        assert.strictEqual(appConfig.version, '1.0.0', 'Assertion failed: version mismatch.');
        assert.strictEqual(appConfig.settings.darkMode, true, 'Assertion failed: darkMode mismatch.');
        assert.deepStrictEqual(appConfig.features, ['json-modules', 'iterator-helpers'], 'Assertion failed: features array mismatch.');

        // Demonstrate that 'appConfig' is indeed an object
        assert.strictEqual(typeof appConfig, 'object', 'Assertion failed: Imported JSON should be an object.');
        assert.ok(appConfig !== null, 'Assertion failed: Imported JSON should not be null.');

        console.log('\nAssertion passed: JSON module imported and accessed successfully.');

    } catch (error) {
        console.error('\n!!! ES2025 JSON Modules Demo FAILED !!!');
        console.error('Error details:', error.message);
        if (error.message.includes('needs an import attribute of "type: json"') || error instanceof SyntaxError) {
            console.error('\nNOTE: This error suggests that the static import assertion syntax (`assert { type: "json" }`)');
            console.error('is not fully supported in your Node.js version/configuration, or your file is not treated as an ES Module.');
            console.error('Please ensure you have `"type": "module"` in your `package.json` or your file has the `.mjs` extension.');
            console.error('If the error persists, you might need to use the dynamic import approach with `assert` as shown in previous examples.');
        } else {
            console.error('\nEnsure your Node.js version (16.14.0+ for JSON modules, specific versions might vary for static assertions) supports JSON modules and ES Modules correctly.');
        }
        process.exit(1);
    }
}

// Execute the demo function
runJsonModulesDemo();