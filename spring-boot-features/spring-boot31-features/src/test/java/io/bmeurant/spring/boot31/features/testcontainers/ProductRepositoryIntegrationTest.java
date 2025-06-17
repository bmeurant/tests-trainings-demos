package io.bmeurant.spring.boot31.features.testcontainers;

import io.bmeurant.spring.boot31.features.SpringBoot31Application;
import io.bmeurant.spring.boot31.features.model.Product;
import io.bmeurant.spring.boot31.features.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = SpringBoot31Application.class)
public class ProductRepositoryIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldFindAllProducts() {
        Product product1 = new Product();
        product1.setName("Test Laptop");
        product1.setPrice(1200.0);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Test Mouse");
        product2.setPrice(25.0);
        productRepository.save(product2);

        List<Product> products = productRepository.findAll();

        assertThat(products).hasSize(2);
        assertThat(products).extracting(Product::getName).containsExactlyInAnyOrder("Test Laptop", "Test Mouse");
    }
}
