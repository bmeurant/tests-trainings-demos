package io.meurant.spring.boot32.features.restclient;

import io.bmeurant.spring.boot32.features.SpringBoot32Application;
import io.bmeurant.spring.boot32.features.restclient.TodoService;
import io.bmeurant.spring.boot32.features.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for TodoService using @RestClientTest and MockRestServiceServer
 * to simulate external API responses.
 */
@RestClientTest(TodoService.class)
@ContextConfiguration(classes = SpringBoot32Application.class)
public class RestClientFeatureTest {

    @Autowired
    private TodoService todoService; // The service under test

    @Autowired
    private MockRestServiceServer mockServer; // Injected by @RestClientTest to configure mock responses

    @Value("${external.api.url}") // <-- Inject the base URL from application.properties
    private String externalApiUrl;

    @BeforeEach
    void setup() {
        // Reset the mock server before each test to ensure a clean state
        this.mockServer.reset();
    }

    @Test
    void testGetTodoById() {
        // 1. Arrange: Define the expected JSON response from the external API
        String expectedResponseBody = """
                {
                    "userId": 1,
                    "id": 1,
                    "title": "delectus aut autem",
                    "completed": false
                }
                """;

        // Configure MockRestServiceServer to expect a GET request to "/todos/1"
        // and respond with the predefined JSON and HTTP 200 OK.
        mockServer.expect(MockRestRequestMatchers.requestTo(externalApiUrl + "/todos/1")) // <-- MODIFIED LINE
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(expectedResponseBody, MediaType.APPLICATION_JSON));

        // 2. Act: Call the service method that uses RestClient
        TodoService.Todo todo = todoService.getTodoById(1L);

        // 3. Assert: Verify the returned object and that all expectations on the mock server were met
        assertThat(todo).isNotNull();
        assertThat(todo.id()).isEqualTo(1L);
        assertThat(todo.userId()).isEqualTo(1L);
        assertThat(todo.title()).isEqualTo("delectus aut autem");
        assertThat(todo.completed()).isFalse();

        mockServer.verify(); // Crucial: verifies that all expected requests were made
    }

    @Test
    void testCreateTodo() {
        // 1. Arrange: Define the Todo object to be sent and its expected response after creation
        TodoService.Todo newTodo = new TodoService.Todo(null, 2L, "Test new todo for creation", false);

        // Define the expected JSON response for a POST request (simulating the created resource with an ID)
        String expectedResponseBody = """
                {
                    "userId": 2,
                    "id": 201,
                    "title": "Test new todo for creation",
                    "completed": false
                }
                """;

        // Configure MockRestServiceServer to expect a POST request to "/todos"
        // and verify its content. Then, respond with a 201 Created status and the defined JSON.
        mockServer.expect(MockRestRequestMatchers.requestTo(externalApiUrl + "/todos")) // <-- MODIFIED LINE
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andExpect(MockRestRequestMatchers.content().json("""
                      {
                          "userId": 2,
                          "title": "Test new todo for creation",
                          "completed": false
                      }
                      """)) // Verify the request body sent by RestClient
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.CREATED)
                        .body(expectedResponseBody)
                        .contentType(MediaType.APPLICATION_JSON));

        // 2. Act: Call the service method to create a new Todo
        TodoService.Todo createdTodo = todoService.createTodo(newTodo);

        // 3. Assert: Verify the returned object and that all expectations were met
        assertThat(createdTodo).isNotNull();
        assertThat(createdTodo.id()).isEqualTo(201L); // Expect an ID from the mock server
        assertThat(createdTodo.title()).isEqualTo("Test new todo for creation");
        assertThat(createdTodo.userId()).isEqualTo(2L);
        assertThat(createdTodo.completed()).isFalse();

        mockServer.verify(); // Crucial: verifies that all expected requests were made
    }
}
