package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

//    @Value("${welcome.greeting:Hi}") //default value nu e foarte recomandata in app mari
    @Value("${welcome.greeting}")
    private String welcomeInfo;

    @GetMapping("welcome")
    public String showWelcomeInfo() {
        return welcomeInfo;
    }
}
