package org.example.lesson_2.persistence.repository;

import org.example.lesson_2.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByCategoryIdAndProductName(Long categoryId, String productName);
}
