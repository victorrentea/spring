package com.example.demo.repo;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.managedTeacherIds WHERE username=?1")
    Optional<User> findByIdForLogin(String username);
}
