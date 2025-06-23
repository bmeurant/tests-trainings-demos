// es2020/features/importMeta.mjs
import assert from 'assert'; // ES Module syntax for assert

async function runImportMetaDemo() {
    console.log('--- ES2020: import.meta ---');

    // --- 1. Accessing import.meta.url ---
    console.log('\n--- Accessing import.meta.url ---');
    const moduleUrl = import.meta.url;
    console.log(`URL of this module: ${moduleUrl}`);

    // In Node.js, it will typically be a 'file://' URL
    assert.ok(moduleUrl.startsWith('file://') || moduleUrl.startsWith('http://') || moduleUrl.startsWith('https://'), 'Assertion Failed: moduleUrl does not look like a URL');
    assert.ok(moduleUrl.includes('es2020-features/features/importMeta.mjs'), 'Assertion Failed: moduleUrl does not contain the correct path');
    console.log('Assertion Passed: import.meta.url provides the correct module URL.');

    // --- 2. Using import.meta.url to resolve relative paths (e.g., for assets) ---
    console.log('\n--- Using import.meta.url to resolve relative paths ---');
    // In a real application, you might use `new URL('./data.json', import.meta.url)`
    // to get the absolute path to a data file located next to your module.
    const relativePath = './some-data.json';
    const absoluteDataUrl = new URL(relativePath, import.meta.url);
    console.log(`Relative path "${relativePath}" resolved to: ${absoluteDataUrl.href}`);
    assert.ok(absoluteDataUrl.href.includes('es2020-features/features/some-data.json'), 'Assertion Failed: Relative path resolution incorrect');
    console.log('Assertion Passed: import.meta.url can be used for relative path resolution.');

    // --- 3. Other potential properties (environment-specific) ---
    console.log('\n--- Other potential properties (environment-specific) ---');
    console.log('Full import.meta object:', import.meta);

    // In Node.js, `import.meta.dirname` and `import.meta.filename` are common.
    // These are not standard ECMAScript but Node.js specific enhancements.
    if (typeof import.meta.dirname !== 'undefined') {
        console.log(`Node.js specific: import.meta.dirname: ${import.meta.dirname}`);
        assert.ok(import.meta.dirname.includes('es2020-features/features'), 'Assertion Failed: Node.js dirname not as expected');
        console.log('Assertion Passed: Node.js specific import.meta.dirname exists.');
    }
    if (typeof import.meta.filename !== 'undefined') {
        console.log(`Node.js specific: import.meta.filename: ${import.meta.filename}`);
        assert.ok(import.meta.filename.includes('es2020-features/features/importMeta.mjs'), 'Assertion Failed: Node.js filename not as expected');
        console.log('Assertion Passed: Node.js specific import.meta.filename exists.');
    }

    console.log('\n--- All import.meta demonstrations complete. ---');
}

// Call the async function (import.meta is only available in module scope)
// We export the function and call it via the run-all-features script which is now also a module
// This is a common pattern when testing module-specific features
try {
    await runImportMetaDemo();
} catch (error) {
    console.error('\n!!! ES2020 import.meta Demo Failed !!!');
    console.error('Error details:', error.message);
    // Ensure the process exits even if async fails to propagate the error
    process.exit(1);
}