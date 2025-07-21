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
    demonstrateConstAssertions();

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

/**
 * Demonstrates 'const assertion' (suffix `as const`).
 * This tells TypeScript to infer the narrowest possible literal type for an expression,
 * making it immutable and deeply readonly. It's often used with literal types.
 */
function demonstrateConstAssertions(): void {
    console.log("--- Exploring 'const assertion' (`as const`) ----");

    // Without 'as const', 'status' would be inferred as 'string'
    const statusWithoutConstAssertion = "active";
    console.log(`Typescript's type of 'statusWithoutConstAssertion': 'string'`);
    // At runtime, JavaScript's typeof operator will report 'string'.
    console.log(`JavaScript's typeof for 'statusWithoutConstAssertion': '${typeof statusWithoutConstAssertion}'`);
    // 💡 EXPERIMENT: Hover over 'statusWithoutConstAssertion' in your IDE to see TypeScript's inferred type (string).

    // With 'as const', 'status' is inferred as the literal type '"active"' by TypeScript at compile-time.
    const statusWithConstAssertion = "active" as const;
    console.log(`\nTypescript's type of 'statusWithConstAssertion' (with 'as const'): '"active"'`);
    // Even with 'as const', at runtime, JavaScript's typeof operator still reports 'string'.
    // This highlights the distinction between TypeScript's compile-time types and JavaScript's runtime types.
    console.log(`JavaScript's typeof for 'statusWithConstAssertion': '${typeof statusWithConstAssertion}'`);
    // 💡 IMPORTANT: Hover over 'statusWithConstAssertion' in your IDE (e.g., IntelliJ)
    //    You should see the precise literal type: 'const statusWithConstAssertion: "active"'.
    //    This is where TypeScript's type-checking power truly lies.

    // To demonstrate the compile-time type safety of 'as const':
    // let forceLiteralType: "another" = statusWithConstAssertion; // 💡 EXPERIMENT: Uncomment this line.
    // You will get a compile-time error:
    // "Type '"active"' is not assignable to type '"another"'."
    // This error proves that TypeScript is indeed treating 'statusWithConstAssertion' as the literal type '"active"'.

    // Use case 1: Creating a literal type array (readonly tuple)
    const COLORS = ["red", "green", "blue"] as const; // Type: readonly ["red", "green", "blue"] (a readonly tuple of literal types)
    console.log(`\nArray with 'as const': ${JSON.stringify(COLORS)}`);
    // COLORS.push("yellow"); // Error: Property 'push' does not exist on type 'readonly ["red", "green", "blue"]'. (Because it's a tuple, not a mutable array)

    // Using 'typeof COLORS[number]' to create a union type from the tuple's elements
    function acceptColor(color: typeof COLORS[number]): void {
        console.log(`Accepted color from COLORS union: ${color}`);
    }
    acceptColor("red");
    // 💡 EXPERIMENT: Uncomment to see error
    // acceptColor("yellow"); // Error: Type '"yellow"' is not assignable to type '"red" | "green" | "blue"'.

    // Use case 2: Literal type for object properties (deeply readonly)
    const userSettings = {
        theme: "dark",
        fontSize: 16,
        notifications: true
    } as const;
    // Type of userSettings is now deeply readonly and specific:
    // { readonly theme: "dark"; readonly fontSize: 16; readonly notifications: true; }

    console.log(`\nUser settings (with 'as const'): theme - ${userSettings.theme}, font - ${userSettings.fontSize}`);
    // 💡 EXPERIMENT: Uncomment to see error
    // userSettings.theme = "light"; // Error: Cannot assign to 'theme' because it is a read-only property.
    // userSettings.fontSize = 18;  // Error: Cannot assign to 'fontSize' because it is a read-only property.

    // Use case 3: Ensuring literal types are maintained in complex object structures
    type TrafficLightState = "red" | "yellow" | "green";
    interface TrafficLight {
        color: TrafficLightState;
        duration: number;
    }

    const redLight: TrafficLight = {
        color: "red",
        duration: 30
    } as const; // 'as const' here makes 'color' literally "red" (and duration 30)
    console.log(`\nTraffic light state: ${redLight.color} for ${redLight.duration}s`);
    // Type of redLight.color is "red", not just TrafficLightState.
    // 💡 EXPERIMENT: Uncomment to see type inference for redLight.color
    // let literalRed: "red" = redLight.color; // No error!

    console.log("-------------------------------------------------\n");
}

demonstrateUnionAndLiteralTypes();