package org.example.lesson_2.service;

import org.example.lesson_2.domain.Product;
import org.example.lesson_2.dto.ProductDto;
import org.example.lesson_2.mapper.ProductMapper;
import org.example.lesson_2.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProductServiceImpl implements ProductService {

    private final Map<Integer, Product> mockDb = new HashMap<>();
    private final AtomicInteger idCounter;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductMapper mapper) {
        this.mapper = mapper;
        // Заглушки
        mockDb.put(1, new Product(1, "Laptop", 1200.0f, 1));
        mockDb.put(2, new Product(2, "Smartphone", 800.0f, 1));
        int maxId = mockDb.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
        this.idCounter = new AtomicInteger(maxId);

                                                    int maxId = mockDb.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
        this.idCounter = new AtomicInteger(maxId);
int maxId = mockDb.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
        this.idCounter = new AtomicInteger(maxId);
int maxId = mockDb.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
        this.idCounter = new AtomicInteger(maxId);
int maxId = mockDb.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
        this.idCounter = new AtomicInteger(maxId);
int maxId = mockDb.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
        this.idCounter = new AtomicInteger(maxId);
int maxId = mockDb.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
        this.idCounter = new AtomicInteger(maxId);
}

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        int id = idCounter.incrementAndGet();
        Product product = mapper.toEntity(productDto);
        product.setId(id);
        mockDb.put(id, product);
        return mapper.toDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return mockDb.values().stream().map(mapper::toDto).toList();
    }

    @Override
    public ProductDto getProductById(int id) {
        Product product = mockDb.get(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product with ID " + id + " not found.");
        }
        return mapper.toDto(product);
    }

    @Override
    public ProductDto updateProduct(int id, ProductDto updatedProduct) {
        Product existing = mockDb.get(id);
        if (existing == null) {
            throw new ResourceNotFoundException("Product with ID " + id + " not found.");
        }

        existing.setName(updatedProduct.getName());
        existing.setPrice(updatedProduct.getPrice());
        mockDb.put(id, existing);
        return mapper.toDto(existing);
    }

    @Override
    public void deleteProduct(int id) {
        if (mockDb.remove(id) == null) {
            throw new ResourceNotFoundException("Product with ID " + id + " not found.");
        }
    }
}
