package org.example.lesson_2.persistence.repository;

import org.example.lesson_2.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByOrderNumber(String orderNumber);
}
