package com.website.FoodOrder.service;

import com.website.FoodOrder.model.*;
import com.website.FoodOrder.repository.AddressRepository;
import com.website.FoodOrder.repository.OrderItemRepository;
import com.website.FoodOrder.repository.OrderRepository;
import com.website.FoodOrder.repository.UserRepository;
import com.website.FoodOrder.requests.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {

        Address shipingAddress = order.getDeliveryAddress();

        Address savedAddress = addressRepository.save(shipingAddress);

        if(!user.getAddresses().contains(savedAddress)) {
            user.getAddresses().add(savedAddress);
            userRepository.save(user);

        }

        Restaurant restaurant = restaurantService.findRestaurantById(order.getRestaurantId());
        Order createdOrder = new Order();
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setCustomer(user);
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setRestaurant(restaurant);

        Cart cart =cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems= new ArrayList<>();

        for( CartItem cartItem : cart.getItems()) {

            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);

            orderItems.add(savedOrderItem);
        }

        Long totalPrice = cartService.calculateCartTotals(cart);

        createdOrder.setTotalPrice(totalPrice);
        createdOrder.setItems(orderItems);

        Order savedOrder = orderRepository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);

        return createdOrder;

    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {

       Order order =  findOrderById(orderId);
       if(orderStatus.equals("OUT_FOR_DELIVERY")
         || orderStatus.equals("DELIVERY")
         || orderStatus.equals("COMPLETED")
         ||orderStatus.equals("PENDING")) {
           order.setOrderStatus(orderStatus);
           return orderRepository.save(order);
       }
       else  {
           throw new Exception("Please select correct order Status");
       }
    }

    @Override
    public void cancelOrder(Long id) throws Exception {

        Order order= findOrderById(id);
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> getUserOrder(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {

        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if(orderStatus !=null ) {
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).toList();
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId).
                orElseThrow(()-> new Exception(" Order is not found with order id " + orderId));
        return order;
    }
}
