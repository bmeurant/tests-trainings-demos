/**
 * Main function to run all demonstrations for Enums and Objects.
 * This is the function imported and called by main.ts.
 */
export function demonstrateEnumsAndObjects(): void {
    console.log("=================================================");
    console.log("             STEP 3: ENUMS AND OBJECTS           ");
    console.log("=================================================\n");

    demonstrateNumericEnums();
    demonstrateStringEnums();
    demonstrateObjectTypes();

    console.log("--- END OF STEP 3 DEMONSTRATIONS ----------------\n");
}

/**
 * Demonstrates Numeric Enums.
 * By default, enums are number-based, starting at 0.
 * They provide a way to define a set of named constants.
 */
function demonstrateNumericEnums(): void {
    console.log("--- Exploring Numeric Enums ---------------------");

    // Define a numeric enum for weekdays with automatic assignment of values.
    // Monday = 0, Tuesday = 1, etc.
    enum Weekday {
        Monday,
        Tuesday,
        Wednesday,
        Thursday,
        Friday,
        Saturday,
        Sunday
    }

    let today: Weekday = Weekday.Wednesday;
    console.log(`Today is: ${Weekday[today]} (value: ${today})`);

    // You can also assign explicit numeric values.
    enum HttpStatusCode {
        OK = 200,
        BadRequest = 400,
        Unauthorized = 401,
        NotFound = 404,
        InternalServerError = 500
    }

    let responseStatus: HttpStatusCode = HttpStatusCode.OK;
    console.log(`HTTP Status: ${HttpStatusCode[responseStatus]} (code: ${responseStatus})`);

    let errorStatus: HttpStatusCode = HttpStatusCode.NotFound;
    console.log(`Error Status: ${HttpStatusCode[errorStatus]} (code: ${errorStatus})`);

    // 💡 EXPERIMENT: Try assigning a number that is not explicitly part of the enum.
    // TypeScript allows this, which can be a trap! Use string enums for better safety.
    // let unknownStatus: HttpStatusCode = 999; // This is technically allowed in numeric enums at compile time

    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates String Enums.
 * String enums are generally preferred as they offer better readability and
 * prevent common runtime bugs by not allowing arbitrary numbers.
 */
function demonstrateStringEnums(): void {
    console.log("--- Exploring String Enums ----------------------");

    // Define a string enum for user roles.
    enum UserRole {
        Admin = "ADMIN",
        Editor = "EDITOR",
        Viewer = "VIEWER"
    }

    let currentUserRole: UserRole = UserRole.Admin;
    console.log(`Current user role: ${currentUserRole}`);

    // String enums provide better compile-time safety.
    function checkAccess(role: UserRole): void {
        if (role === UserRole.Admin) {
            console.log("Admin access granted.");
        } else if (role === UserRole.Editor) {
            console.log("Editor access granted.");
        } else {
            console.log("Viewer access granted.");
        }
    }
    checkAccess(UserRole.Editor);

    // 💡 EXPERIMENT: Uncomment the line below to see a compile-time error.
    // You cannot assign an arbitrary string value to a string enum type.
    // let invalidRole: UserRole = "VIEWER"; // Error: Type '"VIEWER"' is not assignable to type 'UserRole'.

    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates how to define and use objects with explicit types.
 * This ensures that objects conform to a predefined structure.
 */
function demonstrateObjectTypes(): void {
    console.log("--- Exploring Object Types ----------------------");

    // Define an object type using an inline type annotation.
    // This object must have a 'name' (string) and an 'age' (number).
    let person: { name: string; age: number };

    // Assign a value that matches the defined type.
    person = { name: "John Doe", age: 30 };
    console.log(`Person object: Name - ${person.name}, Age - ${person.age}`);

    // 💡 EXPERIMENT: Uncomment to see compile-time errors.
    // Type checking prevents incorrect assignments:
    // person = { name: "Jane" }; // Error: Property 'age' is missing.
    // person = { name: "Bob", age: "twenty" }; // Error: Type 'string' is not assignable to type 'number'.
    // person = { name: "Alice", age: 25, email: "alice@example.com" }; // Error: Object literal may only specify known properties.

    // Using an optional property (with '?')
    let car: { make: string; model: string; year?: number };
    car = { make: "Toyota", model: "Camry" }; // 'year' is optional
    console.log(`Car 1: ${car.make} ${car.model} (${car.year})`);
    car = { make: "Honda", model: "Civic", year: 2020 }; // 'year' is present
    console.log(`Car 2: ${car.make} ${car.model} (${car.year})`);

    // Using a 'readonly' property.
    // The property can only be assigned during initialization.
    let product: { readonly id: number; name: string; price: number };
    product = { id: 101, name: "Keyboard", price: 75.00 };
    console.log(`Product: ID - ${product.id}, Name - ${product.name}, Price - ${product.price}`);
    product.price = 65.00; // Valid: 'price' is not readonly
    console.log(`Product: ID - ${product.id}, Name - ${product.name}, Price - ${product.price}`);

    // 💡 EXPERIMENT: Uncomment to see a compile-time error.
    // product.id = 102; // Error: Cannot assign to 'id' because it is a read-only property.

    console.log("-------------------------------------------------\n");
}

demonstrateEnumsAndObjects();