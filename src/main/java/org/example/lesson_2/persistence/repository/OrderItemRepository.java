package org.example.lesson_2.persistence.repository;

import org.example.lesson_2.persistence.entity.OrderItemEntity;
import org.example.lesson_2.persistence.projection.TopProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

    @Query("""
        select oi.product.id as productId,
               oi.product.productName as productName,
               sum(oi.quantity) as totalQuantity
        from OrderItemEntity oi
        where oi.order.status = :status
        group by oi.product.id, oi.product.productName
        order by sum(oi.quantity) desc
    """)
    List<TopProductProjection> findTopProductsByStatus(@Param("status") String status);
}
