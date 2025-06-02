package io.bmeurant.java9.modules.inventory;

import io.bmeurant.java9.modules.product.Product; // This import will initially show an error!
import io.bmeurant.java9.modules.product.ProductService; // This import will initially show an error!

public class InventoryApp {
    public static void main(String[] args) {
        System.out.println("\n--- Java 9: Module System (JPMS) ---\n");

        ProductService productService = new ProductService();
        System.out.println("Available products:\n");
        productService.getAllProducts().forEach(System.out::println);

        Product laptop = productService.findProductByName("Laptop");
        if (laptop != null) {
            System.out.println("\nFound product: " + laptop.getName() + " at $" + laptop.getPrice());
        } else {
            System.out.println("\nProduct not found.");
        }
    }
}
