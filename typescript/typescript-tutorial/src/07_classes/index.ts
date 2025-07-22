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

demonstrateClasses();