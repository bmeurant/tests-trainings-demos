package io.bmeurant.spring.boot32.features.jdbcclient;

import io.bmeurant.spring.boot32.features.model.Product;
import io.bmeurant.spring.boot32.features.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(JdbcRunner.class);

    private final ProductService productService;
    private final ProductRecordService productRecordService; // Inject the new service

    @Autowired
    public JdbcRunner(ProductService productService, ProductRecordService productRecordService) {
        this.productService = productService;
        this.productRecordService = productRecordService;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing product data...");
        // Keep existing product service initialization
        productService.addProduct(new Product(null, "Laptop", 1200.0));
        productService.addProduct(new Product(null, "Mouse", 25.0));
        productService.addProduct(new Product(null, "Keyboard", 75.0));
        log.info("Product data initialized.");

        // --- Demonstrating JdbcClient usage ---
        log.info("\n--- Demonstrating JdbcClient (Spring Boot 3.2+) ---");

        // 1. Find all products using JdbcClient
        List<ProductRecord> allProducts = productRecordService.findAll();
        log.info("JdbcClient: All products found: {}", allProducts);

        // 2. Find a product by ID (e.g., the first one added by JPA)
        Optional<Product> firstProductOpt = productService.getProductById(1L);
        firstProductOpt.ifPresent(product -> {
            Optional<ProductRecord> foundProduct = productRecordService.findById(product.getId());
            foundProduct.ifPresent(p -> log.info("JdbcClient: Found product by ID {}: {}", p.id(), p));
        });

        // 3. Save a new product using JdbcClient
        Long newProductId = productRecordService.save("Webcam", new BigDecimal("50.00"));
        log.info("JdbcClient: Saved new product with ID: {}", newProductId);

        // 4. Update an existing product using JdbcClient (by name, for example)
        productRecordService.save("Webcam", new BigDecimal("45.00")); // Update existing "Webcam"
        Optional<ProductRecord> updatedWebcam = productRecordService.findById(newProductId);
        updatedWebcam.ifPresent(p -> log.info("JdbcClient: Updated Webcam price: {}", p));

        // 5. Count products using JdbcClient
        Long productCount = productRecordService.count();
        log.info("JdbcClient: Total products count: {}", productCount);

        // 6. Delete a product using JdbcClient
        if (newProductId != null) {
            int deletedRows = productRecordService.deleteById(newProductId);
            log.info("JdbcClient: Deleted {} product(s) with ID {}.", deletedRows, newProductId);
        }

        // 7. Verify count after deletion
        productCount = productRecordService.count();
        log.info("JdbcClient: Total products count after deletion: {}", productCount);

        // --- End of JdbcClient demonstration ---
    }
}
