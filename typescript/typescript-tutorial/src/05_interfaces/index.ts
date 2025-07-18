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

demonstrateInterfaces();