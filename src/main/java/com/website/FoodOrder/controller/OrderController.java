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
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("order")
    ResponseEntity<Order> createOrder(@RequestBody OrderRequest req, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUsernameFromJwt(jwt);
        Order order = orderService.createOrder(req, user);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/order/{orderId}")
    ResponseEntity<String> cancelOrder( @PathVariable Long orderId) throws Exception {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>("Your order successfully cancelled", HttpStatus.OK);
    }

    @GetMapping("/order/user")
    ResponseEntity<List<Order>> getUserOrders(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUsernameFromJwt(jwt);
        List<Order> orders = orderService.getUserOrder(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


}
