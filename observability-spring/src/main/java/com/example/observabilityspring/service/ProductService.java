package com.example.observabilityspring.service;

import com.example.observabilityspring.entity.Product;
import com.example.observabilityspring.dto.ProductDTO;
import com.example.observabilityspring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getQuantity());
    }

    private Product convertToEntity(ProductDTO productDTO) {
        return new Product(productDTO.getId(), productDTO.getName(), productDTO.getPrice(), productDTO.getQuantity());
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        logger.info("Fetching all products");
        return productRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ProductDTO> getProductById(Long id) {
        logger.info("Fetching product with id: {}", id);
        return productRepository.findById(id).map(this::convertToDTO);
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        logger.info("Creating new product with name: {}", productDTO.getName());
        Product product = convertToEntity(productDTO);
        product.setId(null); // Ensure it's a new entity
        return convertToDTO(productRepository.save(product));
    }

    @Transactional
    public Optional<ProductDTO> updateProduct(Long id, ProductDTO productDTO) {
        logger.info("Updating product with id: {}", id);
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setName(productDTO.getName());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setQuantity(productDTO.getQuantity());
            Product updatedProduct = productRepository.save(existingProduct);
            return convertToDTO(updatedProduct);
        });
    }

    @Transactional
    public boolean deleteProduct(Long id) {
        logger.info("Deleting product with id: {}", id);
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Method to be used by OrderService to check and update stock
    @Transactional
    public boolean checkAndUpdateStock(Long productId, int quantityToOrder) {
        logger.info("Checking and updating stock for product id: {} for quantity: {}", productId, quantityToOrder);
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            if (product.getQuantity() >= quantityToOrder) {
                product.setQuantity(product.getQuantity() - quantityToOrder);
                productRepository.save(product);
                logger.info("Stock updated for product id: {}. New quantity: {}", productId, product.getQuantity());
                return true;
            } else {
                logger.warn("Insufficient stock for product id: {}. Requested: {}, Available: {}", productId, quantityToOrder, product.getQuantity());
                return false;
            }
        }
        logger.warn("Product not found for stock update with id: {}", productId);
        return false;
    }
}
