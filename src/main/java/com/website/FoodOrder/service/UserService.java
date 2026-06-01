package com.website.FoodOrder.service;

import com.website.FoodOrder.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User findUsernameFromJwt ( String jwt) throws Exception;

    public User findUserByEmail( String email) throws Exception;
}
