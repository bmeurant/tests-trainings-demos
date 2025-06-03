package io.bmeurant.java11.features;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FilesReadWriteString {

    public static void main(String[] args) {
        Path filePath = Path.of("sample_java11.txt"); // Creates a Path object for a file in the current directory

        String contentToWrite = "This is a test string written by Java 11's writeString method.\n"
                + "It supports multiple lines.\n"
                + "Enjoy the simplicity!";

        // --- Writing to a file ---
        try {
            // Write string to file, creating it if it doesn't exist, or truncating if it does
            Files.writeString(filePath, contentToWrite);
            System.out.println("\nSuccessfully wrote to '" + filePath.toAbsolutePath() + "'.");

            // Write string to file, appending to existing content
            Files.writeString(filePath, "\nThis line was appended.", StandardOpenOption.APPEND);
            System.out.println("Successfully appended to '" + filePath.toAbsolutePath() + "'.");

            // Write string to file with specific charset
            Path utf16FilePath = Path.of("sample_java11_utf16.txt");
            Files.writeString(utf16FilePath, "This is UTF-16 content.", StandardCharsets.UTF_16);
            System.out.println("Successfully wrote UTF-16 content to '" + utf16FilePath.toAbsolutePath() + "'.");

        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }

        // --- Reading from a file ---
        try {
            String readContent = Files.readString(filePath);
            System.out.println("\n--- Content read from '" + filePath.getFileName() + "' ---");
            System.out.println(readContent);
            System.out.println("----------------------------------------------");

            Path utf16FilePath = Path.of("sample_java11_utf16.txt");
            String readUtf16Content = Files.readString(utf16FilePath, StandardCharsets.UTF_16);
            System.out.println("\n--- Content read from '" + utf16FilePath.getFileName() + "' (UTF-16) ---");
            System.out.println(readUtf16Content);
            System.out.println("----------------------------------------------");

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } finally {
            // Clean up: delete the created files
            try {
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(Path.of("sample_java11_utf16.txt"));
                System.out.println("\nCleaned up test files.");
            } catch (IOException e) {
                System.err.println("Error during cleanup: " + e.getMessage());
            }
        }
    }
}
