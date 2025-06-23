const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');

const featuresDir = path.join(__dirname, './features'); // Path to the 'features' directory

console.log(`\n======================================================`);
console.log(` Starting ES2020 Features Demonstrations `);
console.log(`======================================================\n`);

try {
    const featureFiles = fs.readdirSync(featuresDir)
        .filter(file => file.endsWith('.js') && !file.endsWith('.test.js')) // Ensure it's a JS file and not a test file if any were left
        .sort(); // Sort to ensure consistent order

    if (featureFiles.length === 0) {
        console.warn('No feature files found in the "features" directory.');
        process.exit(0);
    }

    for (const file of featureFiles) {
        const filePath = path.join(featuresDir, file);
        console.log(`\n--- Running demonstration for: ${file} ---`);
        try {
            // Execute the feature file directly using node
            execSync(`node ${filePath}`, { stdio: 'inherit' });
            console.log(`--- Successfully ran: ${file} ---\n`);
        } catch (error) {
            console.error(`!!! Error running ${file}:`);
            // The error output is already streamed to 'inherit', so we just log a summary
            console.error(`!!! Demo failed for ${file} with exit code ${error.status}`);
            process.exit(1); // Exit with error if any demo fails
        }
    }
    console.log(`\n======================================================`);
    console.log(` All ES2020 Features Demonstrations Completed Successfully!`);
    console.log(`======================================================\n`);

} catch (error) {
    console.error(`\n!!! Failed to read features directory or execute scripts: ${error.message}`);
    process.exit(1);
}