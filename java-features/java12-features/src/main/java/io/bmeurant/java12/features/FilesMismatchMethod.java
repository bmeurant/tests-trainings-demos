package io.bmeurant.java12.features;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesMismatchMethod {

    public static void main(String[] args) {

        Path file1 = Paths.get("fileA.txt");
        Path file2 = Paths.get("fileB.txt");
        Path file3 = Paths.get("fileC.txt"); // Will be identical to fileA
        Path file4 = Paths.get("fileD.txt"); // Will be a prefix of fileA

        // Prepare test files
        try {
            Files.writeString(file1, "Hello World!");
            Files.writeString(file2, "Hello Java!");
            Files.writeString(file3, "Hello World!"); // Identical to file1
            Files.writeString(file4, "Hello");       // Prefix of file1

            System.out.println("Test files created.");

            System.out.println("\n+++ Example 1: Files are different +++");
            long mismatchIndex1 = Files.mismatch(file1, file2);
            System.out.println("\nComparing '" + file1.getFileName() + "' and '" + file2.getFileName() + "':");
            if (mismatchIndex1 == -1L) {
                System.out.println("  Files are identical.");
            } else {
                System.out.println("  First mismatch at index: " + mismatchIndex1); // Expected: 6 (W vs J)
            }

            System.out.println("\n+++ Example 2: Files are identical +++");
            long mismatchIndex2 = Files.mismatch(file1, file3);
            System.out.println("\nComparing '" + file1.getFileName() + "' and '" + file3.getFileName() + "':");
            if (mismatchIndex2 == -1L) {
                System.out.println("  Files are identical."); // Expected: Files are identical.
            } else {
                System.out.println("  First mismatch at index: " + mismatchIndex2);
            }

            System.out.println("\n+++ Example 3: One file is a prefix of another+++");
            long mismatchIndex3 = Files.mismatch(file1, file4);
            System.out.println("\nComparing '" + file1.getFileName() + "' and '" + file4.getFileName() + "':");
            if (mismatchIndex3 == -1L) {
                System.out.println("  Files are identical.");
            } else {
                System.out.println("  First mismatch at index (or length of smaller file): " + mismatchIndex3); // Expected: 5 (length of "Hello")
            }

            System.out.println("\n+++ Example 3: Comparing with a non-existent file+++");
            Path nonExistent = Paths.get("nonExistent.txt");
            try {
                Files.mismatch(file1, nonExistent);
            } catch (IOException e) {
                System.out.println("\nSuccessfully caught expected exception when comparing with non-existent file: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("An error occurred during file operations: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Clean up created files
            try {
                Files.deleteIfExists(file1);
                Files.deleteIfExists(file2);
                Files.deleteIfExists(file3);
                Files.deleteIfExists(file4);
                System.out.println("\nCleaned up created files.");
            } catch (IOException e) {
                System.err.println("Error cleaning up files: " + e.getMessage());
            }
        }
    }
}
