/**
 * Main function to run all demonstrations for Functions.
 * This is the function imported and called by main.ts.
 */
export function demonstrateFunctions(): void {
    console.log("=================================================");
    console.log("             STEP 4: FUNCTIONS                   ");
    console.log("=================================================\n");

    demonstrateBasicFunctions();
    demonstrateOptionalAndDefaultParameters();
    demonstrateArrowFunctions();
    demonstrateFunctionOverloads();

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

/**
 * Demonstrates optional and default parameters in functions.
 */
function demonstrateOptionalAndDefaultParameters(): void {
    console.log("--- Exploring Optional and Default Parameters ---");

    // Optional parameter: 'lastName' might or might not be provided.
    // Marked with '?' after the parameter name.
    function buildFullName(firstName: string, lastName?: string): string {
        if (lastName) {
            return `${firstName} ${lastName}`;
        }
        return firstName;
    }
    console.log(`Full Name 1: ${buildFullName("John", "Doe")}`);
    console.log(`Full Name 2: ${buildFullName("Jane")}`);

    // Default parameter: 'greeting' defaults to "Hello" if not provided.
    // Declared with an assignment operator '=' after the type.
    function sayHello(name: string, greeting: string = "Hello"): string {
        return `${greeting}, ${name}!`;
    }
    console.log(`Greeting 1: ${sayHello("Bob")}`);
    console.log(`Greeting 2: ${sayHello("Charlie", "Hi")}`);

    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates Arrow Functions (Fat Arrow Functions) with TypeScript.
 * They provide a more concise syntax and lexical 'this' binding.
 */
function demonstrateArrowFunctions(): void {
    console.log("--- Exploring Arrow Functions -------------------");

    // Basic arrow function with explicit types
    const multiply = (a: number, b: number): number => {
        return a * b;
    };
    console.log(`multiply(4, 2) = ${multiply(4, 2)}`);

    // Concise body arrow function (implicit return)
    const subtract = (a: number, b: number): number => a - b;
    console.log(`subtract(10, 3) = ${subtract(10, 3)}`);

    // Arrow function with no parameters
    const getRandomNumber = (): number => Math.random();
    console.log(`Random number: ${getRandomNumber()}`);

    // Arrow function with no return value (void)
    const logInfo = (info: string): void => {
        console.log(`INFO: ${info}`);
    };
    logInfo("Using an arrow function for logging.");

    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates Function Overloads.
 * Allows defining multiple function signatures for a single function
 * to handle different input types or numbers of arguments.
 */
function demonstrateFunctionOverloads(): void {
    console.log("--- Exploring Function Overloads ----------------");

    // Overload signatures (no implementation)
    function combine(a: number, b: number): number;
    function combine(a: string, b: string): string;
    function combine(a: (number | string), b: (number | string)): (number | string) {
        if (typeof a === 'number' && typeof b === 'number') {
            return a + b;
        }
        if (typeof a === 'string' && typeof b === 'string') {
            return a + b;
        }
        throw new Error("Parameters must both be numbers or both be strings.");
    }

    console.log(`combine(10, 20) = ${combine(10, 20)}`);
    console.log(`combine("Hello ", "World") = ${combine("Hello ", "World")}`);

    // 💡 EXPERIMENT: Uncomment the line below to see a compile-time error.
    // combine(10, "world"); // Error: No overload matches this call.

    // Another overload example: calculateArea for square or rectangle
    function calculateArea(side: number): number;
    function calculateArea(length: number, width: number): number;
    function calculateArea(arg1: number, arg2?: number): number {
        if (arg2 !== undefined) {
            return arg1 * arg2; // Rectangle
        }
        return arg1 * arg1; // Square
    }

    console.log(`Area of square (side 5): ${calculateArea(5)}`);
    console.log(`Area of rectangle (5x10): ${calculateArea(5, 10)}`);

    console.log("-------------------------------------------------\n");
}

demonstrateFunctions();