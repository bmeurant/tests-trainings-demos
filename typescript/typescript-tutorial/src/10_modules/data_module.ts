// src/10_modules_namespaces/data_module.ts

/**
 * Modern ES Modules (ESM)
 * A file with a top-level import or export is considered a module.
 * Modules have their own scope and do not leak variables into the global scope.
 */

// Export an interface
export interface UserData {
    name: string;
    email: string;
}

// Export a class
export class User {
    constructor(public name: string, public email: string) {}
}

// Export a constant
export const APP_VERSION = "1.0.2";

// Export a function
export function getUserDetails(id: number): string {
    return `User with ID ${id} fetched from DB.`;
}