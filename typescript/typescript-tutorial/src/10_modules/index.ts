/**
 * This file demonstrates how to use modules and namespaces.
 */

// Import a module and its members.
// The import statement creates a dependency on 'data_module.ts'.
import { APP_VERSION, User, getUserDetails, UserData } from './data_module';

/**
 * Main function to run all demonstrations for Modules and Namespaces.
 * This is the function imported and called by main.ts.
 */
export function demonstrateModules(): void {
    console.log("=================================================");
    console.log("      STEP 10: MODULES                           ");
    console.log("=================================================\n");

    _demonstrateModules();

    console.log("--- END OF STEP 10 DEMONSTRATIONS ---------------\n");
}

// Let's create an instance using the imported module class.
function _demonstrateModules(): void {
    console.log("--- Exploring ES Modules ------------------------");
    console.log(`  Application Version: ${APP_VERSION}`);

    const alice: User = new User("Alice", "alice@example.com");
    console.log(`  Created user: ${alice.name}`);

    // Demonstrate the use of the interface from the module.
    const userFromDB: UserData = {
        name: "Bob",
        email: "bob@example.com"
    };

    const details = getUserDetails(101);
    console.log(`  Details for user: ${userFromDB.name} - ${details}`);
    console.log("-------------------------------------------------\n");
}

_demonstrateModules();