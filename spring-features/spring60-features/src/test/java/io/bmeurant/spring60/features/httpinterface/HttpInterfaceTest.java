package io.bmeurant.spring60.features.httpinterface;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for Spring Framework 6.0's HTTP Interfaces.
 * Uses MockWebServer to simulate a backend HTTP server.
 */
@SpringJUnitConfig(HttpInterfaceTest.TestConfig.class)
public class HttpInterfaceTest {

    @Autowired
    private HttpInterfaceClient httpInterfaceClient;

    private static MockWebServer mockWebServer;

    @BeforeAll // This method runs once before all tests in the class
    static void setup() throws IOException { // Must be static
        mockWebServer = new MockWebServer();
        mockWebServer.start(); // Start the mock server on a random port
    }

    @AfterAll // This method runs once after all tests in the class
    static void tearDown() throws IOException { // Must be static
        if (mockWebServer != null) {
            mockWebServer.shutdown(); // Shut down the mock server
        }
    }

    @Test
    void testGetPostById() throws Exception {
        // Prepare a mock response
        String jsonResponse = "{\"id\":1, \"title\":\"Test Post\", \"body\":\"Some content\", \"userId\":10}";
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(jsonResponse));

        // Call the HTTP Interface
        PostData post = httpInterfaceClient.getPostById(1).block();

        assertNotNull(post);
        assertEquals(1, post.id());
        assertEquals("Test Post", post.title());
        assertEquals("Some content", post.body());
        assertEquals(10, post.userId());

        // Verify that the correct request was made
        assertEquals("/posts/1", mockWebServer.takeRequest().getPath());
    }

    @Test
    void testCreatePost() throws Exception {
        // Prepare a mock response for a created post
        String jsonRequest = new ObjectMapper().writeValueAsString(new PostData(null, "New Title", "New Body", 5));
        String jsonResponse = "{\"id\":101, \"title\":\"New Title\", \"body\":\"New Body\", \"userId\":5}";
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(201) // Created
                .setHeader("Content-Type", "application/json")
                .setBody(jsonResponse));

        // Call the HTTP Interface to create a post
        PostData newPost = new PostData(null, "New Title", "New Body", 5);
        PostData createdPost = httpInterfaceClient.createPost(newPost).block();

        assertNotNull(createdPost);
        assertEquals(101, createdPost.id());
        assertEquals("New Title", createdPost.title());

        // Call takeRequest() ONLY ONCE to get the recorded request
        okhttp3.mockwebserver.RecordedRequest recordedRequest = mockWebServer.takeRequest(); // This consumes the ONE request

        // Now, assert on the properties of that single recorded request
        assertEquals("/posts", recordedRequest.getPath());
        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("application/json", recordedRequest.getHeader("Content-Type")); // Also good to verify content type of request
        assertEquals(jsonRequest, recordedRequest.getBody().readUtf8());
    }

    @Configuration
    static class TestConfig {
        @Bean
        public WebClient webClient() {
            // Configure WebClient to point to the mock server's URL
            return WebClient.builder()
                    .baseUrl(mockWebServer.url("/").toString())
                    .build();
        }

        @Bean
        public HttpServiceProxyFactory httpServiceProxyFactory(WebClient webClient) {
            return HttpServiceProxyFactory.builder()
                    .clientAdapter(WebClientAdapter.forClient(webClient))
                    .build();
        }

        @Bean
        public HttpInterfaceClient myHttpInterfaceClient(HttpServiceProxyFactory factory) {
            return factory.createClient(HttpInterfaceClient.class);
        }
    }
}
