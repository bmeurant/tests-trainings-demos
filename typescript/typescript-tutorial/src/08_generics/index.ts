/**
 * Main function to run all demonstrations for Generics.
 * This is the function imported and called by main.ts.
 */
export function demonstrateGenerics(): void {
    console.log("=================================================");
    console.log("              STEP 8: GENERICS                   ");
    console.log("=================================================\n");

    demonstrateGenericFunctions();

    console.log("--- END OF STEP 8 DEMONSTRATIONS ----------------\n");
}

/**
 * Demonstrates basic Generic Functions.
 * Generic functions work with a variety of types while maintaining type safety.
 * The type variable (e.g., <T>) allows us to capture the type of the argument
 * so we can use it to describe the return type or other parts of the function.
 */
function demonstrateGenericFunctions(): void {
    console.log("--- Exploring Generic Functions -----------------");

    // A simple identity function that returns whatever is passed into it.
    // Without generics, the type would be 'any', losing type information.
    // With generics, 'T' captures the input type and ensures the output type matches.
    function identity<T>(arg: T): T {
        return arg;
    }

    // Explicitly specifying the type argument
    let output1 = identity<string>("myString");
    console.log(`  Generic function with string: ${output1} (Type: ${typeof output1})`);

    // Type argument inference (TypeScript automatically figures out the type)
    let output2 = identity(123);
    console.log(`  Generic function with number: ${output2} (Type: ${typeof output2})`);

    let output3 = identity(true);
    console.log(`  Generic function with boolean: ${output3} (Type: ${typeof output3})`);

    // A generic function to get the first element of an array.
    function getFirstElement<Type>(arr: Type[]): Type | undefined {
        return arr.length > 0 ? arr[0] : undefined;
    }

    let numberArray = [10, 20, 30];
    let firstNum = getFirstElement(numberArray); // Type inferred as number
    console.log(`  First number in array: ${firstNum}`);

    let stringArray = ["apple", "banana", "cherry"];
    let firstString = getFirstElement(stringArray); // Type inferred as string
    console.log(`  First string in array: ${firstString}`);

    let emptyArray: number[] = [];
    let firstUndefined = getFirstElement(emptyArray); // Type inferred as number | undefined
    console.log(`  First element of empty array: ${firstUndefined}`);

    console.log("-------------------------------------------------\n");
}

demonstrateGenerics();