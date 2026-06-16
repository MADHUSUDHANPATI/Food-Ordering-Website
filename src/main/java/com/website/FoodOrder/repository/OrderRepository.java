package com.website.FoodOrder.repository;

import com.website.FoodOrder.model.Order;
import com.website.FoodOrder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long userId);
    List<Order> findByRestaurantId(Long restaurantId);
}
