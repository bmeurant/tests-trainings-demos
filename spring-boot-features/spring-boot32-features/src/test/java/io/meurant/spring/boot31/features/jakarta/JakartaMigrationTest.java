package io.meurant.spring.boot32.features.jakarta;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JakartaMigrationTest {

    @Test
    void shouldBeAbleToLoadJakartaPersistenceClass() {
        assertDoesNotThrow(() -> Class.forName("jakarta.persistence.Entity"),
                "Jakarta persistence Entity class should be loadable.");
    }

    @Test
    void shouldNotBeAbleToLoadJavaxPersistenceClass() {
        assertThrows(ClassNotFoundException.class, () -> Class.forName("javax.persistence.Entity"),
                "Javax persistence Entity class should NOT be loadable.");
    }

    @Test
    void shouldBeAbleToLoadJakartaAnnotationClass() {
        assertDoesNotThrow(() -> Class.forName("jakarta.annotation.PostConstruct"),
                "Jakarta annotation PostConstruct class should be loadable.");
    }

    @Test
    void shouldNotBeAbleToLoadJavaxAnnotationClass() {
        assertThrows(ClassNotFoundException.class, () -> Class.forName("javax.annotation.PostConstruct"),
                "Javax annotation PostConstruct class should NOT be loadable.");
    }
}
