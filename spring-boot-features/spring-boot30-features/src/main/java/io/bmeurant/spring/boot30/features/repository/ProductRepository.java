package io.bmeurant.spring.boot30.features.repository;

import io.bmeurant.spring.boot30.features.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
}
