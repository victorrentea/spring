package com.example.demo.controller;

import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("rest/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // Generata acum de catre Lombok
//    public UserController(UserService userService, UserService userService2, UserService userService3, UserService userService4, UserService userService5, UserService userService6, UserService userService7) {
//        this.userService = userService;
//    }

    @GetMapping("current")
    public String getCurrentUser() {
        return "jdoe";
    }
}
