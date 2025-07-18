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

demonstrateFunctions();