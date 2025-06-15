package io.bmeurant.spring.boot30.features.controller;

import io.bmeurant.spring.boot30.features.model.Product;
import io.bmeurant.spring.boot30.features.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/v1")
public class DemoController {

    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void initData() {
        productRepository.save(new Product(null, "Laptop", 1200.00));
        productRepository.save(new Product(null, "Mouse", 25.00));
        productRepository.save(new Product(null, "Keyboard", 75.00));
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Spring Boot 2.7.18!";
    }

    @GetMapping("/products")
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
