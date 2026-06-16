package com.website.FoodOrder.service;

import com.website.FoodOrder.model.IngredientCategory;
import com.website.FoodOrder.model.IngredientsItem;

import java.util.List;

public interface IngredientService {


    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception;

    public IngredientCategory findIngredientCategoryById(Long id) throws  Exception;

    public List<IngredientCategory> findIngredientCategoryByRestaurantId( Long id) throws Exception;

    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception;

    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId);

    public IngredientsItem updateIngredientStock( Long id) throws Exception;


}
