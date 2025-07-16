
/**
 * Main function to run all demonstrations for fundamental types.
 * This is the function imported and called by main.ts.
 */
export function demonstrateFundamentalTypes(): void {
    console.log("\n=================================================");
    console.log("             STEP 1: FUNDAMENTAL TYPES           ");
    console.log("=================================================\n");

    demonstrateNumberType();

    console.log("\n--------- END OF STEP 1 DEMONSTRATIONS ----------\n");
}

/**
 * Demonstrates the 'number' type in TypeScript, including explicit declaration,
 * type inference, and compile-time error prevention.
 */
function demonstrateNumberType(): void {
    console.log("--- Exploring 'number' Type ---------------------");

    // Explicitly declaring 'age' as a number.
    // This provides clarity and helps TypeScript enforce type safety.
    let age: number = 30;
    console.log(`Declared number: age = ${age}`);

    // TypeScript can infer the type. Here, 'price' is inferred as 'number'
    // based on the assigned value. This is useful for conciseness.
    let price = 19.99;
    console.log(`Inferred number: price = ${price}`);

    // 💡 EXPERIMENT: Uncomment the line below to see a compile-time error.
    // TypeScript prevents assigning a string to a number variable.
    // age = "thirty"; // Error: Type '"thirty"' is not assignable to type 'number'.

    // 💡 EXPERIMENT: Uncomment the line below to see a compile-time error.
    // As price has been inferred as numer, TypeScript prevents assigning a string to a number variable.
    // price = "thirty"; // Error: Type '"thirty"' is not assignable to type 'number'.

    // Mathematical operations work as expected with numeric types.
    let sum = age + price;
    console.log(`Sum of age and price: ${sum}`);
    console.log("-------------------------------------------------\n");
}