package com.website.FoodOrder.repository;

import com.website.FoodOrder.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
