// es2018-features/features/regExpNamedCapture.js
const assert = require('assert');

function runRegExpNamedCaptureDemo() {
    console.log('--- ES2018: RegExp Named Capture Groups ---');

    // --- 1. Parsing a date string ---
    console.log('\n--- Parsing Date with Named Capture Groups ---');
    const dateString = 'The event is scheduled for 2024-07-25 at 14:30.';
    console.log('Original String:', dateString);

    // Regex with named capture groups: ?<year>, ?<month>, ?<day>
    const dateRegex = /(?<year>\d{4})-(?<month>\d{2})-(?<day>\d{2})/;
    const match = dateRegex.exec(dateString);

    if (match) {
        console.log('Full match:', match[0]);
        console.log('Captured groups (numeric):', match[1], match[2], match[3]);
        console.log('Captured groups (named):', match.groups);

        const { year, month, day } = match.groups;
        console.log(`  Year: ${year}, Month: ${month}, Day: ${day}`);

        assert.strictEqual(year, '2024', 'Assertion Failed: Year incorrect');
        assert.strictEqual(month, '07', 'Assertion Failed: Month incorrect');
        assert.strictEqual(day, '25', 'Assertion Failed: Day incorrect');
        console.log('Assertion Passed: Date components correctly extracted by name.');
    } else {
        console.log('No date match found.');
        assert.fail('Assertion Failed: Date should have been matched');
    }

    const invalidDateString = 'No date here.';
    console.log('\nTrying with an invalid date string:', invalidDateString);
    const noMatch = dateRegex.exec(invalidDateString);
    assert.strictEqual(noMatch, null, 'Assertion Failed: Should not match invalid date');
    console.log('Assertion Passed: Correctly did not match invalid date.');

    // --- 2. Using named groups in String.prototype.replace() ---
    console.log('\n--- Reordering Name using Named Capture Groups in replace() ---');
    const fullName = 'Smith, John';
    console.log('Original Name:', fullName);

    // Regex with named capture groups for last and first name
    const nameRegex = /(?<lastName>[^,]+),\s*(?<firstName>.+)/;
    // In the replacement string, you can reference named groups using $<name>
    const reorderedName = fullName.replace(nameRegex, '$<firstName> $<lastName>');
    console.log('Reordered Name:', reorderedName);

    assert.strictEqual(reorderedName, 'John Smith', 'Assertion Failed: Name reordering incorrect');
    console.log('Assertion Passed: Name reordered correctly using named groups in replace().');

    const noMatchName = 'Jane Doe'; // No comma, won't match regex
    console.log('\nTrying with name without comma:', noMatchName);
    const unchangedName = noMatchName.replace(nameRegex, '$<firstName> $<lastName>');
    console.log('Unchanged Name:', unchangedName);
    assert.strictEqual(unchangedName, 'Jane Doe', 'Assertion Failed: Name should be unchanged');
    console.log('Assertion Passed: String remains unchanged if regex does not match.');

    console.log('\n--- All RegExp Named Capture Groups demonstrations complete. ---');
}

try {
    runRegExpNamedCaptureDemo();
} catch (error) {
    console.error('\n!!! ES2018 RegExp Named Capture Demo Failed !!!');
    console.error('Error details:', error.message);
    process.exit(1); // Exit with a non-zero code to indicate failure
}