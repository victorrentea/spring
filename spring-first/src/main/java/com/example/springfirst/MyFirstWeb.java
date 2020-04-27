package com.example.springfirst;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest")
public class MyFirstWeb {

    @GetMapping("hello")
    public String hello() {
        return "Hello SpringX";
    }

}
