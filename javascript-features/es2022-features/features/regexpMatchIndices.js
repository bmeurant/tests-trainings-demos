// es2022-features/features/regexpMatchIndices.js
const assert = require('assert');

function runRegExpMatchIndicesDemo() {
    console.log('--- ES2022: RegExp Match Indices (/d flag) ---');

    const text = 'Hello world, hello universe!';
    const regex = /hello\s(world|universe)/di; // 'd' flag for indices, 'i' for case-insensitive

    console.log(`\nText: "${text}"`);
    console.log(`Regex: ${regex}`);

    // --- 1. Using exec() with /d flag ---
    console.log('\n--- 1. Using exec() with /d flag ---');
    const match1 = regex.exec(text);

    if (match1) {
        console.log('Match found:');
        console.log(`  Full match: "${match1[0]}" at index ${match1.index}`);
        console.log(`  Capture group 1: "${match1[1]}"`);

        // Check for the 'indices' property
        if (match1.indices) {
            console.log('  Match indices available:');
            console.log(`    Full match indices: [${match1.indices[0][0]}, ${match1.indices[0][1]}]`);
            console.log(`    Capture group 1 indices: [${match1.indices[1][0]}, ${match1.indices[1][1]}]`);

            assert.strictEqual(match1.indices[0][0], 0, 'Assertion Failed: Full match start index incorrect.');
            assert.strictEqual(match1.indices[0][1], 11, 'Assertion Failed: Full match end index incorrect.');
            assert.strictEqual(match1.indices[1][0], 6, 'Assertion Failed: Group 1 start index incorrect.');
            assert.strictEqual(match1.indices[1][1], 11, 'Assertion Failed: Group 1 end index incorrect.');
            console.log('Assertion Passed: Match indices are correct.');
        } else {
            assert.fail('Assertion Failed: "indices" property missing from match object.');
        }
    } else {
        assert.fail('Assertion Failed: No match found.');
    }

    // --- 2. Using String.prototype.matchAll() with /d flag ---
    console.log('\n--- 2. Using String.prototype.matchAll() with /d flag ---');
    const regexAll = /hello\s(world|universe)/dig; // 'g' flag for all matches

    const allMatches = Array.from(text.matchAll(regexAll));

    console.log(`Found ${allMatches.length} matches:`);
    assert.strictEqual(allMatches.length, 2, 'Assertion Failed: matchAll count incorrect.');

    allMatches.forEach((match, i) => {
        console.log(`  Match ${i + 1}:`);
        console.log(`    Full match: "${match[0]}" at index ${match.index}`);
        console.log(`    Capture group 1: "${match[1]}"`);
        if (match.indices) {
            console.log(`    Full match indices: [${match.indices[0][0]}, ${match.indices[0][1]}]`);
            console.log(`    Capture group 1 indices: [${match.indices[1][0]}, ${match.indices[1][1]}]`);
        } else {
            assert.fail('Assertion Failed: "indices" property missing from matchAll result.');
        }
    });

    // Asserting specific indices for the second match
    assert.strictEqual(allMatches[1].indices[0][0], 13, 'Assertion Failed: Second full match start index incorrect.');
    assert.strictEqual(allMatches[1].indices[0][1], 27, 'Assertion Failed: Second full match end index incorrect.');
    assert.strictEqual(allMatches[1].indices[1][0], 19, 'Assertion Failed: Second group 1 start index incorrect.');
    assert.strictEqual(allMatches[1].indices[1][1], 27, 'Assertion Failed: Second group 1 end index incorrect.');
    console.log('Assertion Passed: Match indices with matchAll are correct.');

    console.log('\n--- All RegExp Match Indices demonstrations complete. ---');
}

try {
    runRegExpMatchIndicesDemo();
} catch (error) {
    console.error('\n!!! ES2022 RegExp Match Indices Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1);
}