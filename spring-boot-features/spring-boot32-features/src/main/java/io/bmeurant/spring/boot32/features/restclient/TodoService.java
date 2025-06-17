package io.bmeurant.spring.boot32.features.restclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Service to interact with an external Todo API using Spring's new RestClient.
 */
@Service
public class TodoService {

    private final RestClient restClient;

    // Define a simple record for the Todo item (requires Java 16+)
    // Records are a concise way to declare immutable data classes.
    public record Todo(Long id, Long userId, String title, boolean completed) {}

    /**
     * Constructor for TodoService.
     * Spring Boot automatically provides a pre-configured RestClient.Builder.
     *
     * @param restClientBuilder The autoconfigured RestClient.Builder.
     * @param externalApiUrl The base URL for the external API, injected from application.properties.
     */
    public TodoService(RestClient.Builder restClientBuilder, @Value("${external.api.url}") String externalApiUrl) {
        // Build the RestClient instance, setting the base URL for this specific client.
        this.restClient = restClientBuilder
                .baseUrl(externalApiUrl)
                .build();
    }

    /**
     * Fetches a Todo item by its ID from the external API.
     *
     * @param id The ID of the Todo item.
     * @return The Todo object.
     */
    public Todo getTodoById(Long id) {
        return restClient.get()                         // Start a GET request
                .uri("/todos/{id}", id)             // Set the URI path with a variable
                .retrieve()                             // Execute the request and retrieve the response
                .body(Todo.class);                      // Convert the response body to a Todo object
    }

    /**
     * Creates a new Todo item by sending a POST request to the external API.
     *
     * @param newTodo The new Todo object to create (ID is usually null as it's assigned by the server).
     * @return The created Todo object (with the ID assigned by the server).
     */
    public Todo createTodo(Todo newTodo) {
        return restClient.post()                        // Start a POST request
                .uri("/todos")                      // Set the URI path
                .body(newTodo)                          // Set the request body (will be serialized to JSON)
                .retrieve()                             // Execute and retrieve
                .body(Todo.class);                      // Convert response body to a Todo object
    }
}
