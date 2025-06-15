package io.bmeurant.spring.boot27.repository;

import io.bmeurant.spring.boot27.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
}
