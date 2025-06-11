package io.bmeurant.spring61.features.jdbcclient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test for ProductRepository using JdbcClient.
 * `@SpringJUnitConfig` loads the Spring application context defined in AppConfig.
 * `@Transactional` ensures each test runs in its own transaction and is rolled back.
 */
@SpringJUnitConfig(JdbcClientConfig.class) // Load the Spring context from JdbcClientConfig
@Transactional // Ensure tests are transactional and roll back changes
public class JdbcClientTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JdbcClient jdbcClient; // Directly inject JdbcClient for setup/cleanup

    @BeforeEach
    void setupDatabase() {
        // Drop and create table to ensure clean state for each test
        jdbcClient.sql("DROP TABLE IF EXISTS products").update();
        jdbcClient.sql("""
                CREATE TABLE products (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    price DOUBLE NOT NULL
                )
                """).update();
    }

    @Test
    void testSaveAndFindById() {
        Product newProduct = new Product(null, "Monitor", 300.00);
        int affectedRows = productRepository.save(newProduct);
        assertEquals(1, affectedRows);

        Optional<Product> foundProduct = productRepository.findById(1);
        assertTrue(foundProduct.isPresent());
        assertEquals("Monitor", foundProduct.get().name());
        assertEquals(300.00, foundProduct.get().price());
    }

    @Test
    void testFindAll() {
        productRepository.save(new Product(null, "Laptop", 1200.00));
        productRepository.save(new Product(null, "Mouse", 25.00));

        List<Product> products = productRepository.findAll();
        assertEquals(2, products.size());
    }

    @Test
    void testUpdate() {
        productRepository.save(new Product(null, "Webcam", 50.00));
        Product original = productRepository.findById(1).orElseThrow();
        Product updated = new Product(original.id(), "High-Res Webcam", 75.00);

        int affectedRows = productRepository.update(updated);
        assertEquals(1, affectedRows);
    }

    @Test
    void testDeleteById() {
        productRepository.save(new Product(null, "Speaker", 100.00));
        assertEquals(1, productRepository.count());

        int affectedRows = productRepository.deleteById(1);
        assertEquals(1, affectedRows);
        assertEquals(0, productRepository.count());
    }

    @Test
    void testCount() {
        assertEquals(0, productRepository.count());
        productRepository.save(new Product(null, "Desk", 200.00));
        assertEquals(1, productRepository.count());
    }
}