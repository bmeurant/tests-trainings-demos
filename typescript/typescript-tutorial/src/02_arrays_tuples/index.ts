/**
 * Main function to run all demonstrations for Arrays and Tuples.
 * This is the function imported and called by main.ts.
 */
export function demonstrateArraysAndTuples(): void {
    console.log("=================================================");
    console.log("             STEP 2: ARRAYS AND TUPLES           ");
    console.log("=================================================\n");

    demonstrateArrayType();
    demonstrateTupleType();

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

/**
 * Demonstrates the 'Tuple' type in TypeScript.
 * Tuples are arrays with a fixed number of elements, where each element
 * can have a different, predefined type.
 */
function demonstrateTupleType(): void {
    console.log("--- Exploring 'Tuple' Type ----------------------");

    // Declares a tuple 'personInfo'.
    // It must contain exactly two elements: a 'string' followed by a 'number'.
    let personInfo: [string, number] = ["Alice", 30];
    console.log(`Person Info Tuple: Name - ${personInfo[0]}, Age - ${personInfo[1]}`);

    // Accessing elements by index
    const personName = personInfo[0]; // Type is 'string'
    const personAge = personInfo[1];   // Type is 'number'
    console.log(`Accessed by index: ${personName} is ${personAge} years old.`);

    // 💡 EXPERIMENT: Uncomment the line below to see a compile-time error.
    // Attempting to assign elements with incorrect types.
    // personInfo = [30, "Alice"]; // Error: Type 'number' is not assignable to type 'string' for element 0.

    // 💡 EXPERIMENT: Uncomment the line below to see a compile-time error.
    // Attempting to assign a tuple with an incorrect number of elements.
    // personInfo = ["Bob", 25, true]; // Error: Source has 3 elements, but target allows only 2.

    // Tuples are useful for functions returning multiple distinct values.
    function getUserStatus(userId: number): [string, boolean] {
        if (userId === 101) {
            return ["Active", true];
        }
        return ["Inactive", false];
    }

    let [statusText, isActiveUser] = getUserStatus(101);
    console.log(`User Status: ${statusText}, Is Active: ${isActiveUser}`);

    let [statusText2, isActiveUser2] = getUserStatus(200);
    console.log(`User Status 2: ${statusText2}, Is Active 2: ${isActiveUser2}`);

    // A tuple with three elements: a number, a string, and a boolean.
    let productDetails: [number, string, boolean] = [101, "Widget", true];
    console.log(`Product Details: ID=${productDetails[0]}, Name=${productDetails[1]}, InStock=${productDetails[2]}`);


    // Note: While you can push elements to a tuple (if its type definition allows, like [string, number?]),
    // it's generally discouraged as it defeats the purpose of a fixed-size, fixed-type structure.
    // TypeScript allows pushing elements of a union type of the tuple's members.
    // personInfo.push("extra item"); // This actually IS allowed if 'string | number' is a valid type for the elements of the tuple.
    // However, trying to access personInfo[2] would be a compile-time error.
    // This is a known nuance in TS, where push/pop on tuples might not fully enforce length.
    // It's best to treat tuples as immutable for their defined length.

    console.log("-------------------------------------------------\n");
}