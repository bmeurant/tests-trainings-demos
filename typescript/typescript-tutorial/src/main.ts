import { demonstrateFundamentalTypes } from './01_fundamental_types';
import {demonstrateArraysAndTuples} from "./02_arrays_tuples";
import { demonstrateEnumsAndObjects } from './03_enums_objects';
import { demonstrateFunctions } from './04_functions';
import { demonstrateInterfaces } from './05_interfaces';
import { demonstrateUnionAndLiteralTypes } from './06_union_literal_types';
import { demonstrateClasses } from './07_classes';
import { demonstrateGenerics } from './08_generics';
import { demonstrateAdvancedTypes } from './09_advanced_types';
import { demonstrateModules } from './10_modules';

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

    // --- 06 Union Types and Literal Types ---
    demonstrateUnionAndLiteralTypes();

    // --- 07 Classes ---
    demonstrateClasses();

    // --- 08 Generics ---
    demonstrateGenerics();

    // --- 09 Advanced Types ---
    demonstrateAdvancedTypes();

    // --- 10 Modules ---
    demonstrateModules();

    console.log("=================================================");
    console.log("          TYPESCRIPT TUTORIAL COMPLETED          ");
    console.log("=================================================");
}

// Call the main tutorial runner function
runTutorial();