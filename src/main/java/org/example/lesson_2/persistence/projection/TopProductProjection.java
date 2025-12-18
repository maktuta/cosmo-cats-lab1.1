package org.example.lesson_2.persistence.projection;

public interface TopProductProjection {
    Long getProductId();
    String getProductName();
    Long getTotalQuantity();
}
