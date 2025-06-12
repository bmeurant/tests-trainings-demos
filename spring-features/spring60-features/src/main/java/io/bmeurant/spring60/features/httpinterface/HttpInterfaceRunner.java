package io.bmeurant.spring60.features.httpinterface;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.logging.Logger;

/**
 * Service demonstrating the use of HTTP Interfaces.
 * Configures and uses MyApiClient to make HTTP calls.
 */
@Component
public class HttpInterfaceRunner {
    private static final Logger logger = Logger.getLogger(HttpInterfaceRunner.class.getName());

    private final HttpInterfaceClient httpInterfaceClient;

    public HttpInterfaceRunner(HttpInterfaceClient httpInterfaceClient) {
        this.httpInterfaceClient = httpInterfaceClient;
    }

    /**
     * Runs the HTTP Interface demonstration by making sample API calls.
     * This method is now called explicitly from MainApplication.
     */
    public void runHttpInterfaceDemo() {
        logger.info("Calling HTTP Interface demo...");

        // Example: Get a post by ID
        httpInterfaceClient.getPostById(1)
                .doOnNext(post -> logger.info("Fetched Post: " + post))
                .doOnError(error -> logger.severe("Error fetching post: " + error.getMessage()))
                .block(); // Block for demo purposes, use reactive patterns in real applications

        // Example: Create a new post
        PostData newPost = new PostData(null, "Spring 6.0 HTTP Interfaces Demo", "Declarative client in action.", 1);
        httpInterfaceClient.createPost(newPost)
                .doOnNext(created -> logger.info("Created Post: " + created))
                .doOnError(error -> logger.severe("Error creating post: " + error.getMessage()))
                .block();
    }

    /**
     * Configuration class for HTTP Interfaces.
     * This defines the WebClient and the HttpServiceProxyFactory to create the MyApiClient bean.
     */
    @Configuration
    static class HttpInterfaceConfig {

        private static final Logger configLogger = Logger.getLogger(HttpInterfaceConfig.class.getName());

        @Bean
        public WebClient jsonPlaceholderWebClient() {
            configLogger.info("Creating WebClient for JSONPlaceholder.");
            return WebClient.builder()
                    .baseUrl("https://jsonplaceholder.typicode.com") // A public API for testing
                    .build();
        }

        @Bean
        public HttpServiceProxyFactory httpServiceProxyFactory(WebClient jsonPlaceholderWebClient) {
            configLogger.info("Creating HttpServiceProxyFactory.");
            return HttpServiceProxyFactory.builder()
                    .clientAdapter(WebClientAdapter.forClient(jsonPlaceholderWebClient))
                    .build();
        }

        @Bean
        public HttpInterfaceClient myApiClient(HttpServiceProxyFactory factory) {
            configLogger.info("Creating MyApiClient proxy.");
            return factory.createClient(HttpInterfaceClient.class);
        }
    }
}
