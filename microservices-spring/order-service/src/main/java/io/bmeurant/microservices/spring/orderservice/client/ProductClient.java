package io.bmeurant.microservices.spring.orderservice.client;

import io.bmeurant.microservices.spring.orderservice.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    ProductResponse getProductById(@PathVariable("id") Long id);

    @GetMapping("/api/products")
    List<ProductResponse> getAllProducts();
}
