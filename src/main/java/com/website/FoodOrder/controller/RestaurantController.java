package com.website.FoodOrder.controller;

import com.website.FoodOrder.DTO.RestaurantDTO;
import com.website.FoodOrder.model.Restaurant;
import com.website.FoodOrder.model.User;
import com.website.FoodOrder.service.RestaurantService;
import com.website.FoodOrder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {

        List<Restaurant> restaurants = restaurantService.getAllRestaurants();

        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestParam String keyword) {

        List<Restaurant> restaurant = restaurantService.searchRestaurant(keyword);

        return new ResponseEntity<>(restaurant , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(@PathVariable Long id) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(id);

        return new ResponseEntity<>(restaurant , HttpStatus.OK);
    }

    @GetMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDTO> addToFavorites( @PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUsernameFromJwt(jwt);

        RestaurantDTO restaurantDTO = restaurantService.addToFavorites(id, user);

        return new ResponseEntity<>(restaurantDTO, HttpStatus.OK);
    }



}
