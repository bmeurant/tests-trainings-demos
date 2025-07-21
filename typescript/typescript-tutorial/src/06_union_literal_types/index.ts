/**
 * Main function to run all demonstrations for Union and Literal Types.
 * This is the function imported and called by main.ts.
 */
export function demonstrateUnionAndLiteralTypes(): void {
    console.log("=================================================");
    console.log("      STEP 6: UNION TYPES AND LITERAL TYPES      ");
    console.log("        (Including 'const assertion')            ");
    console.log("=================================================\n");

    demonstrateUnionTypes();

    console.log("--- END OF STEP 6 DEMONSTRATIONS ----------------\n");
}

/**
 * Demonstrates Union Types.
 * A union type describes a value that can be one of several types.
 * We use the vertical bar `|` to separate each type.
 */
function demonstrateUnionTypes(): void {
    console.log("--- Exploring Union Types -----------------------");

    // A variable that can be either a string OR a number.
    let userId: string | number;

    userId = "abc-123"; // Valid: string
    console.log(`User ID (string): ${userId}`);

    userId = 456789; // Valid: number
    console.log(`User ID (number): ${userId}`);

    // 💡 EXPERIMENT: Uncomment to see a compile-time error.
    // userId = true; // Error: Type 'boolean' is not assignable to type 'string | number'.

    // Union types in function parameters.
    function printId(id: string | number): void {
        console.log(`The ID is: ${id}`);
        // TypeScript narrows the type inside conditional blocks using typeof.
        if (typeof id === "string") {
            console.log(`  ID is a string, length: ${id.length}`);
        } else {
            console.log(`  ID is a number, value: ${id.toFixed(2)}`);
        }
    }
    printId("my-uuid-001");
    printId(123.456);

    // An array that can contain both numbers and booleans.
    let mixedData: (number | boolean)[] = [1, true, 2, false];
    console.log(`Mixed data array: ${mixedData}`);

    console.log("-------------------------------------------------\n");
}

demonstrateUnionAndLiteralTypes();