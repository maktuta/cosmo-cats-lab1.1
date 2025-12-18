package org.example.lesson_2.domain;

public class Product {
    private int id;
    private String name;
    private float price;
    private int categoryID;

    public Product() {}

    public Product(int id, String name, float price, int categoryID) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryID = categoryID;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    public int getCategory() { return categoryID; }
    public void setCategory(int categoryID) { this.categoryID = categoryID; }
}
