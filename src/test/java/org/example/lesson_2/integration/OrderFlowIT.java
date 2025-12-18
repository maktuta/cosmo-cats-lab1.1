package org.example.lesson_2.integration;

import org.example.lesson_2.persistence.entity.CategoryEntity;
import org.example.lesson_2.persistence.entity.ProductEntity;
import org.example.lesson_2.persistence.entity.UserEntity;
import org.example.lesson_2.persistence.repository.CategoryRepository;
import org.example.lesson_2.persistence.repository.OrderItemRepository;
import org.example.lesson_2.persistence.repository.ProductRepository;
import org.example.lesson_2.persistence.repository.UserRepository;
import org.example.lesson_2.service.db.OrderDbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderFlowIT extends PostgresTcBase {

    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private OrderDbService orderDbService;
    @Autowired private OrderItemRepository orderItemRepository;

    @Test
    void createOrder_persistsItems_andProjectionWorks() {
        UserEntity user = userRepository.save(new UserEntity(null, "captain@cosmo.cats", "Captain Purrnova"));
        CategoryEntity cat = categoryRepository.save(new CategoryEntity(null, "Food"));

        ProductEntity p1 = new ProductEntity();
        p1.setCategory(cat);
        p1.setProductName("Comet Milk");
        p1.setPrice(new BigDecimal("3.00"));
        p1 = productRepository.save(p1);

        ProductEntity p2 = new ProductEntity();
        p2.setCategory(cat);
        p2.setProductName("Star Milk");
        p2.setPrice(new BigDecimal("4.00"));
        p2 = productRepository.save(p2);

        var order = orderDbService.createOrder(user.getEmail(), Map.of(
                p1.getId(), 2,
                p2.getId(), 5
        ));

        assertNotNull(order.getId());
        assertEquals("CREATED", order.getStatus());
        assertNotNull(order.getOrderNumber());
        assertEquals(2, order.getItems().size());

        var top = orderItemRepository.findTopProductsByStatus("CREATED");
        assertFalse(top.isEmpty());
        assertEquals(p2.getId(), top.get(0).getProductId());
        assertEquals("Star Milk", top.get(0).getProductName());
        assertEquals(5L, top.get(0).getTotalQuantity());
    }
}
