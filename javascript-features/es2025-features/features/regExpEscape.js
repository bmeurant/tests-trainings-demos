// es2025-features/features/regExpEscape.js
const assert = require('assert');

function runRegExpEscapeDemo() {
    console.log('--- ES2024: RegExp.escape() ---');

    const unsafeString = 'user.name+*'; // User input with special regex chars
    const textToSearch = 'Finding user.name+* in this text. Another user.name+* is here.';

    // Using escaped string in a RegExp
    console.log('\n--- Using escaped string in a RegExp ---');
    const safeRegex = new RegExp(RegExp.escape(unsafeString), 'g');
    console.log(`  Searching for "${unsafeString}" (escaped) in "${textToSearch}"`);

    const safeMatches = textToSearch.match(safeRegex);
    console.log(`  Safe regex matches: [${safeMatches ? safeMatches.map(m => `"${m}"`).join(', ') : 'No matches'}]`);
    assert.deepStrictEqual(safeMatches, ['user.name+*', 'user.name+*'], 'Assertion Failed: Safe regex matches incorrect.');
    console.log('Assertion Passed: RegExp.escape() enables safe dynamic regex creation.');

    console.log('\n--- All RegExp.escape() demonstrations complete. ---');
}

try {
    runRegExpEscapeDemo();
} catch (error) {
    console.error('\n!!! ES2024 RegExp.escape() Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}