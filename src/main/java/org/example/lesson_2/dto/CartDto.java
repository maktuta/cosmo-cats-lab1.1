package org.example.lesson_2.dto;

import java.util.List;

public class CartDto {
    private int id;
    private List<ProductDto> items;
    private float totalPrice;

    public CartDto() {}

    public CartDto(int id, List<ProductDto> items, float totalPrice) {
        this.id = id;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<ProductDto> getItems() { return items; }
    public void setItems(List<ProductDto> items) { this.items = items; }

    public float getTotalPrice() { return totalPrice; }
    public void setTotalPrice(float totalPrice) { this.totalPrice = totalPrice; }
}
