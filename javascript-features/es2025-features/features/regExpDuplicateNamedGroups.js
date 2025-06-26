// es2025-features/features/regExpDuplicateNamedGroups.js

// Using CommonJS require for the assert module
const assert = require('assert');

function runDuplicateNamedCaptureGroupsDemo() {
    console.log('\n--- ES2025: Duplicate Named Capture Groups (CommonJS Demo) ---');

    // --- 1. Matching different date formats with the same group name ---
    console.log('\n--- 1. Capturing different date formats ---');
    // This regex matches dates in YYYY-MM-DD or MM/DD/YYYY format.
    // Both patterns use the same group name 'date'.
    const dateRegex = /^(?<date>\d{4}-\d{2}-\d{2})|(?<date>\d{2}\/\d{2}\/\d{4})$/;

    const match1 = "2023-10-26".match(dateRegex);
    console.log(`Text: "2023-10-26"`);
    console.log(`Match: ${JSON.stringify(match1.groups)}`);
    assert.strictEqual(match1.groups.date, "2023-10-26", 'Assertion failed: match1.groups.date should be "2023-10-26"');
    console.log('Assertion passed: YYYY-MM-DD format captured correctly.');

    const match2 = "10/26/2023".match(dateRegex);
    console.log(`Text: "10/26/2023"`);
    console.log(`Match: ${JSON.stringify(match2.groups)}`);
    assert.strictEqual(match2.groups.date, "10/26/2023", 'Assertion failed: match2.groups.date should be "10/26/2023"');
    console.log('Assertion passed: MM/DD/YYYY format captured correctly.');

    const match3 = "Invalid Date".match(dateRegex);
    console.log(`Text: "Invalid Date"`);
    console.log(`Match: ${JSON.stringify(match3 && match3.groups)}`);
    assert.strictEqual(match3, null, 'Assertion failed: "Invalid Date" should not match.');
    console.log('Assertion passed: Non-matching text handled correctly.');

    // --- 2. Matching different ID formats with the same group name ---
    console.log('\n--- 2. Capturing different ID formats ---');
    // This regex matches IDs that can be either numeric (e.g., "12345")
    // or alphanumeric (e.g., "ABC-001"). Both use the group name 'id'.
    const idRegex = /(?:ID-(?<id>\d{5}))|(?:REF-(?<id>[A-Z]{3}-\d{3}))/;

    const match4 = "User ID-54321 Data".match(idRegex);
    console.log(`Text: "User ID-54321 Data"`);
    console.log(`Match: ${JSON.stringify(match4.groups)}`);
    assert.strictEqual(match4.groups.id, "54321", 'Assertion failed: match4.groups.id should be "54321"');
    console.log('Assertion passed: Numeric ID format captured correctly.');

    const match5 = "Reference REF-XYZ-789 Value".match(idRegex);
    console.log(`Text: "Reference REF-XYZ-789 Value"`);
    console.log(`Match: ${JSON.stringify(match5.groups)}`);
    assert.strictEqual(match5.groups.id, "XYZ-789", 'Assertion failed: match5.groups.id should be "XYZ-789"');
    console.log('Assertion passed: Alphanumeric ID format captured correctly.');

    const match6 = "Unknown ID".match(idRegex);
    console.log(`Text: "Unknown ID"`);
    console.log(`Match: ${JSON.stringify(match6 && match6.groups)}`);
    assert.strictEqual(match6, null, 'Assertion failed: "Unknown ID" should not match.');
    console.log('Assertion passed: Non-matching ID text handled correctly.');


    console.log('\n--- All Duplicate Named Capture Groups demonstrations complete. ---');
}

// Execute the demo function
runDuplicateNamedCaptureGroupsDemo();