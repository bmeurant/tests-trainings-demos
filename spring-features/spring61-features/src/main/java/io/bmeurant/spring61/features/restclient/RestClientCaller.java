package io.bmeurant.spring61.features.restclient;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.logging.Logger;

@Service
public class RestClientCaller {

    private static final Logger logger = Logger.getLogger(RestClientCaller.class.getName());
    private final RestClient restClient;

    // Constructor modified: should take RestClient.Builder
    // to allow mock server to instrument it effectively
    public RestClientCaller(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public RestClientData fetchData(String message) {
        logger.info("Fetching data with message: " + message);
        RestClientData data = restClient.get()
                .uri("/api/data?msg={message}", message)
                .retrieve()
                .body(RestClientData.class);
        logger.info("Data fetched: " + data);
        return data;
    }

    public String fetchStatus() {
        logger.info("Fetching service status.");
        String status = restClient.get()
                .uri("/api/status")
                .retrieve()
                .body(String.class);
        logger.info("Service status: " + status);
        return status;
    }

    public HttpStatus checkStatus() {
        logger.info("Checking service status code.");
        HttpStatusCode statusCode = restClient.get()
                .uri("/api/status")
                .retrieve()
                .toBodilessEntity()
                .getStatusCode();
        HttpStatus status = HttpStatus.resolve(statusCode.value());
        logger.info("Service status code: " + status);
        return status;
    }
}
