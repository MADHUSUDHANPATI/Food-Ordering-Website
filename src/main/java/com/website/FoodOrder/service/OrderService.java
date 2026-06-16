package com.website.FoodOrder.service;

import com.website.FoodOrder.model.Order;
import com.website.FoodOrder.model.User;
import com.website.FoodOrder.requests.OrderRequest;

import java.util.List;

public interface OrderService {

    public Order createOrder(OrderRequest order, User user) throws Exception;

    public Order updateOrder(Long orderId, String orderStatus) throws Exception;

    public void cancelOrder(Long Id) throws  Exception;

    public List<Order> getUserOrder(Long userId) throws Exception;

    public List<Order> getRestaurantOrder ( Long restaurantId , String orderStatus) throws Exception;

    public Order findOrderById(Long orderId) throws Exception;

}
