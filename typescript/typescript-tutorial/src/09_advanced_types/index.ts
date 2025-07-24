/**
 * Main function to run all demonstrations for Advanced Types.
 * This is the function imported and called by main.ts.
 */
export function demonstrateAdvancedTypes(): void {
    console.log("=================================================");
    console.log("          STEP 9: ADVANCED TYPES                 ");
    console.log("=================================================\n");

    demonstrateIntersectionTypes();

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

demonstrateAdvancedTypes();