package io.bmeurant.microservices.spring.productservice.repository;

import io.bmeurant.microservices.spring.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository; // Interface de Spring Data JPA

public interface ProductRepository extends JpaRepository<Product, Long> {

}