package com.website.FoodOrder.controller;

import com.website.FoodOrder.model.Food;
import com.website.FoodOrder.model.Restaurant;
import com.website.FoodOrder.requests.CreateFoodRequest;
import com.website.FoodOrder.service.FoodService;
import com.website.FoodOrder.service.RestaurantService;
import com.website.FoodOrder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

//    @Autowired
//    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req) throws Exception {

        Long restaurantId = req.getRestaurantId();
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        Food food = foodService.createFood(req, req.getCategory(), restaurant);

        return new ResponseEntity<Food>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteFood(@PathVariable Long id) throws Exception {

        foodService.deleteFood(id);
        return new ResponseEntity<>("Food has deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<Food> updateAvailabilityStatus(@PathVariable  Long id) throws Exception {

        Food food = foodService.updateAvailabilityStatus(id);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }

}
