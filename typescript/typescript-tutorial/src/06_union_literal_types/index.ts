/**
 * Main function to run all demonstrations for Union and Literal Types.
 * This is the function imported and called by main.ts.
 */
export function demonstrateUnionAndLiteralTypes(): void {
    console.log("=================================================");
    console.log("      STEP 6: UNION TYPES AND LITERAL TYPES      ");
    console.log("        (Including 'const assertion')            ");
    console.log("=================================================\n");

    demonstrateUnionTypes();
    demonstrateLiteralTypes();
    demonstrateDiscriminatedUnions();

    console.log("--- END OF STEP 6 DEMONSTRATIONS ----------------\n");
}

/**
 * Demonstrates Union Types.
 * A union type describes a value that can be one of several types.
 * We use the vertical bar `|` to separate each type.
 */
function demonstrateUnionTypes(): void {
    console.log("--- Exploring Union Types -----------------------");

    // A variable that can be either a string OR a number.
    let userId: string | number;

    userId = "abc-123"; // Valid: string
    console.log(`User ID (string): ${userId}`);

    userId = 456789; // Valid: number
    console.log(`User ID (number): ${userId}`);

    // 💡 EXPERIMENT: Uncomment to see a compile-time error.
    // userId = true; // Error: Type 'boolean' is not assignable to type 'string | number'.

    // Union types in function parameters.
    function printId(id: string | number): void {
        console.log(`The ID is: ${id}`);
        // TypeScript narrows the type inside conditional blocks using typeof.
        if (typeof id === "string") {
            console.log(`  ID is a string, length: ${id.length}`);
        } else {
            console.log(`  ID is a number, value: ${id.toFixed(2)}`);
        }
    }
    printId("my-uuid-001");
    printId(123.456);

    // An array that can contain both numbers and booleans.
    let mixedData: (number | boolean)[] = [1, true, 2, false];
    console.log(`Mixed data array: ${mixedData}`);

    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates Literal Types.
 * Literal types allow you to define a type that accepts only one specific value.
 * This is useful for creating highly specific types.
 */
function demonstrateLiteralTypes(): void {
    console.log("--- Exploring Literal Types ---------------------");

    // A variable whose type is literally the string "success".
    type Status = "success" | "error" | "pending"; // Combination of literal types (also a union!)
    let requestStatus: Status;

    requestStatus = "success"; // Valid
    console.log(`Request status: ${requestStatus}`);

    requestStatus = "pending"; // Valid
    console.log(`Request status: ${requestStatus}`);

    // 💡 EXPERIMENT: Uncomment to see a compile-time error.
    // requestStatus = "failed"; // Error: Type '"failed"' is not assignable to type 'Status'.

    // Numeric literal types.
    type HttpCode = 200 | 400 | 404 | 500;
    let statusCode: HttpCode = 200; // Valid
    console.log(`HTTP Status Code: ${statusCode}`);

    // 💡 EXPERIMENT: Uncomment to see a compile-time error.
    // statusCode = 201; // Error: Type '201' is not assignable to type 'HttpCode'.

    // Boolean literal types (less common as 'true' | 'false' is just 'boolean').
    type IsEnabled = true;
    let featureEnabled: IsEnabled = true;
    // featureEnabled = false; // Error: Type 'false' is not assignable to type 'true'.

    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates the powerful combination of Union Types and Literal Types.
 * This allows defining complex, precise types.
 */
function demonstrateDiscriminatedUnions(): void {
    console.log("-- Exploring Union & Literal Type Combination ---");

    // Define an action type that describes different possible actions.
    // Each action is an object with a specific 'type' literal property.
    type Action =
        | { type: "FETCH_START" }
        | { type: "FETCH_SUCCESS"; payload: any }
        | { type: "FETCH_ERROR"; error: string };

    let startAction: Action = { type: "FETCH_START" };
    console.log("Action 1:", startAction);

    let successAction: Action = { type: "FETCH_SUCCESS", payload: { data: "some data" } };
    console.log("Action 2:", successAction);

    let errorAction: Action = { type: "FETCH_ERROR", error: "Network error" };
    console.log("Action 3:", errorAction);

    // 💡 EXPERIMENT: Uncomment to see a compile-time error.
    // let invalidAction: Action = { type: "FETCH_DONE" }; // Error: Type '"FETCH_DONE"' is not assignable to type '"FETCH_START" | "FETCH_SUCCESS" | "FETCH_ERROR"'.

    // Function using a union of literal types for its parameter.
    function setLightState(state: "on" | "off" | "dimmed"): void {
        console.log(`Setting light state to: ${state}`);
    }
    setLightState("on");
    setLightState("dimmed");
    // 💡 EXPERIMENT: Uncomment to see a compile-time error.
    // setLightState("bright"); // Error: Argument of type '"bright"' is not assignable to parameter of type '"on" | "off" | "dimmed"'.

    // Combining with interfaces for more structured types
    interface SuccessResponse {
        status: "success";
        data: any;
    }

    interface ErrorResponse {
        status: "error";
        message: string;
        code: number;
    }

    type ApiResponse = SuccessResponse | ErrorResponse;

    function handleResponse(response: ApiResponse): void {
        if (response.status === "success") {
            console.log(`API Response Success! Data:`, response.data);
            // Type is narrowed to SuccessResponse here, so 'data' is accessible.
        } else {
            console.log(`API Response Error! Message: ${response.message}, Code: ${response.code}`);
            // Type is narrowed to ErrorResponse here, so 'message' and 'code' are accessible.
        }
    }

    handleResponse({ status: "success", data: { user: "Bob" } });
    handleResponse({ status: "error", message: "Item not found", code: 404 });

    console.log("-------------------------------------------------\n");
}

demonstrateUnionAndLiteralTypes();