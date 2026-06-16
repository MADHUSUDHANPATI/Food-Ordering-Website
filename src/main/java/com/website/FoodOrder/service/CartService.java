package com.website.FoodOrder.service;

import com.website.FoodOrder.model.Cart;
import com.website.FoodOrder.model.CartItem;
import com.website.FoodOrder.requests.CartItemRequest;

public interface CartService {

    public CartItem addCartItemToCart(CartItemRequest req, String jwt) throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;

    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception;

    public Long calculateCartTotals( Cart cart) throws Exception;

    public Cart findCartById( Long id) throws Exception;

    public Cart findCartByUserId( Long userId) throws  Exception;

    public Cart cleanCart ( Long userId) throws Exception;

}
