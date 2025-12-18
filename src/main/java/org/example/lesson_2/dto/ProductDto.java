package org.example.lesson_2.dto;

import jakarta.validation.constraints.*;
import org.example.lesson_2.domain.Category;
import org.example.lesson_2.validation.CosmicWordCheck;

public class ProductDto {

    private int id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 50, message = "Name length must be between 3 and 50 characters")
    @CosmicWordCheck
    private String name;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be greater than 0")
    private Float price;
    private int categoryID;

    public ProductDto() {}

    public ProductDto(int id, String name, Float price, int categoryID) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryID = categoryID;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Float getPrice() { return price; }
    public void setPrice(Float price) { this.price = price; }

    public int getCategory() {
        return categoryID;
    }

    public void setCategory(int categoryID) {
        this.categoryID = categoryID;
    }
}
