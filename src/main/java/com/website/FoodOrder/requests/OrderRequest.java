package com.website.FoodOrder.requests;

import com.website.FoodOrder.model.Address;
import lombok.Data;

@Data
public class OrderRequest {

    private Long restaurantId;
    private Address deliveryAddress;
}
