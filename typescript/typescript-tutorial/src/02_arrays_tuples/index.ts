/**
 * Main function to run all demonstrations for Arrays and Tuples.
 * This is the function imported and called by main.ts.
 */
export function demonstrateArraysAndTuples(): void {
    console.log("=================================================");
    console.log("             STEP 2: ARRAYS AND TUPLES           ");
    console.log("=================================================\n");

    demonstrateArrayType();

    console.log("--- END OF STEP 2 DEMONSTRATIONS ----------------\n");
}

/**
 * Demonstrates the 'Array' type in TypeScript.
 * Arrays allow storing collections of elements of the same type.
 */
function demonstrateArrayType(): void {
    console.log("--- Exploring 'Array' Type ---");

    // Method 1: Using square brackets (most common and recommended)
    // Declares an array named 'numbers' that can only contain 'number' types.
    let numbers: number[] = [10, 20, 30, 40, 50];
    console.log(`Numbers array: ${numbers}`);
    console.log(`First element: ${numbers[0]}`);
    console.log(`Array length: ${numbers.length}`);

    // Adding an element to the array. TypeScript ensures type compatibility.
    numbers.push(60);
    console.log(`Numbers after push(60): ${numbers}`);

    // 💡 EXPERIMENT: Uncomment the line below to see a compile-time error.
    // TypeScript prevents adding an element of an incorrect type.
    // numbers.push("seventy"); // Error: Argument of type '"seventy"' is not assignable to parameter of type 'number'.

    // Iterating over an array
    console.log("Iterating over numbers (for):");
    for (let i = 0; i < numbers.length; i++) {
        console.log(`Index: ${i}, Value: ${numbers[i]}`);
    }

    console.log("Iterating over numbers (for...of):");
    for (const num of numbers) {
        console.log(`- ${num}`);
    }

    // Iterating over an array with indexes using entries
    console.log("Iterating over numbers (for...of) with indexes:");
    for (const [index, num] of numbers.entries()) {
        console.log(`Index: ${index}, Value: ${num}`);
    }

    // Method 2: Using the Array<T> generic type
    // This is equivalent to number[] but can be less readable for simple cases.
    let names: Array<string> = ["Alice", "Bob", "Charlie"];
    console.log(`Names array: ${names}`);
    console.log("Iterating over names (forEach):");
    names.forEach(name => console.log(`Hello, ${name}!`));
    // with indexes
    console.log("Iterating over names (forEach) with indexes:");
    names.forEach((name, index) => console.log(`- Index: ${index}, Value: ${name}`));

    // An array of booleans
    let statuses: boolean[] = [true, false, true];
    console.log(`Statuses array: ${statuses}`);

    // An array that can contain multiple types (using Union Types, covered more later!)
    let mixedArray: (string | number)[] = ["apple", 123, "banana", 456];
    console.log(`Mixed array: ${mixedArray}`);

    console.log("-------------------------------------------------\n");
}