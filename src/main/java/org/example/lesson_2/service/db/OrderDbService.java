package org.example.lesson_2.service.db;

import org.example.lesson_2.persistence.entity.OrderEntity;
import org.example.lesson_2.persistence.entity.OrderItemEntity;
import org.example.lesson_2.persistence.repository.OrderRepository;
import org.example.lesson_2.persistence.repository.ProductRepository;
import org.example.lesson_2.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
public class OrderDbService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderDbService(OrderRepository orderRepository,
                          UserRepository userRepository,
                          ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderEntity createOrder(String userEmail, Map<Long, Integer> productIdToQty) {
        var user = userRepository.findByEmail(userEmail).orElseThrow(() ->
                new IllegalArgumentException("User with email " + userEmail + " not found"));

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setStatus("CREATED");

        for (var entry : productIdToQty.entrySet()) {
            var product = productRepository.findById(entry.getKey()).orElseThrow(() ->
                    new IllegalArgumentException("Product with id " + entry.getKey() + " not found"));

            OrderItemEntity item = new OrderItemEntity();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(entry.getValue());
            item.setUnitPrice(product.getPrice());

            order.getItems().add(item);
        }

        return orderRepository.save(order);
    }
}
