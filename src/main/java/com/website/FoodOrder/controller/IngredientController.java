package com.website.FoodOrder.controller;

import com.website.FoodOrder.model.IngredientCategory;
import com.website.FoodOrder.model.IngredientsItem;
import com.website.FoodOrder.model.Restaurant;
import com.website.FoodOrder.model.User;
import com.website.FoodOrder.requests.IngredientCategoryRequest;
import com.website.FoodOrder.requests.IngredientItemRequest;
import com.website.FoodOrder.service.IngredientService;
import com.website.FoodOrder.service.RestaurantService;
import com.website.FoodOrder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private RestaurantService restaurantService;


    @PostMapping("/category")
    ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest req) throws Exception {

//        User user = userService.findUsernameFromJwt(jwt);
//        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());

        IngredientCategory ingredientCategory = ingredientService.createIngredientCategory(req.getName(), req.getRestaurantId());


        return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
    }

    @GetMapping("/category/{id}")
    ResponseEntity<IngredientCategory> findIngredientCategoryById(@PathVariable Long id) throws Exception {

        IngredientCategory ingredientCategory = ingredientService.findIngredientCategoryById(id);

        return new ResponseEntity<>(ingredientCategory, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    ResponseEntity<List<IngredientCategory>> findIngredientCategoryByRestaurantId(@PathVariable Long id) throws Exception {

//        User user = userService.findUsernameFromJwt(jwt);
//        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());

        List<IngredientCategory> itemList = ingredientService.findIngredientCategoryByRestaurantId(id);

        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientItemRequest request) throws Exception {

//        User user = userService.findUsernameFromJwt(jwt);
//        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());

        IngredientsItem ingredientsItem = ingredientService.createIngredientItem(request.getRestaurantId(), request.getIngredientName(), request.getCategoryId());

        return new ResponseEntity<>(ingredientsItem, HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/{id}")
    ResponseEntity<List<IngredientsItem>> findRestaurantIngredients(@PathVariable Long id) throws Exception {

//        User user = userService.findUsernameFromJwt(jwt);
//        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());

        List<IngredientsItem> itemList = ingredientService.findRestaurantIngredients(id);

        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    @PutMapping("/{id}/stoke")
    ResponseEntity<IngredientsItem> updateIngredientStock(@PathVariable Long id) throws Exception {

        IngredientsItem ingredientsItem = ingredientService.updateIngredientStock(id);

        return new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
    }
}
