package io.bmeurant.microservices.spring.productservice.controller;

import io.bmeurant.microservices.spring.productservice.model.Product;
import io.bmeurant.microservices.spring.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody Product product) {
        productRepository.save(product);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable("id") Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Value("${product-service.message}")
    private String messageFromConfigServer;

    @GetMapping("/message")
    @ResponseStatus(HttpStatus.OK)
    public String getMessage() {
        return messageFromConfigServer;
    }

    @RestController
    @RequestMapping("/api/products/init")
    public static class ProductInitController {

        private final ProductRepository productRepository;

        public ProductInitController(ProductRepository productRepository) {
            this.productRepository = productRepository;
        }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public void initProducts() {
            if (productRepository.count() == 0) {
                productRepository.save(Product.builder().name("Laptop HP").description("Laptop gaming").price(new BigDecimal("1200.00")).build());
                productRepository.save(Product.builder().name("Mouse Logitech").description("Souris sans fil").price(new BigDecimal("50.00")).build());
                productRepository.save(Product.builder().name("Keyboard Razer").description("Clavier mécanique").price(new BigDecimal("150.00")).build());
                productRepository.save(Product.builder().name("Monitor Dell").description("Écran 27 pouces").price(new BigDecimal("300.00")).build());
            }
        }
    }
}