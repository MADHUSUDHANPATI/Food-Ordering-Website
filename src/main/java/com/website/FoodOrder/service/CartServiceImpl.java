package com.website.FoodOrder.service;

import com.website.FoodOrder.model.Cart;
import com.website.FoodOrder.model.CartItem;
import com.website.FoodOrder.model.Food;
import com.website.FoodOrder.model.User;
import com.website.FoodOrder.repository.CartItemRepository;
import com.website.FoodOrder.repository.CartRepository;
import com.website.FoodOrder.requests.CartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addCartItemToCart(CartItemRequest req, String jwt) throws Exception {

        User user = userService.findUsernameFromJwt(jwt);

        Food food = foodService.findFoodById(req.getFoodId());

        Cart cart = cartRepository.findByCustomerId(user.getId());

        for(CartItem cartItem : cart.getItems()) {
            if(cartItem.getFood().equals(food)) {
                int newQuantity = cartItem.getQuantity() + req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setFood(food);
        cartItem.setQuantity(req.getQuantity());
        cartItem.setIngredients(req.getIngredients());
        cartItem.setTotalPrice(req.getQuantity() * food.getPrice());
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        cart.getItems().add(savedCartItem);
        cartRepository.save(cart); // I added additionally
        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(()-> new Exception("CartItem not found with id " + cartItemId));

        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getFood().getPrice() * quantity);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {

        User user = userService.findUsernameFromJwt(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(()-> new Exception("CartItem not found with id " + cartItemId));
//        cartItemRepository.delete(cartItem);
        cart.getItems().remove(cartItem);
        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {

        Long total = 0L;
        for(CartItem cartItem : cart.getItems()) {
            total += cartItem.getFood().getPrice() * cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {

        Cart cart = cartRepository.findById(id)
                .orElseThrow(()-> new Exception(" Cart not found with id" + id));
        return cart;
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {

//        User user = userService.findUsernameFromJwt(jwt);
        Cart cart = cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotals(cart));

        return cart;
    }

    @Override
    public Cart cleanCart(Long userId) throws Exception {

//        User user = userService.findUsernameFromJwt(jwt);
        Cart cart = findCartById(userId);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
