#!/usr/bin/env node
// es2023-features/features/hashbangGrammar.js
const assert = require('assert');

function runHashbangGrammarDemo() {
    console.log('--- ES2023: Hashbang Grammar (#!) ---');
    console.log('  This script demonstrates the hashbang line at the very top.');
    console.log('  On Unix-like systems, if this file has execute permissions,');
    console.log('  it can be run directly like: ./hashbangGrammar.js');

    const message = 'Hashbang grammar successfully parsed and script executed.';
    console.log(`  ${message}`);
    assert.strictEqual(message, 'Hashbang grammar successfully parsed and script executed.', 'Assertion Failed: Hashbang demo message incorrect.');
    console.log('Assertion Passed: The script executed without a SyntaxError due to the hashbang.');

    // We can also check process arguments to see if it was executed directly or via `node`
    // This is more of an OS/Node.js detail, but relevant for hashbang context.
    const executablePath = process.argv[0];
    const scriptPath = process.argv[1];

    // On systems where hashbang works, argv[0] might be the script itself if run directly
    // or 'node' if run with `node script.js`.
    // The key here is that if it was invoked via hashbang, Node.js still manages it,
    // but the initial execution mechanism changes.
    // We are primarily testing that the hashbang line itself doesn't cause a syntax error.
    console.log(`  Process executable: ${executablePath}`);
    console.log(`  Script being executed: ${scriptPath}`);

    console.log('\n--- All Hashbang Grammar demonstrations complete. ---');
}

try {
    runHashbangGrammarDemo();
} catch (error) {
    console.error('\n!!! ES2023 Hashbang Grammar Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}