package com.website.FoodOrder.service;

import com.website.FoodOrder.model.Category;
import com.website.FoodOrder.model.Food;
import com.website.FoodOrder.model.Restaurant;
import com.website.FoodOrder.repository.FoodRepository;
import com.website.FoodOrder.requests.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    private FoodRepository foodRepository;


    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {

        Food food=  new Food();

        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setImages(req.getImages());
        food.setDescription(req.getDescription());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setIngredients(req.getIngredients());
        food.setVegetarian(req.isVegetarian());
        food.setSeasonal(req.isSessional());

        Food savedFood  =  foodRepository.save(food);
        restaurant.getFoods().add(savedFood);

        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {  // We are not deleting data, we are removing food from particular restaurat;

        Food food= findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);
    }

    @Override
    public List<Food> getRestaurantFood(Long restaurantId, boolean isVegetarian,boolean isNonveg,  boolean isSessional, String foodCategory) {

        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

        if(isVegetarian) {
            foods = filterByVegetarian(foods, isVegetarian);
        }
        
        if(isNonveg) {
            foods = filterByNonveg(foods, isNonveg);
        }
        
        if(isSessional) {
            foods = filterBySessional(foods, isSessional);
        }

        if(foodCategory!=null && !foodCategory.equals("")) {
            foods = filterByCategory(foods, foodCategory);
        }
        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if(food.getFoodCategory()!=null) {
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySessional(List<Food> foods, boolean isSessional) {
        return foods.stream().filter(food -> food.isSeasonal()==isSessional).collect(Collectors.toList());
    }

    private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {
        return foods.stream().filter(food -> !food.isVegetarian()).collect(Collectors.toList());
    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream().filter(food -> food.isVegetarian()==isVegetarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {

        Food food = foodRepository.findById(foodId)
                .orElseThrow(()-> new Exception(" Food not found with id " + foodId));
        return food;
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {

        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}
