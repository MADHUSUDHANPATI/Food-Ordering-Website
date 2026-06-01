package com.website.FoodOrder.controller;

import com.website.FoodOrder.model.Restaurant;
import com.website.FoodOrder.model.User;
import com.website.FoodOrder.requests.CreateRestaurantRequest;
import com.website.FoodOrder.service.RestaurantService;
import com.website.FoodOrder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest req, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUsernameFromJwt(jwt);

        Restaurant restaurant = restaurantService.createRestaurant(req, user);

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantRequest req, @PathVariable Long id) throws Exception {


        Restaurant restaurant = restaurantService.updateRestaurant(id, req);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long id) throws Exception {


        restaurantService.deleteRestaurant(id);

        return new ResponseEntity<>("Restaurant deleted Successfully", HttpStatus.OK);
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(@PathVariable Long id) throws Exception {


        Restaurant restaurant = restaurantService.updateRestaurantStatus(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUsernameFromJwt(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }




}
