/**
 * Main function to run all demonstrations for Generics.
 * This is the function imported and called by main.ts.
 */
export function demonstrateGenerics(): void {
    console.log("=================================================");
    console.log("              STEP 8: GENERICS                   ");
    console.log("=================================================\n");

    demonstrateGenericFunctions();
    demonstrateGenericInterfaces();
    demonstrateGenericClasses();

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

/**
 * Demonstrates Generic Interfaces.
 * Generic interfaces allow you to define flexible data structures
 * that can work with different types.
 */
function demonstrateGenericInterfaces(): void {
    console.log("--- Exploring Generic Interfaces ----------------");

    // Define a generic interface for a pair of values.
    interface Pair<T1, T2> {
        first: T1;
        second: T2;
    }

    // Use the generic interface with specific types.
    let numberAndString: Pair<number, string> = { first: 1, second: "one" };
    console.log(`  Pair (number, string): ${numberAndString.first}, ${numberAndString.second}`);

    let booleanAndBoolean: Pair<boolean, boolean> = { first: true, second: false };
    console.log(`  Pair (boolean, boolean): ${booleanAndBoolean.first}, ${booleanAndBoolean.second}`);

    // Define a generic interface for a key-value store.
    interface KeyValuePair<K, V> {
        key: K;
        value: V;
    }

    let userConfig: KeyValuePair<string, number> = { key: "theme", value: 123 };
    console.log(`  Key-Value Pair (string, number): ${userConfig.key} -> ${userConfig.value}`);

    // A more complex generic interface, e.g., for a simple generic repository pattern.
    interface Repository<T> {
        getById(id: string): T | undefined;
        add(item: T): void;
        getAll(): T[];
    }

    // Implement the generic repository for a 'User' type.
    interface User {
        id: string;
        name: string;
        email: string;
    }

    class UserRepository implements Repository<User> {
        readonly users: User[] = [];

        constructor(initialUsers: User[] = []) {
            this.users = initialUsers;
        }

        getById(id: string): User | undefined {
            return this.users.find(u => u.id === id);
        }

        add(item: User): void {
            this.users.push(item);
            console.log(`    Added user: ${item.name}`);
        }

        getAll(): User[] {
            return [...this.users]; // Return a copy
        }
    }

    const myUsers = [
        { id: "u1", name: "Alice", email: "alice@example.com" },
        { id: "u2", name: "Bob", email: "bob@example.com" }
    ];
    let userRepo = new UserRepository(myUsers);
    console.log(`\n  All users: ${userRepo.getAll().map(u => u.name).join(", ")}`);

    userRepo.add({ id: "u3", name: "Charlie", email: "charlie@example.com" });
    console.log(`  User with ID u2: ${userRepo.getById("u2")?.name}`);

    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates Generic Classes.
 * Generic classes allow you to define classes that can work with various types,
 * making them reusable for different data structures.
 */
function demonstrateGenericClasses(): void {
    console.log("--- Exploring Generic Classes -------------------");

    // A generic class for a simple Stack data structure.
    class Stack<T> {
        readonly elements: T[] = [];

        push(element: T): void {
            this.elements.push(element);
            console.log(`  Pushed: ${element}`);
        }

        pop(): T | undefined {
            const popped = this.elements.pop();
            console.log(`  Popped: ${popped}`);
            return popped;
        }

        peek(): T | undefined {
            return this.elements[this.elements.length - 1];
        }

        isEmpty(): boolean {
            return this.elements.length === 0;
        }

        size(): number {
            return this.elements.length;
        }
    }

    // Create a stack of numbers.
    let numberStack = new Stack<number>();
    numberStack.push(10);
    numberStack.push(20);
    numberStack.pop();
    console.log(`  Number stack size: ${numberStack.size()}`);

    // Create a stack of strings.
    let stringStack = new Stack<string>();
    stringStack.push("Hello");
    stringStack.push("World");
    console.log(`  String stack peek: ${stringStack.peek()}`);
    stringStack.pop();
    stringStack.pop();
    console.log(`  String stack is empty: ${stringStack.isEmpty()}`);

    console.log("-------------------------------------------------\n");
}

demonstrateGenerics();