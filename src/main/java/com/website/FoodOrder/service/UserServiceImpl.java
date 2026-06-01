package com.website.FoodOrder.service;

import com.website.FoodOrder.config.JwtProvider;
import com.website.FoodOrder.model.User;
import com.website.FoodOrder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;


    @Override
    public User findUsernameFromJwt(String jwt) throws Exception {

        String email = jwtProvider.getEmailFromJwtToken(jwt);

        User user= userRepository.findByEmail(email);
        return user;

    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user= userRepository.findByEmail(email);
        if(user== null) {
            throw new Exception("User not found");
        }
        return user;
    }
}
