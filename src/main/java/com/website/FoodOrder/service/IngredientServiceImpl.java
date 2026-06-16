package com.website.FoodOrder.service;

import com.website.FoodOrder.model.IngredientCategory;
import com.website.FoodOrder.model.IngredientsItem;
import com.website.FoodOrder.model.Restaurant;
import com.website.FoodOrder.repository.IngredientCategoryRepository;
import com.website.FoodOrder.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService{


    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientCategory ingredientCategory= new IngredientCategory();

        ingredientCategory.setName(name);
        ingredientCategory.setRestaurant(restaurant);

        return ingredientCategoryRepository.save(ingredientCategory);

    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {

        IngredientCategory ingredientCategory = ingredientCategoryRepository.findById(id)
                .orElseThrow(()-> new Exception(" Ingredient category has not found with id " + id));
        return ingredientCategory;
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {

        restaurantService.findRestaurantById(id);
        List<IngredientCategory> categoryList = ingredientCategoryRepository.findByRestaurantId(id);
        return categoryList;
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = findIngredientCategoryById(categoryId);

        IngredientsItem ingredientsItem=new IngredientsItem();
        ingredientsItem.setName(ingredientName);
        ingredientsItem.setRestaurant(restaurant);
        ingredientsItem.setCategory(category);
        IngredientsItem savedItem = ingredientItemRepository.save(ingredientsItem);
        category.getIngredients().add(savedItem);
        return savedItem;
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {

        List<IngredientsItem> items = ingredientItemRepository.findByRestaurantId(restaurantId);
        return items;
    }

    @Override
    public IngredientsItem updateIngredientStock(Long id) throws Exception {

        IngredientsItem ingredientsItem = ingredientItemRepository.findById(id)
                .orElseThrow(()-> new Exception(" Ingredient Item has not found with id " + id));

        ingredientsItem.setStoke(!ingredientsItem.isStoke());
        return ingredientItemRepository.save(ingredientsItem);
    }
}
