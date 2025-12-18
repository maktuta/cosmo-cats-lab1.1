package org.example.lesson_2.dto;

import java.util.List;

public class OrderDto {
    private int id;
    private List<ProductDto> products;
    private float totalPrice;

    public OrderDto() {}

    public OrderDto(int id, List<ProductDto> products, float totalPrice) {
        this.id = id;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<ProductDto> getProducts() { return products; }
    public void setProducts(List<ProductDto> products) { this.products = products; }

    public float getTotalPrice() { return totalPrice; }
    public void setTotalPrice(float totalPrice) { this.totalPrice = totalPrice; }
}
