package org.example.lesson_2.integration;

import org.example.lesson_2.persistence.entity.CategoryEntity;
import org.example.lesson_2.persistence.entity.ProductEntity;
import org.example.lesson_2.persistence.repository.CategoryRepository;
import org.example.lesson_2.persistence.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryIT extends PostgresTcBase {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void createAndReadProduct() {
        CategoryEntity cat = categoryRepository.save(new CategoryEntity(null, "Milk"));

        ProductEntity p = new ProductEntity();
        p.setCategory(cat);
        p.setProductName("Galaxy Milk");
        p.setPrice(new BigDecimal("10.50"));

        ProductEntity saved = productRepository.save(p);

        ProductEntity loaded = productRepository.findById(saved.getId()).orElseThrow();
        assertEquals("Galaxy Milk", loaded.getProductName());
        assertEquals(cat.getId(), loaded.getCategory().getId());
        assertNotNull(loaded.getCreatedAt());
    }

    @Test
    void uniqueProductNameInsideCategory_enforced() {
        CategoryEntity cat = categoryRepository.save(new CategoryEntity(null, "Yarn"));

        ProductEntity p1 = new ProductEntity();
        p1.setCategory(cat);
        p1.setProductName("Star Yarn");
        p1.setPrice(new BigDecimal("5.00"));
        productRepository.saveAndFlush(p1);

        ProductEntity p2 = new ProductEntity();
        p2.setCategory(cat);
        p2.setProductName("Star Yarn");
        p2.setPrice(new BigDecimal("6.00"));

        assertThrows(DataIntegrityViolationException.class, () -> productRepository.saveAndFlush(p2));
    }
}
