package com.website.FoodOrder.repository;

import com.website.FoodOrder.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
