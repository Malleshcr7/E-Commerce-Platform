package com.telugu.ecommerce.repository;

import com.telugu.ecommerce.domain.OrderStatus;
import com.telugu.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findBySellerId(Long sellerId);
    List<Order> findByOrderStatus(OrderStatus orderStatus);
}
