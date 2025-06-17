package io.bmeurant.spring.boot32.features.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * Configuration for a basic OAuth 2.0 Authorization Server.
 * This example sets up a public client for demonstration purposes.
 * In a real application, you'd use secure client management and more robust key management.
 */
@Configuration
public class AuthorizationServerConfig {

    /**
     * Configures the security filter chain for the Authorization Server endpoints.
     * The order annotation ensures this chain is evaluated first.
     *
     * @param http HttpSecurity for configuration
     * @return The configured SecurityFilterChain
     * @throws Exception if configuration fails
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        authorizationServerConfigurer.oidc(Customizer.withDefaults());

        // Define the matchers for the Authorization Server endpoints
        RequestMatcher authorizationServerEndpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        http
                .securityMatcher(authorizationServerEndpointsMatcher)
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers(
                        authorizationServerEndpointsMatcher // Use the original endpoints matcher for CSRF ignoring
                ))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        // Apply the authorization server configurer AFTER securityMatcher
        http.apply(authorizationServerConfigurer);


        return http.build();
    }

    /**
     * Configures the default Spring Security filter chain for application security.
     * This will handle login and general access to the application endpoints.
     *
     * @param http HttpSecurity for configuration
     * @return The configured SecurityFilterChain
     * @throws Exception if configuration fails
     */
    @Bean
    @Order(2) // This ensures the default security chain is processed after the Authorization Server's
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated() // All requests to the application require authentication
                )
                .formLogin(Customizer.withDefaults()); // Enable form-based login

        return http.build();
    }

    /**
     * Configures the RegisteredClientRepository to manage OAuth2 clients.
     * This example uses an in-memory repository for a single public client.
     *
     * @return A RegisteredClientRepository instance
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        // A "public" client (without client secret) for demonstration
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("public-client") // Client ID
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE) // Public client (no secret)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("https://127.0.0.1:8443/authorized") // Callback URI after authorization
                .postLogoutRedirectUri("https://127.0.0.1:8443/logged-out")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("message.read") // Custom scope
                .scope("message.write") // Custom scope
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build()) // No consent screen required
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    /**
     * Configures the UserDetailsService for managing user details (for login).
     * This example uses an in-memory user store.
     *
     * @return A UserDetailsService instance
     */
    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.withUsername("user")
                .password(encoder.encode("password"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    /**
     * Configures the JWKSource for signing tokens.
     * This example generates an RSA key pair in memory for simplicity.
     * In production, you'd load keys securely.
     *
     * @return A JWKSource instance
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    /**
     * Generates an RSA key pair.
     * @return A KeyPair instance
     */
    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    /**
     * Configures the AuthorizationServerSettings for the Authorization Server.
     *
     * @return An AuthorizationServerSettings instance
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build(); // Uses default settings
    }
}
