package io.bmeurant.spring.boot32.features.jdbcclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductRecordService {

    private static final Logger log = LoggerFactory.getLogger(ProductRecordService.class);

    private final JdbcClient jdbcClient;

    /**
     * Constructor for ProductJdbcClientService, injecting JdbcClient.
     * Spring Boot automatically configures JdbcClient if JdbcTemplate is on the classpath.
     * @param jdbcClient The auto-configured JdbcClient instance.
     */
    public ProductRecordService(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * Finds all products using JdbcClient.
     * Maps results directly to ProductRecord.
     * @return A list of all products.
     */
    public List<ProductRecord> findAll() {
        log.info("JdbcClient: Fetching all products.");
        return jdbcClient.sql("SELECT id, name, price FROM product")
                .query(ProductRecord.class) // Map directly to a record
                .list();
    }

    /**
     * Finds a product by its ID.
     * @param id The ID of the product to find.
     * @return An Optional containing the ProductRecord if found, empty otherwise.
     */
    public Optional<ProductRecord> findById(Long id) {
        log.info("JdbcClient: Fetching product by ID: {}", id);
        return jdbcClient.sql("SELECT id, name, price FROM product WHERE id = :id")
                .param("id", id) // Use named parameters
                .query(ProductRecord.class)
                .optional();
    }

    /**
     * Saves a new product or updates an existing one.
     * Demonstrates insert/update logic and returning a generated key for inserts.
     * @param name The name of the product.
     * @param price The price of the product.
     * @return The ID of the saved/updated product.
     */
    @Transactional
    public Long save(String name, BigDecimal price) {
        log.info("JdbcClient: Saving product: {} with price {}", name, price);
        // Check if product with this name already exists for update scenario
        Optional<Long> existingId = jdbcClient.sql("SELECT id FROM product WHERE name = :name")
                .param("name", name)
                .query(Long.class)
                .optional();

        if (existingId.isPresent()) {
            // Update existing product
            jdbcClient.sql("UPDATE product SET price = :price WHERE id = :id")
                    .param("price", price)
                    .param("id", existingId.get())
                    .update();
            log.info("JdbcClient: Updated product with ID: {}", existingId.get());
            return existingId.get();
        } else {
            // Insert new product and retrieve generated key
            // Use GeneratedKeyHolder to get the generated key
            KeyHolder keyHolder = new GeneratedKeyHolder();

            int rowsAffected = jdbcClient.sql("INSERT INTO product (name, price) VALUES (:name, :price)")
                    .param("name", name)
                    .param("price", price)
                    .update(keyHolder); // Pass the KeyHolder to the update method

            if (rowsAffected > 0 && keyHolder.getKey() != null) {
                Long newId = keyHolder.getKey().longValue(); // Get the generated key
                log.info("JdbcClient: Inserted new product with ID: {}", newId);
                return newId;
            } else {
                throw new RuntimeException("Failed to insert new product or retrieve generated key.");
            }
        }
    }

    /**
     * Deletes a product by its ID.
     * @param id The ID of the product to delete.
     * @return The number of rows affected.
     */
    @Transactional
    public int deleteById(Long id) {
        log.info("JdbcClient: Deleting product with ID: {}", id);
        return jdbcClient.sql("DELETE FROM product WHERE id = :id")
                .param("id", id)
                .update();
    }

    /**
     * Counts the total number of products.
     * @return The count of products.
     */
    public Long count() {
        log.info("JdbcClient: Counting products.");
        return jdbcClient.sql("SELECT COUNT(*) FROM product")
                .query(Long.class)
                .single(); // Use single() for a single result
    }

    /**
     * Deletes all products.
     */
    @Transactional
    public void deleteAll() {
        log.info("JdbcClient: Deleting all products.");
        jdbcClient.sql("DELETE FROM product").update();
    }
}
