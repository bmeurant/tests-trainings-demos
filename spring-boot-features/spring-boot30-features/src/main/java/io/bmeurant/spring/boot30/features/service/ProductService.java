package io.bmeurant.spring.boot30.features.service;

import io.bmeurant.spring.boot30.features.model.Product;
import io.bmeurant.spring.boot30.features.repository.ProductRepository;
import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Observed(name = "productService")
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Observed(name = "product.find-all", contextualName = "finding-all-products",
            lowCardinalityKeyValues = {"component", "data-access"})
    public List<Product> findAllProducts() {
        log.info("ProductService: Fetching all products.");
        return productRepository.findAll();
    }

    @Observed(name = "product.add-one", contextualName = "adding-single-product",
            lowCardinalityKeyValues = {"operation", "write"})
    public Product addProduct(Product product) {
        log.info("ProductService: Adding product: {}", product.getName());
        return productRepository.save(product);
    }
}
