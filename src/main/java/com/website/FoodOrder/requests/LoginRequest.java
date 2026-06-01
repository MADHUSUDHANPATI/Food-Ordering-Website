package com.website.FoodOrder.requests;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;
}
