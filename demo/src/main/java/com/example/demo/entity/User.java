package com.example.demo.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User implements Serializable {
    @Id
    private String username;

    public enum Role {
        USER, ADMIN
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    private String name;

    @ElementCollection
    private Set<Long> managedTeacherIds;

    public User() {}
    public User(String username, String name, Role role, List<Long> managedTeacherIds) {
        this.username = username;
        this.name = name;
        this.role = role;
        this.managedTeacherIds = new HashSet<>(managedTeacherIds);
    }

    public Set<Long> getManagedTeacherIds() {
        return managedTeacherIds;
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
