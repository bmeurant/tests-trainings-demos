
/**
 * Main function to run all demonstrations for fundamental types.
 * This is the function imported and called by main.ts.
 */
export function demonstrateFundamentalTypes(): void {
    console.log("\n=================================================");
    console.log("             STEP 1: FUNDAMENTAL TYPES           ");
    console.log("=================================================\n");

    demonstrateNumberType();
    demonstrateStringType();
    demonstrateBooleanType();
    demonstrateAnyType();
    demonstrateVoidType();
    demonstrateNullUndefinedTypes();

    console.log("--------- END OF STEP 1 DEMONSTRATIONS ----------\n");
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

/**
 * Demonstrates the 'string' type in TypeScript, covering different declaration
 * methods (single/double quotes, template literals) and type safety.
 */
function demonstrateStringType(): void {
    console.log("--- Exploring 'string' Type ---------------------");

    // Using double quotes for string declaration.
    let productName: string = "Laptop Pro";
    console.log(`Product Name: ${productName}`);

    // Using single quotes; type is inferred as 'string'.
    let manufacturer = 'TechCorp';
    console.log(`Manufacturer: ${manufacturer}`);

    // Using backticks for template literals. These support multi-line strings
    // and embedding expressions using ${}.
    let description: string = `This is a high-performance
${productName} from ${manufacturer}.`;
    console.log(`Description:\n${description}`);

    // Standard string concatenation.
    let welcomeMessage = "Hello, " + "TypeScript!";
    console.log(`Concatenated string: ${welcomeMessage}`);

    // 💡 EXPERIMENT: Uncomment to see a compile-time error.
    // TypeScript ensures that only string values are assigned to string variables.
    // productName = 123; // Error: Type 'number' is not assignable to type 'string'.
    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates the 'boolean' type, showing how to declare boolean variables
 * and their use in conditional logic.
 */
function demonstrateBooleanType(): void {
    console.log("--- Exploring 'boolean' Type --------------------");

    // Explicitly declaring 'isAvailable' as a boolean.
    let isAvailable: boolean = true;
    console.log(`Is Product Available: ${isAvailable}`);

    // Type inference for boolean.
    let hasStock = false; // Inferred as 'boolean'
    console.log(`Has Stock: ${hasStock}`);

    // Booleans are fundamental for control flow.
    if (isAvailable && hasStock) {
        console.log("Product is ready for sale!");
    } else {
        console.log("Product is currently unavailable or out of stock.");
    }

    // 💡 EXPERIMENT: Uncomment to see a compile-time error.
    // TypeScript prevents assigning non-boolean values to a boolean variable.
    // isAvailable = 1; // Error: Type '1' is not assignable to type 'boolean'.
    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates the 'any' type, highlighting its flexibility and the
 * associated risks due to bypassing type checks.
 */
function demonstrateAnyType(): void {
    console.log("--- Exploring 'any' Type ------------------------");
    console.log("(Use with extreme caution! Bypasses type checking)");

    let unknownValue: any = "This can be a string";
    console.log(`Any type (string): ${unknownValue}`);

    unknownValue = 123.45; // No compile-time error!
    console.log(`Any type (number): ${unknownValue}`);

    unknownValue = { name: "Mystery", value: true }; // No compile-time error!
    console.log(`Any type (object):`, unknownValue);

    // With 'any', you can call any method. This can lead to runtime errors.
    // Example: This line will work if unknownValue is a string (like its initial value).
    // unknownValue.toUpperCase();

    // Example of a potential runtime error if the type isn't what's expected:
    // 💡 EXPERIMENT: Uncomment the lines below AFTER the object assignment to see a runtime error.
    unknownValue = { data: "some data" };
    console.log("Attempting to call .toUpperCase() on an object of type 'any':");
    try {
        unknownValue.toUpperCase(); // This will throw a runtime error: unknownValue.toUpperCase is not a function
    }
    catch (error) {
        if (error instanceof Error) {
            console.error("Runtime Error:", error.message);
        }
    }

    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates the 'void' type, used for functions that do not return a value.
 */
function demonstrateVoidType(): void {
    console.log("--- Exploring 'void' Type -----------------------");

    function logActivity(activity: string): void {
        console.log(`Activity logged: ${activity}`);
        // 💡 EXPERIMENT: Uncomment to see a compile-time error.
        // return "some value"; // Error: Type '"some value"' is not assignable to type 'void'.
    }
    logActivity("User logged in.");

    // Functions without an explicit return statement implicitly return 'void'.
    function performTask() {
        console.log("Performing a task...");
    }
    performTask();

    // Variables of type void are generally not useful, as they can only hold 'undefined' or 'null' (if strictNullChecks is off).
    let noReturn: void = undefined;
    // let noReturnNull: void = null; // Only valid if strictNullChecks is false in tsconfig.json
    console.log(`void variable:`, noReturn);

    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates the 'null' and 'undefined' types, highlighting their use
 * and the impact of 'strictNullChecks' in tsconfig.json.
 */
function demonstrateNullUndefinedTypes(): void {
    console.log("--- Exploring 'null' and 'undefined' Types -------");
    console.log("(Impacted by 'strictNullChecks' in tsconfig.json)");

    // By default, with 'strictNullChecks: true', variables must explicitly allow null/undefined.
    let userName: string | null = "Jane Doe"; // Union type: can be string OR null
    console.log(`User Name (initial): ${userName}`);
    userName = null; // Valid assignment due to union type
    // userName = undefined; // Error: Type 'undefined' is not assignable to type 'string' | `null`.
    console.log(`User Name after null assignment: ${userName}`);

    let userAge: number | undefined; // Union type: can be number OR undefined
    console.log(`User Age (initial, undefined): ${userAge}`);
    userAge = 25;
    console.log(`User Age after assignment: ${userAge}`);
    userAge = undefined; // Valid assignment
    // userAge = null; // Error: Type 'undefined' is not assignable to type 'string' | `undefined`.
    console.log(`User Age after undefined assignment: ${userAge}`);

    let userGender: string | null | undefined = 'M'; // Union type: can be number OR undefined OR null
    console.log(`User Gender (initial): ${userGender}`);
    userGender = null; // Valid assignment
    console.log(`User Gender after null assignment: ${userGender}`);
    userGender = undefined; // Valid assignment
    console.log(`User Gender after undefined assignment: ${userGender}`);

    // 💡 EXPERIMENT: Uncomment the lines below to see compile-time errors.
    // With 'strictNullChecks: true', you cannot assign null/undefined to a
    // variable unless its type explicitly includes null/undefined.
    // let score: number = null; // Error: Type 'null' is not assignable to type 'number'.
    // let descriptionText: string = undefined; // Error: Type 'undefined' is not assignable to type 'string'.

    let email: string | null = null;
    console.log(`User Email: ${email}`);

    console.log("-------------------------------------------------\n");
}