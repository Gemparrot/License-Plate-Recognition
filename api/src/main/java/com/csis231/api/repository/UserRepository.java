package com.csis231.api.repository;

import com.csis231.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNameAndPassword(String name, String password);

    Optional<Object> findByName(String name);
}
