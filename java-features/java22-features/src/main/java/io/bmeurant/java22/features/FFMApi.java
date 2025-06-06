package io.bmeurant.java22.features;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public class FFMApi {
    public static void main(String[] args) {
        // 1. Obtain a linker and lookup for native libraries
        Linker linker = Linker.nativeLinker();
        SymbolLookup stdlib = linker.defaultLookup();

        // Load our custom native library
        // Make sure 'libnativelib.so' (Linux/macOS) or 'nativelib.dll' (Windows)
        // is in the project root or on the library path.
        SymbolLookup nativeLib;
        try {
            // Adjust this path if your library is not in the current working directory
            // or if you named it differently. Use the simple name (without lib/.)
            nativeLib = SymbolLookup.libraryLookup("nativelib", Arena.global());
        } catch (Exception e) {
            System.err.println("\nERROR: Could not load native library 'nativelib'.");
            System.err.println("Please ensure 'native_lib.c' was compiled to 'libnativelib.so' (Linux/macOS) or 'nativelib.dll' (Windows) in the project root.");
            System.err.println("Compilation command example: gcc -shared -o libnativelib.so native_lib.c");
            System.err.println("Exiting demo.");
            return;
        }

        // --- Calling a C function (print_from_c) ---
        System.out.println("\n--- Calling C function: print_from_c ---");
        try (Arena arena = Arena.ofConfined()) { // Manage memory within this scope
            // 1. Look up the native function symbol
            MemorySegment printFromCFunc = nativeLib.find("print_from_c")
                    .orElseThrow(() -> new NoSuchMethodError("print_from_c not found"));

            // 2. Define the function descriptor: void print_from_c(const char* message)
            FunctionDescriptor printFromCDesc = FunctionDescriptor.ofVoid(ValueLayout.ADDRESS);

            // 3. Obtain a method handle for the native function
            MethodHandle printFromCHandle = linker.downcallHandle(printFromCFunc, printFromCDesc);

            // 4. Allocate a native string and call the function
            String javaString = "Hello from Java via FFM API!";
            MemorySegment cString = arena.allocateFrom(javaString); // Allocate native memory for the string

            printFromCHandle.invokeExact(cString); // Call the C function
        } catch (Throwable e) {
            System.err.println("Error calling print_from_c: " + e.getMessage());
            e.printStackTrace();
        }

        // --- Calling another C function (concatenate_strings) ---
        System.out.println("\n--- Calling C function: concatenate_strings ---");
        try (Arena arena = Arena.ofConfined()) {
            // 1. Look up the native function symbol
            MemorySegment concatStringsFunc = nativeLib.find("concatenate_strings")
                    .orElseThrow(() -> new NoSuchMethodError("concatenate_strings not found"));

            // 2. Define the function descriptor: int concatenate_strings(char* dest, const char* str1, const char* str2, int dest_size)
            FunctionDescriptor concatStringsDesc = FunctionDescriptor.of(ValueLayout.JAVA_INT,
                    ValueLayout.ADDRESS, // dest
                    ValueLayout.ADDRESS, // str1
                    ValueLayout.ADDRESS, // str2
                    ValueLayout.JAVA_INT); // dest_size

            // 3. Obtain a method handle
            MethodHandle concatStringsHandle = linker.downcallHandle(concatStringsFunc, concatStringsDesc);

            // 4. Prepare arguments and call the function
            String s1 = "Java ";
            String s2 = "22 FFM API";
            int bufferSize = 256; // Max size for concatenated string + null terminator

            MemorySegment cStr1 = arena.allocateFrom(s1);
            MemorySegment cStr2 = arena.allocateFrom(s2);
            MemorySegment cDest = arena.allocate(bufferSize); // Allocate native memory for destination buffer

            int totalLength = (int) concatStringsHandle.invokeExact(cDest, cStr1, cStr2, bufferSize);

            if (totalLength != -1) {
                // Read the result from native memory back into a Java String
                String result = cDest.getString(0); // Read from offset 0
                System.out.println("Concatenated string from C: \"" + result + "\" (Length: " + totalLength + ")");
            } else {
                System.out.println("Concatenation failed: Buffer too small.");
            }

        } catch (Throwable e) {
            System.err.println("Error calling concatenate_strings: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\nFFM API simplifies and secures native interoperability in Java 22!");
    }
}
