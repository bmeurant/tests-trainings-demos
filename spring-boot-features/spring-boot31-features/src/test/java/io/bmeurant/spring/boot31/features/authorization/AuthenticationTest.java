package io.bmeurant.spring.boot31.features.authorization;

import io.bmeurant.spring.boot31.features.SpringBoot31Application;
import io.bmeurant.spring.boot31.features.config.AuthorizationServerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;

/**
 * Integration tests for authentication mechanisms, especially focusing on form login
 * and basic OAuth2 Authorization Code flow initiation.
 */
@SpringBootTest(classes = {SpringBoot31Application.class, AuthorizationServerConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"debug=true", "logging.level.org.springframework.security=DEBUG", "logging.level.org.springframework.boot.autoconfigure.security=DEBUG"})
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class AuthenticationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void contextLoads() {
        // basic Spring Boot context test
        assertThat(port).isGreaterThan(0);
    }

    @Test
    void accessToProtectedResourceRedirectsToLogin() {
        webTestClient.get().uri("/api/v1/hello")
                .exchange()
                .expectStatus().isFound()
                .expectHeader().value("Location", containsString("/login"));
    }

    @Test
    void authorizationEndpointRedirectsToLoginWhenUnauthenticated() {
        webTestClient.get().uri("/oauth2/authorize?response_type=code&client_id=public-client&scope=openid&redirect_uri=https://127.0.0.1:8443/authorized")
                .exchange()
                .expectStatus().isFound()
                .expectHeader().value("Location", containsString("/login"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void oauth2AuthorizationEndpointShouldReturnRedirect() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth2/authorize")
                        .queryParam("response_type", "code")
                        .queryParam("client_id", "public-client")
                        .queryParam("redirect_uri", "https://127.0.0.1:8443/authorized")
                        .queryParam("scope", "openid")
                        .queryParam("state", "1234")
                        .build()
                )
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().exists("Location");
    }

    @Test
    void loginPageShouldBeAccessible() {
        webTestClient.get()
                .uri("/login")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(body -> assertThat(body).contains("username", "password"));
    }

}