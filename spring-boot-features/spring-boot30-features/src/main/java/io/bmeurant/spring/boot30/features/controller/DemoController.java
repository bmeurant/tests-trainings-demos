package io.bmeurant.spring.boot30.features.controller;

import io.bmeurant.spring.boot30.features.model.Product;
import io.bmeurant.spring.boot30.features.service.ProductService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1")
public class DemoController {

    private static final Logger log = LoggerFactory.getLogger(DemoController.class);

    private final ProductService productService; // Use ProductService

    public DemoController(ProductService productService) {
        this.productService = productService;
    }

    @PostConstruct
    public void initData() {
        log.info("Initializing product data...");
        productService.addProduct(new Product(null, "Laptop", 1200.00));
        productService.addProduct(new Product(null, "Mouse", 25.00));
        productService.addProduct(new Product(null, "Keyboard", 75.00));
        log.info("Product data initialized.");
    }

    @GetMapping("/hello")
    public String sayHello() {
        log.info("Received request for /api/v1/hello"); // Log with trace context
        return "Hello from Spring Boot 3.0.13!";
    }

    @GetMapping("/products")
    public Iterable<Product> getAllProducts() {
        log.info("Fetching all products via service.");
        return productService.findAllProducts(); // Call service method
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        log.info("Creating new product: {}", product.getName());
        return productService.addProduct(product);
    }

    @GetMapping("/long-operation")
    public String longOperation() throws InterruptedException {
        log.info("Starting long operation - Thread: {}", Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(2); // Simulate a blocking I/O operation
        log.info("Finished long operation - Thread: {}", Thread.currentThread().getName());
        return "Long operation completed with Virtual Thread in Spring Boot 3.0.13!";
    }
}
