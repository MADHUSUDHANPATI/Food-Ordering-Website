package com.website.FoodOrder.controller;

import com.website.FoodOrder.model.Category;
import com.website.FoodOrder.model.User;
import com.website.FoodOrder.service.CategoryService;
import com.website.FoodOrder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @PostMapping("/admin/category")
    ResponseEntity<Category> createCategory(@RequestBody Category category  , @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUsernameFromJwt(jwt);

        Category createdCategory = categoryService.createCategory(category.getName(), user.getId());

        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);

    }

    @GetMapping("/category/restaurant")
    ResponseEntity< List<Category>> getRestaurantCategory(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUsernameFromJwt(jwt);

        List<Category> createdCategory = categoryService.findCategoryByRestaurantId(user.getId());

        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);

    }
}
