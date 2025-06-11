// src/main/java/io/bmeurant/spring61/features/jdbcclient/ProductRepository.java
package io.bmeurant.spring61.features.jdbcclient;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Repository demonstrating the usage of Spring's new JdbcClient.
 */
@Repository
public class ProductRepository {

    private static final Logger logger = Logger.getLogger(ProductRepository.class.getName());
    private final JdbcClient jdbcClient;

    /**
     * Constructor for ProductRepository, injecting a JdbcClient instance.
     * Spring Boot auto-configures a JdbcClient bean if JDBC is enabled.
     */
    public ProductRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * Finds all products.
     *
     * @return A list of all products.
     */
    public List<Product> findAll() {
        logger.info("Finding all products using JdbcClient...");
        return jdbcClient.sql("SELECT id, name, price FROM products")
                .query(Product.class) // Maps rows directly to Product record
                .list();
    }

    /**
     * Finds a product by its ID.
     *
     * @param id The ID of the product.
     * @return An Optional containing the product if found.
     */
    public Optional<Product> findById(Integer id) {
        logger.info("Finding product by ID: " + id);
        return jdbcClient.sql("SELECT id, name, price FROM products WHERE id = :id")
                .param("id", id) // Use named parameters
                .query(Product.class)
                .optional(); // Returns an Optional
    }

    /**
     * Adds a new product to the database.
     *
     * @param product The product to add (ID can be null for auto-increment).
     * @return The number of rows affected.
     */
    public int save(Product product) {
        logger.info("Saving product: " + product);
        // Supports insert, update, delete operations
        return jdbcClient.sql("INSERT INTO products (name, price) VALUES (:name, :price)")
                .param("name", product.name())
                .param("price", product.price())
                .update();
    }

    /**
     * Updates an existing product.
     *
     * @param product The product to update.
     * @return The number of rows affected.
     */
    public int update(Product product) {
        logger.info("Updating product: " + product);
        return jdbcClient.sql("UPDATE products SET name = :name, price = :price WHERE id = :id")
                .param("name", product.name())
                .param("price", product.price())
                .param("id", product.id())
                .update();
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     * @return The number of rows affected.
     */
    public int deleteById(Integer id) {
        logger.info("Deleting product by ID: " + id);
        return jdbcClient.sql("DELETE FROM products WHERE id = :id")
                .param("id", id)
                .update();
    }

    /**
     * Counts the total number of products.
     *
     * @return The total count.
     */
    public long count() {
        logger.info("Counting products...");
        return jdbcClient.sql("SELECT COUNT(*) FROM products")
                .query(Long.class) // Query for a single value, mapped to Long
                .single();
    }
}