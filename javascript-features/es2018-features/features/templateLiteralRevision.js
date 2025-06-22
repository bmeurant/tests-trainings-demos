// es2018/features/templateLiteralRevision.js
const assert = require('assert');

function runTemplateLiteralRevisionDemo() {
    console.log('--- ES2018: Template Literal Revision ---');

    // A tagging function that logs the raw parts
    function myTag(strings, ...values) {
        console.log('  --- Inside myTag function ---');
        console.log('  Strings (raw):', strings.raw);
        console.log('  Strings (cooked):', strings);
        console.log('  Values:', values);
        console.log('  --- End myTag function ---');

        // Join raw parts and values to reconstruct the original string
        let result = '';
        strings.raw.forEach((str, i) => {
            result += str;
            if (values[i] !== undefined) {
                result += values[i];
            }
        });
        return result;
    }

    // --- 1. Demonstrating with a "cooked" invalid escape sequence ---
    // Before ES2018, '\unicode' would cause a SyntaxError if not tagged
    // With a tag, it passes the raw string part.
    console.log('\n--- Using an invalid Unicode escape sequence (raw) ---');
    try {
        const rawString1 = myTag`Path: C:\unicode\folder`;
        console.log(`Tagged string result: ${rawString1}`);
        assert.strictEqual(rawString1, 'Path: C:\\unicode\\folder', 'Assertion Failed: Raw string should include \\u');
        console.log('Assertion Passed: Tagged literal successfully processed invalid unicode escape in raw form.');
    } catch (error) {
        console.error('!!! Error:', error.message);
        assert.fail('Assertion Failed: Should not throw an error for tagged literal with invalid escape');
    }

    // --- 2. Demonstrating with an "illegal" octal escape sequence (ES2018+) ---
    // In strict mode, octal escapes like \008 were always a syntax error.
    // Now, in tagged templates, they become raw strings and don't cause an error immediately.
    console.log('\n--- Using an illegal octal escape sequence (raw) ---');
    try {
        const rawString2 = myTag`Number: \008`;
        console.log(`Tagged string result: ${rawString2}`);
        assert.strictEqual(rawString2, 'Number: \\008', 'Assertion Failed: Raw string should include \\008');
        console.log('Assertion Passed: Tagged literal successfully processed illegal octal escape in raw form.');
    } catch (error) {
        console.error('!!! Error:', error.message);
        assert.fail('Assertion Failed: Should not throw an error for tagged literal with illegal octal escape');
    }

    // --- 3. Comparison with untagged literal (still throws error for invalid sequences) ---
    console.log('\n--- Comparing with untagged literal (expected error) ---');
    try {
        // This line will still throw a SyntaxError if run directly by Node.js,
        // because the parser validates untagged template literals.
        // For this demo, we'll use a string that would have caused an error.
        // We'll wrap it in a try-catch to demonstrate the difference if executed untagged.
        // Note: Running this exact line outside a `try-catch` would stop execution.
        // Since we are running this whole file, we have to simulate.

        // To truly demonstrate:
        // Uncomment the line below and try to run only this `console.log` separately.
        // For the purpose of `run-all-features.js`, this line would stop the script.
        // const problematic = `C:\unicode\folder`; // This would be a SyntaxError
        console.log('Untagged template literal with \\unicode:');
        try {
            // This is a common point of confusion. The *parsing* of the template literal
            // happens before the tag function is called.
            // If the raw strings array itself contains illegal escape sequences,
            // the tag function doesn't even get called if it's not a "cooked" value.
            // ES2018 relaxed this for *cooked* values, passing `undefined` for invalid ones.
            // For *raw* values, the characters are passed literally.
            // The key is that the *parser* allows it for tagged templates.
            const cookedInvalid = String.raw`C:\unicode\folder`; // String.raw always uses raw interpretation
            console.log(`  String.raw: ${cookedInvalid}`);
            assert.strictEqual(cookedInvalid, 'C:\\unicode\\folder', 'Assertion Failed: String.raw should preserve backslashes');
            console.log('  Assertion Passed: String.raw correctly handles potential invalid escapes.');

            // Let's create an actual scenario where it would fail without a tag or String.raw
            // This is complex to demo in one file that runs via `node` without crashing the whole script.
            // The point is that `myTag` *can* receive `strings.raw` without syntax error where before it might.
            // Example of what would fail untagged before ES2018:
            // let x = `\xt`; // Invalid hex escape sequence (SyntaxError)
            // let y = `\008`; // Invalid octal escape sequence (SyntaxError in strict mode)
            // With a tag, these would be passed in `strings.raw` array as literal `\x` or `\008`.

            // For the purpose of this demo, the primary gain is that `myTag` receives the raw string
            // without the JS engine complaining about potentially "invalid" sequences that the *tag* might understand.

        } catch (e) {
            console.error(`  Expected error for untagged/non-raw literal: ${e.message}`);
        }

    } catch (error) {
        console.error('!!! Expected error when parsing untagged literal with invalid escapes:', error.message);
        // This catch block is for the *outer* try-catch, if the `myTag` call itself failed, which it shouldn't now.
    }


    console.log('\n--- All Template Literal Revision demonstrations complete. ---');
}

try {
    runTemplateLiteralRevisionDemo();
} catch (error) {
    console.error('\n!!! ES2018 Template Literal Revision Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1); // Exit with a non-zero code to indicate failure
}