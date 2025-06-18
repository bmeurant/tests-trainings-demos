package com.example.observabilityspring.scheduler;

import com.example.observabilityspring.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductStockReporter {

    private static final Logger logger = LoggerFactory.getLogger(ProductStockReporter.class);
    private final ProductService productService;

    @Autowired
    public ProductStockReporter(ProductService productService) {
        this.productService = productService;
    }

    // Run every 2 minutes. Cron: second, minute, hour, day of month, month, day(s) of week
    @Scheduled(cron = "0 */2 * * * *")
    public void reportCurrentStockLevels() {
        logger.info("Scheduled task: Reporting current stock levels...");
        productService.getAllProducts().forEach(product ->
            logger.info("Stock Report - Product ID: {}, Name: {}, Quantity: {}", product.getId(), product.getName(), product.getQuantity())
        );
        logger.info("Scheduled task: Stock report complete.");
    }
}
