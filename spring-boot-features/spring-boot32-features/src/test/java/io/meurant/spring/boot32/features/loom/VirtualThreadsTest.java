package io.meurant.spring.boot32.features.loom;

import io.bmeurant.spring.boot32.features.SpringBoot32Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SpringBoot32Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class VirtualThreadsTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldUseVirtualThreadToHandleRequest() {
        webTestClient.get()
                .uri("/api/v1/check-thread")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(body -> {
                    System.out.println("Thread info: " + body);
                    assertThat(body).contains("isVirtual=true");
                });
    }
}

