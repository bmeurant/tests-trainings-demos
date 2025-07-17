/**
 * Main function to run all demonstrations for Enums and Objects.
 * This is the function imported and called by main.ts.
 */
export function demonstrateEnumsAndObjects(): void {
    console.log("=================================================");
    console.log("             STEP 3: ENUMS AND OBJECTS           ");
    console.log("=================================================\n");

    demonstrateNumericEnums();

    console.log("--- END OF STEP 3 DEMONSTRATIONS ----------------\n");
}

/**
 * Demonstrates Numeric Enums.
 * By default, enums are number-based, starting at 0.
 * They provide a way to define a set of named constants.
 */
function demonstrateNumericEnums(): void {
    console.log("--- Exploring Numeric Enums ---------------------");

    // Define a numeric enum for weekdays with automatic assignment of values.
    // Monday = 0, Tuesday = 1, etc.
    enum Weekday {
        Monday,
        Tuesday,
        Wednesday,
        Thursday,
        Friday,
        Saturday,
        Sunday
    }

    let today: Weekday = Weekday.Wednesday;
    console.log(`Today is: ${Weekday[today]} (value: ${today})`);

    // You can also assign explicit numeric values.
    enum HttpStatusCode {
        OK = 200,
        BadRequest = 400,
        Unauthorized = 401,
        NotFound = 404,
        InternalServerError = 500
    }

    let responseStatus: HttpStatusCode = HttpStatusCode.OK;
    console.log(`HTTP Status: ${HttpStatusCode[responseStatus]} (code: ${responseStatus})`);

    let errorStatus: HttpStatusCode = HttpStatusCode.NotFound;
    console.log(`Error Status: ${HttpStatusCode[errorStatus]} (code: ${errorStatus})`);

    // 💡 EXPERIMENT: Try assigning a number that is not explicitly part of the enum.
    // TypeScript allows this, which can be a trap! Use string enums for better safety.
    // let unknownStatus: HttpStatusCode = 999; // This is technically allowed in numeric enums at compile time

    console.log("-------------------------------------------------\n");
}

demonstrateEnumsAndObjects();