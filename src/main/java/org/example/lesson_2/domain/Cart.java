package org.example.lesson_2.domain;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int id;
    private List<Product> items = new ArrayList<>();

    public Cart() {}

    public Cart(int id) {
        this.id = id;
    }

    public void addProduct(Product product) {
        items.add(product);
    }

    public void removeProduct(Product product) {
        items.remove(product);
    }

    public float getTotalPrice() {
        return (float) items.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<Product> getItems() { return items; }
    public void setItems(List<Product> items) { this.items = items; }
}
