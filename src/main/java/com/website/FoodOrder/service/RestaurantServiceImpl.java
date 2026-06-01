package com.website.FoodOrder.service;

import com.website.FoodOrder.DTO.RestaurantDTO;
import com.website.FoodOrder.model.Address;
import com.website.FoodOrder.model.Restaurant;
import com.website.FoodOrder.model.User;
import com.website.FoodOrder.repository.AddressRepository;
import com.website.FoodOrder.repository.RestaurantRepository;
import com.website.FoodOrder.repository.UserRepository;
import com.website.FoodOrder.requests.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {

        Address address = addressRepository.save(req.getAddress());

        Restaurant restaurant= new Restaurant();
        restaurant.setAddress(address);
        restaurant.setName(req.getName());
        restaurant.setDescription(req.getDescription());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setImages(req.getImages());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

//        if(restaurant.getCuisineType()!= null) {
//            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
//        }
//        if(restaurant.getDescription()!=null) {
//            restaurant.setDescription(updatedRestaurant.getDescription());
//        }
//
//        if(restaurant.getName()!=null) {
//            restaurant.setName(updatedRestaurant.getName());
//        }
//
        if(updatedRestaurant.getCuisineType()!= null) {
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }
        if(updatedRestaurant.getDescription()!=null) {
            restaurant.setDescription(updatedRestaurant.getDescription());
        }

        if(updatedRestaurant.getName()!=null) {
            restaurant.setName(updatedRestaurant.getName());
        }

        if(updatedRestaurant.getContactInformation() != null) {
            restaurant.setContactInformation(
                    updatedRestaurant.getContactInformation()
            );
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

        restaurantRepository.delete(restaurant);  // Here we need to change it to string return one string here.
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword); // Instead of writing in query we can write like this=> Page<Product> pageDetails = productRepository.findByProductNameLikeIgnoreCase('%' + keyword+'%',pageable);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {

        Restaurant restaurant = restaurantRepository.findById(id).
                orElseThrow(()-> new  Exception("Restaurant not found with id " + id));
        return restaurant;
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {

        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);

        if(restaurant == null){
            throw new Exception("Restaurant is not found with userId " + userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDTO addToFavorites(Long restaurantId, User user) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setDescription(restaurant.getDescription());
        restaurantDTO.setImages(restaurant.getImages());
        restaurantDTO.setId(restaurant.getId());
        restaurantDTO.setTitle(restaurant.getName());

//        if(user.getFavourites().contains(restaurantDTO)) {
//            user.getFavourites().remove(restaurantDTO);
//        }
//        else {
//            user.getFavourites().add(restaurantDTO);
//        }  Not working as expected

        boolean isFavorite = false;
        List<RestaurantDTO> favorites = user.getFavourites();
        for( RestaurantDTO favorite : favorites) {
            if( favorite.getId().equals(restaurantId)) {
                isFavorite = true;
                break;
            }
        }

        if(isFavorite) {
            favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
        }
        else {
            favorites.add(restaurantDTO);
        }

        userRepository.save(user);
        return restaurantDTO;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {

        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
