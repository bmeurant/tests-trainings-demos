package io.bmeurant.java9.modules.product;

import java.util.List;
import java.util.ArrayList;

public class ProductService {
    private List<Product> products = new ArrayList<>();

    public ProductService() {
        products.add(new Product("Laptop", 1200.00));
        products.add(new Product("Mouse", 25.00));
        products.add(new Product("Keyboard", 75.00));
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products); // Return a copy to prevent external modification
    }

    public Product findProductByName(String name) {
        return products.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
