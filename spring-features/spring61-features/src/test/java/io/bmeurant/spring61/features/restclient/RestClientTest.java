package io.bmeurant.spring61.features.restclient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RestClientTest {

    private static final Logger logger = Logger.getLogger(RestClientTest.class.getName());

    @Autowired
    private RestClient.Builder restClientBuilder; // Spring Boot auto-configures a RestClient.Builder

    private RestClientCaller restClientCaller;
    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setup() {
        // 1. Bind MockRestServiceServer to the RestClient.Builder.
        // This instruments the builder so any RestClient created from it will be mocked.
        mockServer = MockRestServiceServer.bindTo(restClientBuilder).build();

        // 2. Instantiate your RestClientCaller, passing the instrumented builder.
        // The RestClientCaller will internally build its RestClient from this builder.
        this.restClientCaller = new RestClientCaller(restClientBuilder);
        logger.info("RestClientCaller setup with MockRestServiceServer-bound RestClient.");
    }

    @Test
    void testFetchData() throws IOException {
        logger.info("Running testFetchData...");
        // CORRECTED LINE: Encode the space in the expected URI
        mockServer.expect(MockRestRequestMatchers.requestTo("http://localhost:8080/api/data?msg=Test%20message"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"message\":\"Mocked message\", \"value\":456}"));

        RestClientData data = restClientCaller.fetchData("Test message");
        assertNotNull(data, "Returned data should not be null");
        assertEquals("Mocked message", data.message(), "Message should match the mocked response");
        assertEquals(456, data.value(), "Value should match the mocked response");

        mockServer.verify();
        logger.info("testFetchData finished successfully.");
    }

    @Test
    void testFetchStatus() throws IOException {
        logger.info("Running testFetchStatus...");
        mockServer.expect(MockRestRequestMatchers.requestTo("http://localhost:8080/api/status"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.TEXT_PLAIN)
                        .body("Service is up!"));

        String status = restClientCaller.fetchStatus();
        assertEquals("Service is up!", status, "Status should match the mocked response");

        mockServer.verify();
        logger.info("testFetchStatus finished successfully.");
    }

    @Test
    void testCheckStatusReturnsOk() throws IOException {
        logger.info("Running testCheckStatusReturnsOk...");
        mockServer.expect(MockRestRequestMatchers.requestTo("http://localhost:8080/api/status"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK));

        HttpStatus status = restClientCaller.checkStatus();
        assertEquals(HttpStatus.OK, status, "HTTP Status should be OK");

        mockServer.verify();
        logger.info("testCheckStatusReturnsOk finished successfully.");
    }
}