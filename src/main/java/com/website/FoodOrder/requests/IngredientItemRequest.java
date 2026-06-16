package com.website.FoodOrder.requests;

import lombok.Data;

@Data
public class IngredientItemRequest {

    private String ingredientName;
    private Long categoryId;
    private Long restaurantId;

}
