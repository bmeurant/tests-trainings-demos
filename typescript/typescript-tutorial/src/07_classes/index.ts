/**
 * Main function to run all demonstrations for Classes.
 * This is the function imported and called by main.ts.
 */
export function demonstrateClasses(): void {
    console.log("=================================================");
    console.log("              STEP 7: CLASSES                    ");
    console.log("=================================================\n");

    demonstrateBasicClasses();

    console.log("--- END OF STEP 7 DEMONSTRATIONS ----------------\n");
}

/**
 * Demonstrates basic class definition, instantiation, and methods.
 * Classes provide a blueprint for creating objects with similar properties and methods.
 */
function demonstrateBasicClasses(): void {
    console.log("--- Exploring Basic Classes ---------------------");

    // Define a simple class 'Person'.
    // Properties are typically defined first, then the constructor, then methods.
    class Person {
        name: string;
        age: number;

        // The constructor is a special method called when an object is created.
        constructor(name: string, age: number) {
            this.name = name; // Initialize the 'name' property
            this.age = age;   // Initialize the 'age' property
        }

        // A method (function) associated with the class.
        greet(): void {
            console.log(`  Hello, my name is ${this.name} and I am ${this.age} years old.`);
        }
    }

    // Create an instance (object) of the 'Person' class.
    let person1 = new Person("Alice", 30);
    person1.greet(); // Call a method on the instance

    let person2 = new Person("Bob", 24);
    person2.greet();

    // Accessing properties directly (if not private/protected).
    console.log(`  Person 1's name: ${person1.name}`);

    console.log("-------------------------------------------------\n");
}

demonstrateClasses();