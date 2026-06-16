package com.website.FoodOrder.controller;

import com.website.FoodOrder.model.Cart;
import com.website.FoodOrder.model.CartItem;
import com.website.FoodOrder.model.User;
import com.website.FoodOrder.requests.CartItemRequest;
import com.website.FoodOrder.requests.UpdateCartItemRequest;
import com.website.FoodOrder.service.CartService;
import com.website.FoodOrder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PostMapping("/cart/add")
    ResponseEntity<CartItem> addCartItemToCart(@RequestBody CartItemRequest req, @RequestHeader("Authorization") String jwt) throws Exception {

        CartItem cartItem = cartService.addCartItemToCart(req, jwt);

        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    @PutMapping("/cart-item/update")
    ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest req) throws Exception {

        CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/{cartItemId}/remove") // delete mapping can't access request  body
    ResponseEntity<Cart>removeItemFromCart(@PathVariable Long cartItemId,@RequestHeader("Authorization") String jwt ) throws Exception {

        Cart cart = cartService.removeItemFromCart(cartItemId, jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/cart/clear")
    ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUsernameFromJwt(jwt);
        Cart cart = cartService.cleanCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/cart")
    ResponseEntity<Cart> findCartByUserId(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUsernameFromJwt(jwt);
        Cart cart = cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }





}
