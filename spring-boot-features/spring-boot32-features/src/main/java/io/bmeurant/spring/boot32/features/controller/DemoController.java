package io.bmeurant.spring.boot32.features.controller;

import io.bmeurant.spring.boot32.features.model.Product;
import io.bmeurant.spring.boot32.features.restclient.TodoService;
import io.bmeurant.spring.boot32.features.service.ProductService;
import io.micrometer.observation.annotation.Observed;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1")
public class DemoController {

    private static final Logger log = LoggerFactory.getLogger(DemoController.class);

    private final ProductService productService;
    private final TodoService todoService;

    public DemoController(ProductService productService, TodoService todoService) {
        this.productService = productService;
        this.todoService = todoService;
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
        return "Hello from Spring Boot 3.2.12!";
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
        return "Long operation completed with Virtual Thread in Spring Boot 3.1.12!";
    }

    @GetMapping("/check-thread")
    public ResponseEntity<String> checkThread() {
        Thread thread = Thread.currentThread();
        return ResponseEntity.ok("Thread: " + thread + " | isVirtual=" + thread.isVirtual());
    }

    /**
     * New endpoint to fetch a Todo item from an external API using RestClient.
     * Access via: GET /api/v1/todos/{id} (e.g., /api/v1/todos/1)
     * Observability is automatically applied due to @Observed.
     */
    @GetMapping("/todos/{id}")
    @Observed(name = "todo.fetch", contextualName = "todo-api")
    public TodoService.Todo getTodo(@PathVariable Long id) {
        log.info("Get todo by ID endpoint called: {}", id);
        return todoService.getTodoById(id);
    }
}
