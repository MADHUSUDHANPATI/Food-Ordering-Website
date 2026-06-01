package com.website.FoodOrder.controller;

import com.website.FoodOrder.model.Food;
import com.website.FoodOrder.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {


    @Autowired
    private FoodService foodService;

    @GetMapping("/restaurant/{restaurantId}")
    ResponseEntity<List<Food>> getRestaurantFood(@PathVariable Long restaurantId ,
                                                 @RequestParam boolean Vegetarian,
                                                 @RequestParam boolean nonVeg,
                                                 @RequestParam boolean seasonal,
                                                 @RequestParam(required = false) String foodCategory
                                                 ){
        List<Food> foods = foodService.getRestaurantFood(restaurantId, Vegetarian, nonVeg, seasonal, foodCategory);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/search")
    ResponseEntity<List<Food>> searchFood( @RequestParam String keyword) {

        List<Food> foodList = foodService.searchFood(keyword);

        return new ResponseEntity<>(foodList, HttpStatus.OK);
    }

}
