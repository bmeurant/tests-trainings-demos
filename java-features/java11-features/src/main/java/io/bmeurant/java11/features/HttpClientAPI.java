package io.bmeurant.java11.features;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class HttpClientAPI {

    public static void main(String[] args) {

        // --- 1. Synchronous GET Request ---
        System.out.println("\n+++ Example 1: Synchronous GET Request +++\n");

        HttpClient client = HttpClient.newHttpClient(); // Create a client with default settings
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/todos/1"))
                .timeout(Duration.ofSeconds(10)) // Set a timeout
                .GET() // Default is GET, but explicit for clarity
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response Body:\n" + response.body().substring(0, Math.min(response.body().length(), 200)) + "..."); // Print first 200 chars
        } catch (IOException | InterruptedException e) {
            System.err.println("Error during synchronous GET request: " + e.getMessage());
            e.printStackTrace();
        }

        // --- 2. Asynchronous POST Request ---
        System.out.println("\n+++ Example 2: Asynchronous POST Request +++\n");
        String jsonBody = "{\"title\": \"foo\", \"body\": \"bar\", \"userId\": 1}";
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/posts"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)) // Set request body as String
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse =
                client.sendAsync(postRequest, HttpResponse.BodyHandlers.ofString());

        // Non-blocking operation: process the response when it arrives
        futureResponse.thenAccept(response -> {
            System.out.println("Async POST Status Code: " + response.statusCode());
            System.out.println("Async POST Response Body:\n" + response.body().substring(0, Math.min(response.body().length(), 200)) + "...");
        }).exceptionally(e -> {
            System.err.println("Error during asynchronous POST request: " + e.getMessage());
            return null;
        });

        // To ensure the main thread waits for the async operation to complete
        // In a real application, you might chain other CompletableFuture or use join()
        // to wait at a specific point if needed.
        try {
            futureResponse.join(); // Wait for the async operation to complete for demonstration
        } catch (Exception e) {
            // Exception already handled in .exceptionally()
        }

        // --- 3. Client with custom configuration ---
        System.out.println("\n+++ Example 3: Client with custom configuration +++\n");
        HttpClient customClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2) // Prefer HTTP/2
                .followRedirects(HttpClient.Redirect.NORMAL) // Follow redirects
                .connectTimeout(Duration.ofSeconds(5)) // Connection timeout
                .build();

        HttpRequest githubRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/zen")) // A fun GitHub API
                .GET()
                .build();

        try {
            HttpResponse<String> githubResponse = customClient.send(githubRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("GitHub Zen Status Code: " + githubResponse.statusCode());
            System.out.println("GitHub Zen Quote:\n" + githubResponse.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error during GitHub request: " + e.getMessage());
            e.printStackTrace();
        }
    }
}