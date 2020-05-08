package com.example.demo.service;

import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepo userRepo;

    @Autowired // NU ASA
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
}
