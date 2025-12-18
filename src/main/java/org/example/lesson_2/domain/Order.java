package org.example.lesson_2.domain;

import java.util.List;

public class Order {
    private int id;
    private List<Product> products;
    private float totalPrice;

    public Order() {}

    public Order(int id, List<Product> products) {
        this.id = id;
        this.products = products;
        this.totalPrice = calculateTotal();
    }

    private float calculateTotal() {
        return (float) products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    public float getTotalPrice() { return totalPrice; }
}
