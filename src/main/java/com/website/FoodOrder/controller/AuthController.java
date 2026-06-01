package com.website.FoodOrder.controller;

import com.website.FoodOrder.config.JwtProvider;
import com.website.FoodOrder.model.Cart;
import com.website.FoodOrder.model.USER_ROLE;
import com.website.FoodOrder.model.User;
import com.website.FoodOrder.repository.CartRepository;
import com.website.FoodOrder.repository.UserRepository;
import com.website.FoodOrder.requests.LoginRequest;
import com.website.FoodOrder.responses.AuthResponse;
import com.website.FoodOrder.service.CustomerUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomerUserDetailService customerUserDetailService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user) throws Exception {

        User isEmailExists = userRepository.findByEmail(user.getEmail());
        if(isEmailExists!= null) {
            throw new Exception("Email is already used with another account");
        }

        User createdUser = new User();
        createdUser.setFullName(user.getFullName());
        createdUser.setEmail(user.getEmail());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(createdUser);

        Cart cart=new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setMessage("User Registered Successfully");
        response.setRole(savedUser.getRole());

        return new ResponseEntity<AuthResponse>(response, HttpStatus.CREATED);
    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {

        String email = req.getEmail();
        String password = req.getPassword();

        Authentication authentication = authenticate(email, password);

        Collection< ? extends GrantedAuthority> authorities= authentication.getAuthorities();
        String role = authorities.isEmpty()? null : authorities.iterator().next().getAuthority();
        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setMessage(" Login Successfully ");
        response.setRole(USER_ROLE.valueOf(role));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Authentication authenticate(String email, String password) {

        UserDetails userDetails = customerUserDetailService.loadUserByUsername(email);
        if(userDetails == null) {
            throw new BadCredentialsException(" Invalid Username ...");
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())) {
            throw new BadCredentialsException(" Invalid Password...");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


}
