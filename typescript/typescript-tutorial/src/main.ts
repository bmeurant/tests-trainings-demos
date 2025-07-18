import { demonstrateFundamentalTypes } from './01_fundamental_types';
import {demonstrateArraysAndTuples} from "./02_arrays_tuples";
import { demonstrateEnumsAndObjects } from './03_enums_objects';
import { demonstrateFunctions } from './04_functions';
import { demonstrateInterfaces } from './05_interfaces';

/**
 * This is the main entry point for the TypeScript tutorial.
 * It orchestrates the execution of demonstrations for each learning step.
 */
function runTutorial(): void {
    console.log("=================================================");
    console.log("          STARTING TYPESCRIPT TUTORIAL           ");
    console.log("=================================================");

    // --- 01 Fundamental Types ---
    demonstrateFundamentalTypes();

    // --- 02 Arrays and Tuples ---
    demonstrateArraysAndTuples();

    // --- 03 Enums and Objects ---
    demonstrateEnumsAndObjects();

    // --- 04 Functions ---
    demonstrateFunctions();

    // --- 05 Interfaces ---
    demonstrateInterfaces();

    console.log("=================================================");
    console.log("          TYPESCRIPT TUTORIAL COMPLETED          ");
    console.log("=================================================");
}

// Call the main tutorial runner function
runTutorial();