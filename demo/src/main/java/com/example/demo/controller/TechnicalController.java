package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TechnicalController {

    @Autowired
    ConfigurableApplicationContext spring;

    @GetMapping("admin/restart")
    public String restart() {
//        spring.refresh();
        return "SECURED";
    }
//    @PreAuthorize().
    @GetMapping("ping")
    public String ping() {
//        spring.refresh();
        return "SECURED";
    }
    @Autowired WelcomeInfo welcomeInfo;
    @GetMapping("unsecured/welcome-info")
    public WelcomeInfo welcomeInfo() {
        return welcomeInfo;
    }
}
