package com.csis231.api.service;


import com.csis231.api.exception.UserNotFoundException;
import com.csis231.api.model.User;
import com.csis231.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public User authenticateUser(String name, String password) {
        Optional<User> user = userRepository.findByNameAndPassword(name, password);
        return user.orElse(null);
    }

    public boolean userExists(String name) {
        return userRepository.findByName(name).isPresent();
    }

    public User updateUserProfile(long userId, String newName, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!user.getName().equals(newName)) {
                user.setName(newName);
            }
            if (!user.getPassword().equals(newPassword)) {
                user.setPassword(newPassword);
            }
            return userRepository.save(user);
        }
        return null;
    }


}