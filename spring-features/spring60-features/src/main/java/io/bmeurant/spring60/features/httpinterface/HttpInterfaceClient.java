package io.bmeurant.spring60.features.httpinterface;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

/**
 * Declarative HTTP client interface using @HttpExchange.
 * Spring 6.0 provides an implementation for this interface using WebClient.
 */
public interface HttpInterfaceClient {
    @GetExchange("/posts/{id}")
    Mono<PostData> getPostById(@PathVariable("id") Integer id);

    @PostExchange("/posts")
    Mono<PostData> createPost(@RequestBody PostData postData);

    @GetExchange("/posts")
    Mono<PostData[]> getAllPosts();
}
