package com.website.FoodOrder.repository;

import com.website.FoodOrder.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>{
}
