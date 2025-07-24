/**
 * Main function to run all demonstrations for Advanced Types.
 * This is the function imported and called by main.ts.
 */
export function demonstrateAdvancedTypes(): void {
    console.log("=================================================");
    console.log("          STEP 9: ADVANCED TYPES                 ");
    console.log("=================================================\n");

    demonstrateIntersectionTypes();
    demonstrateTemplateLiteralTypes();
    demonstrateConditionalTypes();
    demonstrateMappedTypes();

    console.log("--- END OF STEP 9 DEMONSTRATIONS ----------------\n");
}

/**
 * Demonstrates Intersection Types (`&`).
 * Intersection types allow you to combine multiple types into one.
 * The new type will have all the properties of all the combined types.
 * It's like a logical AND for types: Type A AND Type B.
 */
function demonstrateIntersectionTypes(): void {
    console.log("--- Exploring Intersection Types (`&`) ----------");

    interface Taggable {
        tags: string[];
    }

    interface Identifiable {
        id: string;
    }

    interface HasName {
        name: string;
    }

    // A type that combines properties from all three interfaces.
    type User = Identifiable & HasName & Taggable;

    const user1: User = {
        id: "u123",
        name: "Alice Smith",
        tags: ["admin", "active"]
    };
    console.log(`  User 1: ID=${user1.id}, Name=${user1.name}, Tags=[${user1.tags.join(", ")}]`);

    // Another example: combining a person and a contact.
    type PersonDetails = {
        firstName: string;
        lastName: string;
    };

    type ContactDetails = {
        email: string;
        phone?: string; // Optional property
    };

    type FullContact = PersonDetails & ContactDetails;

    const myContact: FullContact = {
        firstName: "Bob",
        lastName: "Johnson",
        email: "bob@example.com"
        // phone is optional, so we don't need to provide it.
    };
    console.log(`  Full contact: ${myContact.firstName} ${myContact.lastName}, Email: ${myContact.email}`);

    // Intersection with conflicting properties (usually avoided, but shows behavior)
    type ConflictingType1 = { value: string; };
    type ConflictingType2 = { value: number; };
    // This results in 'never' for the conflicting property, meaning it's impossible to satisfy.
    type ConflictingIntersection = ConflictingType1 & ConflictingType2;

    // 💡 EXPERIMENT: Uncomment the line below to see a compile-time error.
    // const impossible: ConflictingIntersection = { value: "hello" }; // Error: Type 'string' is not assignable to type 'never'.
    // const impossible2: ConflictingIntersection = { value: 123 }; // Error: Type 'number' is not assignable to type 'never'.

    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates Template Literal Types.
 * These allow you to create new string literal types by concatenating string literals
 * and union types with template string syntax.
 */
function demonstrateTemplateLiteralTypes(): void {
    console.log("--- Exploring Template Literal Types ------------");

    type Direction = "left" | "right" | "up" | "down";
    type ButtonPrefix = "btn_";

    // Combines a prefix with a direction to create new literal strings.
    type ButtonId = `${ButtonPrefix}${Direction}`;

    const saveButton: ButtonId = "btn_left";
    // 💡 EXPERIMENT: Uncomment the line below to see a compile-time error.
    // const invalidButton: ButtonId = "btn_forward"; // Error: Type '"btn_forward"' is not assignable to type 'ButtonId'.

    console.log(`  Button ID: ${saveButton}`);

    // More complex example: creating event names
    type EventName = "click" | "hover" | "focus";
    type Element = "button" | "input" | "div";

    type DomEvent = `${Element}_${EventName}`;

    let myDomEvent: DomEvent = "button_click";
    console.log(`  DOM Event: ${myDomEvent}`);

    // 💡 EXPERIMENT: Uncomment the line below to see a compile-time error.
    // let invalidDomEvent: DomEvent = "span_click"; // Error: Type '"span_click"' is not assignable to type 'DomEvent'.

    // This type extracts everything before the SECOND underscore.
    // We match the pattern: (anything)_ (anything else)_ (rest of string)
    // And we infer the part before the second underscore.
    type GetPrefixBeforeSecondUnderscore<S extends string> =
        S extends `${infer PrefixA}_${infer PrefixB}_${string}` ? `${PrefixA}_${PrefixB}` : never;

    // Example 1: String with multiple underscores
    type ExtractedPrefix2 = GetPrefixBeforeSecondUnderscore<"my_long_string_value">; // Type is "my_long"
    const actualPrefix2: ExtractedPrefix2 = "my_long";
    console.log(`  Extracted prefix (before second underscore - Example 1): ${actualPrefix2}`);

    // 💡 EXPERIMENT: Uncomment the line below to see a compile-time error.
    // const wrongPrefix2: ExtractedPrefix2 = "my_wrong"; // Error: Type '"my_wrong"' is not assignable to type '"my_long"'.

    // Example 2: String with only one underscore (should result in never)
    type ExtractedPrefixWithSingleUnderscore = GetPrefixBeforeSecondUnderscore<"single_value">; // Type is never
    // 💡 EXPERIMENT: Uncomment the line below to see a compile-time error.
    // const shouldBeNever: ExtractedPrefixWithSingleUnderscore = "single"; // Error: Type '"single"' is not assignable to type 'never'.
    console.log(`  Extracted prefix (from single_value): ${"This will be 'never' type" as ExtractedPrefixWithSingleUnderscore}`);


    // Example 3: String with no underscore (should result in never)
    type ExtractedPrefixNoUnderscore = GetPrefixBeforeSecondUnderscore<"no_underscore_here">; // Type is never
    // 💡 EXPERIMENT: Uncomment the line below to see a compile-time error.
    // const shouldBeNeverAgain: ExtractedPrefixNoUnderscore = "no"; // Error: Type '"no"' is not assignable to type 'never'.
    console.log(`  Extracted prefix (from no_underscore_here): ${"This will also be 'never' type" as ExtractedPrefixNoUnderscore}`);


    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates Conditional Types.
 * Conditional types allow you to choose a type based on a condition
 * that checks the relationship between two types.
 * Syntax: `SomeType extends OtherType ? TrueType : FalseType`
 */
function demonstrateConditionalTypes(): void {
    console.log("--- Exploring Conditional Types -----------------");

    // Example 1: Check if a type is a string
    type IsString<T> = T extends string ? "Yes" : "No";

    type Result1 = IsString<"hello">; // "Yes"
    type Result2 = IsString<123>;     // "No"
    type Result3 = IsString<string>;  // "Yes" (string extends string)
    type Result4 = IsString<number>;  // "No"

    // To demonstrate the *type* being "Yes" or "No", we should use a string literal.
    const isHelloAString: Result1 = "Yes"; // This assignment is valid because "Yes" matches the Result1 type
    console.log(`  Is "hello" a string? Result type is '${isHelloAString}'`);

    const is123AString: Result2 = "No"; // This assignment is valid because "No" matches the Result2 type
    console.log(`  Is 123 a string? Result type is '${is123AString}'`);
    // 💡 EXPERIMENT: Uncomment the line below to see a compile-time error.
    // const attemptWrongAssignment: Result2 = "Yes"; // Error: Type '"Yes"' is not assignable to type '"No"'.

    // Example 2: Extracting return type of a function (often used in utility types)
    type FunctionType = (a: number, b: string) => boolean;
    type NotFunctionType = { prop: string };

    type ReturnTypeIfFunction<T> = T extends (...args: any[]) => infer R ? R : never;

    type MyReturnType = ReturnTypeIfFunction<FunctionType>;     // Type is boolean
    type MyOtherType = ReturnTypeIfFunction<NotFunctionType>; // Type is never

    // To demonstrate, we describe what the type is
    console.log(`  Return type of a function: Type is 'boolean' (actual value might be true/false)`);
    console.log(`  Return type of non-function: Type is 'never'`);

    // Example 3: Distributive Conditional Types
    // When a conditional type acts on a union type, it distributes over the union.
    type ToArray<T> = T extends any ? T[] : never;

    type ResultUnion = ToArray<string | number>; // Becomes string[] | number[]
    // string extends any ? string[] : never  => string[]
    // number extends any ? number[] : never  => number[]
    // Result: string[] | number[]

    console.log(`  Distributive conditional type result: Type is 'string[] | number[]'`);
    const exampleDistributive: ResultUnion = ["test", "string"]; // Valid
    const exampleDistributive2: ResultUnion = [10, 20];      // Valid
    console.log(`  Example: [${exampleDistributive.join(", ")}] or [${exampleDistributive2.join(", ")}]`);

    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates Mapped Types.
 * Mapped types allow you to take an existing type and transform each of its
 * properties based on a new set of property modifiers.
 * They are often used to create variations of existing types.
 */
function demonstrateMappedTypes(): void {
    console.log("--- Exploring Mapped Types ----------------------");

    interface UserProfile {
        id: string;
        name: string;
        email: string;
        age?: number; // Optional
    }

    // 1. Partial<T>: Makes all properties in T optional.
    type PartialUserProfile = Partial<UserProfile>;
    const partialUser: PartialUserProfile = { name: "Jane" }; // Only 'name' is needed
    console.log(`  Partial user: ${JSON.stringify(partialUser)}`);

    // 2. Required<T>: Makes all properties in T required.
    type RequiredUserProfile = Required<UserProfile>;
    // 💡 EXPERIMENT: Uncomment the line below to see a compile-time error.
    // const requiredUser: RequiredUserProfile = { id: "u1", name: "John", email: "john@ex.com" }; // Error: Property 'age' is missing
    const requiredUser: RequiredUserProfile = { id: "u1", name: "John", email: "john@ex.com", age: 40 };
    console.log(`  Required user: ${JSON.stringify(requiredUser)}`);

    // 3. Readonly<T>: Makes all properties in T readonly.
    type ReadonlyUserProfile = Readonly<UserProfile>;
    const readonlyUser: ReadonlyUserProfile = { id: "u2", name: "Mark", email: "mark@ex.com", age: 25 };
    // 💡 EXPERIMENT: Uncomment the line below to see a compile-time error.
    // readonlyUser.name = "Marcus"; // Error: Cannot assign to 'name' because it is a read-only property.
    console.log(`  Readonly user: ${JSON.stringify(readonlyUser)}`);

    // 4. Pick<T, K>: Constructs a type by picking the set of properties K from T.
    type UserSummary = Pick<UserProfile, "id" | "name">;
    const userSummary: UserSummary = { id: "u3", name: "Sarah" };
    console.log(`  User summary (picked): ${JSON.stringify(userSummary)}`);

    // 5. Omit<T, K>: Constructs a type by omitting the set of properties K from T.
    type UserWithoutIdAndEmail = Omit<UserProfile, "id" | "email">;
    const userOmitted: UserWithoutIdAndEmail = { name: "Peter", age: 50 };
    console.log(`  User (omitted id/email): ${JSON.stringify(userOmitted)}`);

    // Custom Mapped Type Example: making properties nullable
    type Nullable<T> = { [P in keyof T]: T[P] | null; };
    type NullableUserProfile = Nullable<UserProfile>;
    const nullableUser: NullableUserProfile = { id: "u4", name: "Paul", email: null, age: null };
    console.log(`  Nullable user: ${JSON.stringify(nullableUser)}`);

    console.log("-------------------------------------------------\n");
}

demonstrateAdvancedTypes();