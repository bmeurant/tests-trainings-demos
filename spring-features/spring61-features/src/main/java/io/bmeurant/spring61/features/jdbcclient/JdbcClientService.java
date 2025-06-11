package io.bmeurant.spring61.features.jdbcclient;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class JdbcClientService {
    private static final Logger logger = Logger.getLogger(JdbcClientService.class.getName());
    private final ProductRepository productRepository;

    public JdbcClientService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // @PostConstruct is commented out because we will call runJdbcClientDemo() explicitly from main for order control.
    // In a typical Spring application, it would be active.
    // @PostConstruct
    public void runJdbcClientDemo() {
        logger.info("\n--- Starting JdbcClient Demo ---");

        try {
            // Use the JdbcClient from the repository to execute DDL
            productRepository.createProductsTable();
            logger.info("Products table created.");
        } catch (Exception e) {
            logger.warning("Table creation failed (might already exist): " + e.getMessage());
        }

        productRepository.save(new Product(null, "Laptop", 1200.00));
        productRepository.save(new Product(null, "Mouse", 25.00));
        productRepository.save(new Product(null, "Keyboard", 75.00));
        logger.info("Added 3 products.");

        List<Product> products = productRepository.findAll();
        logger.info("All products: " + products);

        Optional<Product> laptop = productRepository.findById(1);
        laptop.ifPresentOrElse(
                p -> logger.info("Found Laptop: " + p),
                () -> logger.info("Laptop not found.")
        );

        if (laptop.isPresent()) {
            Product updatedLaptop = new Product(laptop.get().id(), "Gaming Laptop", 1500.00);
            productRepository.update(updatedLaptop);
            logger.info("Updated Laptop.");
            productRepository.findById(1).ifPresent(p -> logger.info("Laptop after update: " + p));
        }

        long count = productRepository.count();
        logger.info("Total products: " + count);

        productRepository.deleteById(2);
        logger.info("Deleted product with ID 2.");

        products = productRepository.findAll();
        logger.info("Products after deletion: " + products);

        logger.info("--- JdbcClient Demo Finished ---\n");
    }
}
