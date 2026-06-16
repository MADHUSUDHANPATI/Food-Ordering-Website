package com.website.FoodOrder.controller;

import com.website.FoodOrder.model.Order;
import com.website.FoodOrder.model.User;
import com.website.FoodOrder.requests.OrderRequest;
import com.website.FoodOrder.service.OrderService;
import com.website.FoodOrder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;


    @PutMapping("/order/{orderId}/{orderStatus}")
    ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @PathVariable String orderStatus) throws Exception {
        Order order = orderService.updateOrder(orderId, orderStatus);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }


    @GetMapping("/{order/restaurant/restaurantId}")
    ResponseEntity<List<Order>> getRestaurantOrders( @PathVariable Long restaurantId, @RequestParam(required = false) String orderStatus) throws Exception {
        List<Order> orders = orderService.getRestaurantOrder(restaurantId, orderStatus);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
