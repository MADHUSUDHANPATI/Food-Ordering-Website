package com.website.FoodOrder.service;

import com.website.FoodOrder.model.Category;
import com.website.FoodOrder.model.Food;
import com.website.FoodOrder.model.Restaurant;
import com.website.FoodOrder.requests.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest req , Category category, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantFood(Long restaurantId, boolean isVegetarian, boolean isNonveg , boolean isSeasonal, String foodCategory);

    public List<Food> searchFood( String keyword);

    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailabilityStatus( Long foodId) throws Exception;
}
