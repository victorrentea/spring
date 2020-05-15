package com.example.demo.controller;

import com.example.demo.repo.TestRepo;
import com.example.demo.security.DemoPrincipal;
import com.example.demo.service.Alta;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;


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
        // cum e oare posibil ca dintr-o metoda STATICA sa obtii userul curent acum logat pe acest request??!
        // Hint: fiecare request HTTP este procesat in propriul sau thread. :D
        // ghici unde se agata date de securitate ? -> pe thread.
        DemoPrincipal user = (DemoPrincipal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return user.getFullName();
    }

    // daca vrei sa propagi security context pentru call-ul @Async:
    @PostConstruct
    public void enableSecurityContextPropagationOverAsync() {
//		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }


    @Autowired
    private TestRepo repo;

    @GetMapping("testdb")
    public String testDB() {
        return repo.test();
    }
    @GetMapping("testdb2")
    public void testDB2() {
        alta.inseraSiTeacher();
    }
    @Autowired private Alta alta;
}
