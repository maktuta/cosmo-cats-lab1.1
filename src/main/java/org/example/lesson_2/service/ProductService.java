package org.example.lesson_2.service;

import org.example.lesson_2.dto.ProductDto;
import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    List<ProductDto> getAllProducts();
    ProductDto getProductById(int id);
    ProductDto updateProduct(int id, ProductDto updatedProduct);
    void deleteProduct(int id);
}
