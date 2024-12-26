package com.csis231.api.controller;

import com.csis231.api.model.User;
import com.csis231.api.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    //Get the user using his ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    //Sign up user with name and password
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        if (userService.userExists(user.getName())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        User newUser = userService.createUser(user);
        return ResponseEntity.ok(newUser);
    }

    //Login user with name and password
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User loginUser = userService.authenticateUser(user.getName(), user.getPassword());
        if (loginUser == null) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
        return ResponseEntity.ok(loginUser);
    }

    //Update the user profile
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserProfile(@PathVariable("userId") long userId, @RequestBody User user) {
        User updatedUser = userService.updateUserProfile(userId, user.getName(), user.getPassword());
        return ResponseEntity.ok(updatedUser);
    }
}
