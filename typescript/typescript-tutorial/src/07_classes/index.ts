/**
 * Main function to run all demonstrations for Classes.
 * This is the function imported and called by main.ts.
 */
export function demonstrateClasses(): void {
    console.log("=================================================");
    console.log("              STEP 7: CLASSES                    ");
    console.log("=================================================\n");

    demonstrateBasicClasses();
    demonstrateAccessModifiers();
    demonstrateInheritance();
    demonstrateClassImplementsInterface();
    demonstrateParameterProperties();
    demonstrateReadonlyProperties();
    demonstrateStaticMembers();

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

/**
 * Demonstrates access modifiers: public, private, protected.
 * These control the visibility of class members (properties and methods).
 * This is a key feature provided by TypeScript, not natively in JavaScript.
 */
function demonstrateAccessModifiers(): void {
    console.log("--- Exploring Access Modifiers ---");

    class Employee {
        public readonly id: string; // 'public': Accessible everywhere. 'readonly': Cannot be reassigned after initialization.
        private salary: number;     // 'private': Only accessible within the 'Employee' class itself.
        protected department: string; // 'protected': Accessible within 'Employee' and its subclasses.

        constructor(id: string, salary: number, department: string) {
            this.id = id;
            this.salary = salary;
            this.department = department;
        }

        public getDetails(): string {
            // Inside the class, all members are accessible.
            return `  ID: ${this.id}, Department: ${this.department}`;
        }

        // Public method to provide controlled access to the private 'salary'.
        public getSalary(): number {
            return this.salary;
        }
    }

    // A subclass inheriting from 'Employee'.
    class Manager extends Employee {
        constructor(id: string, salary: number, department: string) {
            super(id, salary, department); // Call the parent class's constructor
        }

        public getManagerDetails(): string {
            // 'id' (public) is accessible.
            // 'department' (protected) is accessible in subclasses.
            return `  Manager ID: ${this.id}, Managing Department: ${this.department}`;
        }

        // 💡 EXPERIMENT: Uncomment the line below to see a compile-time error.
        // public testSalaryAccess(): number { return this.salary; } // Error: Property 'salary' is private and only accessible within class 'Employee'.
    }

    let emp = new Employee("E001", 60000, "Sales");
    console.log(emp.getDetails());
    console.log(`  Employee salary (via public getter): ${emp.getSalary()}`);
    // 💡 EXPERIMENT: Uncomment to see compile-time errors.
    // console.log(emp.salary); // Error: Property 'salary' is private and only accessible within class 'Employee'.
    // emp.id = "E002"; // Error: Cannot assign to 'id' because it is a read-only property.

    let manager = new Manager("M001", 90000, "Engineering");
    console.log(manager.getManagerDetails());

    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates class inheritance using 'extends' and 'super'.
 * Subclasses (derived classes) inherit properties and methods from their base class.
 */
function demonstrateInheritance(): void {
    console.log("--- Exploring Inheritance -----------------------");

    // Base class
    class Animal {
        name: string;

        constructor(name: string) {
            this.name = name;
        }

        move(distanceInMeters: number = 0): void {
            console.log(`  ${this.name} moved ${distanceInMeters}m.`);
        }
    }

    // Derived class (subclass) 'Dog' extends 'Animal'.
    class Dog extends Animal {
        breed: string;

        constructor(name: string, breed: string) {
            super(name); // Call the constructor of the base class (Animal) using 'super()'.
                         // This is mandatory in derived class constructors.
            this.breed = breed;
        }

        bark(): void {
            console.log(`  ${this.name} (${this.breed}) barks! Woof!`);
        }

        // Override the 'move' method from the base class.
        // We can optionally call the base class method using 'super.methodName()'.
        move(distanceInMeters = 5): void {
            console.log(`  ${this.name} is running...`);
            super.move(distanceInMeters); // Call the original 'move' method from 'Animal'
        }
    }

    let myDog = new Dog("Buddy", "Golden Retriever");
    myDog.bark();
    myDog.move(10); // Calls the overridden 'move' method in 'Dog'

    let genericAnimal = new Animal("Generic Beast");
    genericAnimal.move(3);

    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates how classes can implement interfaces.
 * A class implementing an interface must guarantee that it has all the
 * properties and methods defined by that interface. This enforces a contract.
 */
function demonstrateClassImplementsInterface(): void {
    console.log("--- Exploring Class Implements Interface --------");

    // Define an interface for a basic logger contract.
    interface Logger {
        log(message: string): void;
        logError(message: string, error: Error): void;
    }

    // Implement the 'Logger' interface in a class.
    // TypeScript will ensure 'ConsoleLogger' has 'log' and 'logError' methods.
    class ConsoleLogger implements Logger {
        log(message: string): void {
            console.log(`  [CONSOLE LOG] ${message}`);
        }

        logError(message: string, error: Error): void {
            console.error(`  [CONSOLE ERROR] ${message}: ${error.message}`);
        }
    }

    // Another class implementing the same interface, perhaps writing to a file.
    class FileLogger implements Logger {
        private filePath: string; // Specific property for FileLogger

        constructor(filePath: string) {
            this.filePath = filePath;
        }

        log(message: string): void {
            // In a real application, this would write to a file.
            console.log(`  [FILE LOG - ${this.filePath}] ${message}`);
        }

        logError(message: string, error: Error): void {
            // In a real application, this would write error details to a file.
            console.error(`  [FILE ERROR - ${this.filePath}] ${message}: ${error.message}`);
        }
    }

    let consoleLogger: Logger = new ConsoleLogger(); // Type: Logger, actual instance: ConsoleLogger
    consoleLogger.log("This is a console message.");
    try { throw new Error("Something went wrong with console!"); }
    catch (e: any) { consoleLogger.logError("Failed console operation", e); }


    let fileLogger: Logger = new FileLogger("/var/log/app.log"); // Type: Logger, actual instance: FileLogger
    fileLogger.log("This message would theoretically go to a file.");
    try { throw new Error("File system access error!"); }
    catch (e: any) { fileLogger.logError("Critical file issue", e); }

    // This demonstrates polymorphism: you can treat different logger implementations
    // uniformly because they all adhere to the 'Logger' interface contract.
    function processLogs(logger: Logger, messages: string[]): void {
        console.log(`\n  Processing logs with ${logger.constructor.name}:`);
        messages.forEach(msg => logger.log(msg));
    }
    processLogs(new ConsoleLogger(), ["User logged in", "Data saved"]);
    processLogs(new FileLogger("debug.log"), ["Configuration loaded", "Service started"]);


    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates Parameter Properties (Constructor Shorthand).
 * This is a convenient TypeScript-specific syntax for declaring class properties
 * and initializing them directly within the constructor's parameter list.
 */
function demonstrateParameterProperties(): void {
    console.log("--- Exploring Parameter Properties --------------");

    // Define a class 'Coordinate' using parameter properties.
    // By adding an access modifier (public, private, protected, readonly)
    // before a constructor parameter, TypeScript automatically:
    // 1. Declares a class property with that name and type.
    // 2. Initializes that property with the value passed to the constructor.
    class Coordinate {
        // No need to explicitly declare 'x', 'y', 'description' as properties here.
        // No need to explicitly write 'this.x = x;' inside the constructor body.
        // TypeScript does it all for you!

        constructor(public x: number, private y: number, protected description: string) {
            // The constructor body can be empty if only parameter properties are used for initialization.
            // Or it can contain additional logic if needed.
        }

        getPoint(): string {
            // 'x' is public, 'y' is private (accessible inside the class).
            return `  Point coordinates: (${this.x}, ${this.y}). Description: ${this.description}`;
        }

        // Public getter for the private 'y' property (controlled access).
        get PrivateY(): number {
            return this.y;
        }
    }

    // Create an instance of 'Coordinate'.
    let p1 = new Coordinate(10, 20, "Center Point");
    console.log(p1.getPoint());
    console.log(`  Accessing public x directly: ${p1.x}`);
    console.log(`  Accessing private y via getter: ${p1.PrivateY}`);

    // 💡 EXPERIMENT: Uncomment to see compile-time errors.
    // console.log(p1.y); // Error: Property 'y' is private and only accessible within class 'Coordinate'.
    // console.log(p1.description); // Error: Property 'description' is protected and only accessible within class 'Coordinate' and its subclasses.

    // A subclass can access 'protected' properties.
    class WeightedCoordinate extends Coordinate {
        constructor(x: number, y: number, description: string, public weight: number) {
            super(x, y, description); // Call the parent constructor
        }

        getWeightedDescription(): string {
            // 'description' is protected and therefore accessible here.
            return `  Weighted point: '${this.description}', Weight: ${this.weight}`;
        }
    }

    let wc1 = new WeightedCoordinate(5, 5, "Key Location", 1.5);
    console.log(wc1.getWeightedDescription());
    console.log(`  Accessing public x from subclass: ${wc1.x}`);

    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates the 'readonly' modifier for class properties.
 * A readonly property can only be assigned during its declaration or in the constructor.
 * After that, it cannot be changed.
 */
function demonstrateReadonlyProperties(): void {
    console.log("--- Exploring Readonly Properties ----------------");

    class Product {
        readonly id: string;         // Readonly property, must be assigned in constructor or at declaration
        public name: string;
        private _price: number;
        readonly createdAt: Date = new Date(); // Readonly property, initialized at declaration

        constructor(id: string, name: string, price: number) {
            this.id = id; // OK: Assignment in the constructor
            this.name = name;
            this._price = price;
        }

        public get Price(): number {
            return this._price;
        }

        public set Price(newPrice: number) {
            if (newPrice > 0) {
                this._price = newPrice;
            } else {
                console.warn("  Price cannot be negative.");
            }
        }

        // 💡 EXPERIMENT: Uncomment to see compile-time error.
        // changeId(newId: string): void {
        //     this.id = newId; // Error: Cannot assign to 'id' because it is a read-only property.
        // }
    }

    let product1 = new Product("PROD-001", "Laptop", 1200);
    console.log(`  Product: ${product1.name} (ID: ${product1.id}), Created: ${product1.createdAt.toLocaleDateString()}`);

    product1.name = "Gaming Laptop"; // OK: 'name' is not readonly
    // product1.id = "NEW-ID"; // Error: Cannot assign to 'id' because it is a read-only property.

    console.log(`  Product name after modification: ${product1.name}`);
    console.log(`  Product ID remains: ${product1.id}`); // ID remains the same

    // Readonly with Parameter Properties (a common and concise pattern)
    class UserProfile {
        constructor(
            public readonly userId: string, // Readonly and public
            private _email: string,
            public displayName: string
        ) {}

        get email(): string {
            return this._email;
        }
        set email(newEmail: string) {
            this._email = newEmail;
        }

        // 💡 EXPERIMENT: Uncomment to see compile-time error.
        // setUserId(newId: string): void {
        //     this.userId = newId; // Error: Cannot assign to 'userId' because it is a read-only property.
        // }
    }

    let user = new UserProfile("USER-ALPHA", "alpha@example.com", "Alpha User");
    console.log(`\n  User Profile - ID: ${user.userId}, Name: ${user.displayName}, Email: ${user.email}`);
    user.displayName = "Beta User"; // OK
    user.email = "beta@example.com"; // OK via setter
    // user.userId = "USER-BETA"; // Error: Cannot assign to 'userId' because it is a read-only property.

    console.log(`  User Profile after modification - ID: ${user.userId}, Name: ${user.displayName}, Email: ${user.email}`);

    console.log("-------------------------------------------------\n");
}

/**
 * Demonstrates static properties and methods.
 * These members belong to the class itself, not to instances (objects) of the class.
 * They are accessed directly on the class name.
 */
function demonstrateStaticMembers(): void {
    console.log("--- Exploring Static Properties and Methods ------");

    class MathUtility {
        // A static property - shared across all instances (or accessed directly on the class).
        static PI: number = 3.14159;
        static readonly E: number = 2.71828; // Static and read-only

        // A static method - called directly on the class name.
        // It cannot access instance-specific properties (using 'this').
        static add(x: number, y: number): number {
            return x + y;
        }

        static multiply(x: number, y: number): number {
            return x * y;
        }

        // 💡 EXPERIMENT: Uncomment to see compile-time error.
        // instanceMethod(): void { console.log(this.PI); } // Error: Static property 'PI' cannot be accessed through non-static 'this'.
    }

    // Access static property directly on the class name.
    console.log(`  The value of PI is: ${MathUtility.PI}`);
    console.log(`  The value of E is: ${MathUtility.E}`);

    // Call static method directly on the class name.
    console.log(`  5 + 3 = ${MathUtility.add(5, 3)}`);
    console.log(`  4 * 2 = ${MathUtility.multiply(4, 2)}`);

    // 💡 EXPERIMENT: Uncomment to see compile-time error.
    // You cannot access static members from an instance.
    // let calculator = new MathUtility();
    // console.log(calculator.PI); // Error: Property 'PI' does not exist on type 'MathUtility'.
    // calculator.add(1, 2); // Error: Property 'add' does not exist on type 'MathUtility'.

    console.log("--------------------------------------------------\n");
}

demonstrateClasses();