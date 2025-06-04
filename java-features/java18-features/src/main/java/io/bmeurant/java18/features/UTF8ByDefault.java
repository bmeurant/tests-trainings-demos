package io.bmeurant.java18.features;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class UTF8ByDefault {
    private static final Path FILE_PATH = Path.of("utf8_demo.txt");

    public static void main(String[] args) {
        System.out.println("\n+++ Example 1: Show default charset in Java 18 +++");
        System.out.println("\nDefault Charset in Java 18: " + Charset.defaultCharset().name()); // Should be UTF-8

        String content = "Hello, Java 18! Caractères spéciaux: éàçüöñ";

        System.out.println("\n+++ Example 2: Write content to a file using default charset (UTF-8) +++");
        try {
            Files.writeString(FILE_PATH, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("\nSuccessfully wrote to " + FILE_PATH + " with default encoding.");
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }

        System.out.println("\n+++ Example 3: Read content from the file using default charset (UTF-8) +++");
        try {
            String readContent = Files.readString(FILE_PATH);
            System.out.println("\nSuccessfully read from " + FILE_PATH + " with default encoding.");
            System.out.println("Read Content: " + readContent);

            // Verify content integrity
            if (content.equals(readContent)) {
                System.out.println("Content matches! UTF-8 handling is correct.");
            } else {
                System.out.println("Content mismatch! There might be an encoding issue (unlikely in Java 18).");
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } finally {
            // Clean up the created file
            try {
                Files.deleteIfExists(FILE_PATH);
                System.out.println("Cleaned up " + FILE_PATH);
            } catch (IOException e) {
                System.err.println("Error cleaning up file: " + e.getMessage());
            }
        }

        System.out.println("\nPrior to Java 18, this behavior depended on the OS default charset (e.g., Windows-1252 on Windows).");
        System.out.println("Now, UTF-8 is the consistent default across platforms.");
    }
}
