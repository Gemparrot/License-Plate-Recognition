package com.csis231.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    @NotEmpty
    @Size(min = 2, message = "user name should have at least 2 characters")
    private String name;

    @Column(name = "password", nullable = false)
    @NotEmpty
   // @Size(min = 8, message = "password should have at least 8 characters")
    private String password;

    public User() {}

    public User(String name, String password) {
        super();
        this.name = name;
        this.password = password;
    }
    public long getId() {
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
}