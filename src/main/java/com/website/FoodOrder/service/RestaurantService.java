package com.website.FoodOrder.service;


import com.website.FoodOrder.DTO.RestaurantDTO;
import com.website.FoodOrder.model.Restaurant;
import com.website.FoodOrder.model.User;
import com.website.FoodOrder.requests.CreateRestaurantRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService {

    public Restaurant createRestaurant(CreateRestaurantRequest req , User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception;

    public void deleteRestaurant ( Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurants();

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById( Long id) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDTO addToFavorites(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;



}
