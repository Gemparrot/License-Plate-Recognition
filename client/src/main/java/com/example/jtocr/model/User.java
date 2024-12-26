package com.example.jtocr.model;

import java.util.HashMap;
import java.util.Map;

public class User {
    private static final Map<String, User> USERS = new HashMap<String, User>();
    private static long id;
    private String name;
    private String password;

    public User() {
    }

    public User(String name, String password) {
        super();
        this.name = name;
        this.password = password;
    }

    public static long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    private User(String id) {
        this.id = Long.parseLong((id));
    }

    public static User of(String id) {
        User user = USERS.get(id);
        if (user == null) {
            user = new User(id);
            USERS.put(id, user);
        }
        return user;
    }
}