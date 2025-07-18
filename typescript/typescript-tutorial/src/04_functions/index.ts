/**
 * Main function to run all demonstrations for Functions.
 * This is the function imported and called by main.ts.
 */
export function demonstrateFunctions(): void {
    console.log("=================================================");
    console.log("             STEP 4: FUNCTIONS                   ");
    console.log("=================================================\n");

    demonstrateBasicFunctions();

    console.log("--- END OF STEP 4 DEMONSTRATIONS ----------------\n");
}

/**
 * Demonstrates basic function types: typed parameters and return values.
 */
function demonstrateBasicFunctions(): void {
    console.log("--- Exploring Basic Functions -------------------");

    // Function with typed parameters and explicit return type
    function add(a: number, b: number): number {
        return a + b;
    }

    let result = add(5, 3);
    console.log(`add(5, 3) = ${result}`);

    // 💡 EXPERIMENT: Uncomment the line below to see a compile-time error.
    // add("hello", 3); // Error: Argument of type '"hello"' is not assignable to parameter of type 'number'.

    function greet(name: string): string {
        return `Hello, ${name}!`;
    }

    console.log(greet("Alice"));

    // Function with void return type (does not return a value)
    function logMessage(message: string): void {
        console.log(`LOG: ${message}`);
    }
    logMessage("This is a log entry.");

    console.log("-------------------------------------------------\n");
}

demonstrateFunctions();