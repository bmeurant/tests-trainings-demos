/**
 * Main function to run all demonstrations for Interfaces.
 * This is the function imported and called by main.ts.
 */
export function demonstrateInterfaces(): void {
    console.log("=================================================");
    console.log("             STEP 5: INTERFACES                  ");
    console.log("=================================================\n");

    demonstrateBasicInterfaces();
    demonstrateOptionalAndReadonlyProperties();
    demonstrateFunctionInterfaces();

    console.log("--- END OF STEP 5 DEMONSTRATIONS ----------------\n");
}

/**
 * Demonstrates basic usage of interfaces for object types.
 * Interfaces define the shape that an object must have.
 */
function demonstrateBasicInterfaces(): void {
    console.log("--- Exploring Basic Interfaces ------------------");

    // Define a simple interface for a user object.
    // It specifies that any object implementing 'User' must have 'id' (number) and 'name' (string).
    interface User {
        id: number;
        name: string;
    }

    // Create an object that adheres to the 'User' interface.
    let user1: User = {
        id: 1,
        name: "Alice"
    };
    console.log(`User 1: ID - ${user1.id}, Name - ${user1.name}`);

    // Function that expects an object conforming to the 'User' interface.
    function printUser(user: User): void {
        console.log(`Printing User: ID=${user.id}, Name=${user.name}`);
    }
    printUser(user1);

    // 💡 EXPERIMENT: Uncomment to see compile-time errors.
    // TypeScript ensures the object matches the interface's shape.
    // let user2: User = { id: 2, name: "Bob", email: "bob@example.com" }; // Error: Object literal may only specify known properties.
    // let user3: User = { id: 3 }; // Error: Property 'name' is missing.
    // let user4: User = { id: "4", name: "Charlie" }; // Error: Type 'string' is not assignable to type 'number'.

    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates optional and readonly properties in interfaces.
 */
function demonstrateOptionalAndReadonlyProperties(): void {
    console.log("--- Exploring Optional & Readonly Properties ----");

    // Interface with optional and readonly properties.
    interface Product {
        readonly productId: string; // Cannot be changed after creation.
        name: string;
        price: number;
        description?: string;     // Optional property (may or may not be present).
    }

    function printProduct(product : Product): void {
        console.log(`Product: ID - ${product.productId}, Name - ${product.name}, Price - ${product.price},  Desc - ${product.description}`);
    }

    let product1: Product = {
        productId: "P001",
        name: "Laptop",
        price: 1200
    };

    printProduct(product1);

    let product2: Product = {
        productId: "P002",
        name: "Mouse",
        price: 25,
        description: "Wireless optical mouse."
    };

    printProduct(product2);

    // Update a mutable property.
    product1.price = 1150;
    printProduct(product1);

    // 💡 EXPERIMENT: Uncomment to see a compile-time error.
    // product1.productId = "P003"; // Error: Cannot assign to 'productId' because it is a read-only property.

    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates how interfaces can define function types.
 * This is useful for ensuring functions adhere to a specific signature.
 */
/**
 * Demonstrates how interfaces can define function types AND
 * introduces the recommended 'type' alias for pure function signatures.
 */
function demonstrateFunctionInterfaces(): void {
    console.log("--- Exploring Function Interfaces & Type Aliases for Functions ---");

    // Old way (valid, but less idiomatic if only a call signature)
    interface MathOperationOldWay {
         (x: number, y: number): number;
    }
    let addNumbersOld: MathOperationOldWay = function(a: number, b: number): number {
         return a + b;
    };
    console.log(`Add using old interface way: ${addNumbersOld(10, 5)}`);

    // RECOMMENDED WAY: Using a 'type' alias for a function signature.
    // This is clearer when the only purpose is to define a function's shape.
    type MathOperation = (x: number, y: number) => number;

    // Implement the type alias with a function expression.
    let addNumbers: MathOperation = function(a: number, b: number): number {
        return a + b;
    };
    console.log(`Add using type alias: ${addNumbers(10, 5)}`);

    // Implement the type alias with an arrow function.
    let subtractNumbers: MathOperation = (a, b) => a - b;
    console.log(`Subtract using type alias: ${subtractNumbers(10, 5)}`);

    // 💡 EXPERIMENT: Uncomment to see a compile-time error.
    // let multiplyNumbers: MathOperation = (a: string, b: number) => a.length * b; // Error: Type '(a: string, b: number) => number' is not assignable to type 'MathOperation'.

    // When an interface also has properties AND a call signature, it's still appropriate:
    interface DisposableFunction {
        (message: string): void;
        wasDisposed: boolean;
        dispose(): void;
    }

    const myDisposableFunc: DisposableFunction = Object.assign(
        (msg: string) => {
            console.log(`Disposable Func: ${msg}`);
            myDisposableFunc.wasDisposed = false;
        },
        {
            wasDisposed: false,
            dispose() {
                console.log("Disposable function disposed.");
                myDisposableFunc.wasDisposed = true;
            }
        }
    );

    myDisposableFunc("Hello from disposable func");
    console.log(`Is disposable func disposed? ${myDisposableFunc.wasDisposed}`);
    myDisposableFunc.dispose();
    console.log(`Is disposable func disposed? ${myDisposableFunc.wasDisposed}`);


    console.log("-------------------------------------------------\n");
}

demonstrateInterfaces();