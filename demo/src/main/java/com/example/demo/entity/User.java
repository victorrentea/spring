package com.example.demo.entity;

import javax.persistence.*;

@Entity
public class User {
    @Id
    private String username;

    public enum Role {
        USER, ADMIN
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    private String name;

    public User() {}
    public User(String username, String name, Role role) {
        this.username = username;
        this.name = name;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public User setRole(Role role) {
        this.role = role;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }
}
