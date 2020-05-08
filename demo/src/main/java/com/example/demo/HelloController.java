package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

//    @RequestMapping(path = "hello", method = RequestMethod.GET)
    @GetMapping(path = "hello")
    public String hello() {
        return "Hello Spring Web DEV TOOLS";
    }
    @PostMapping(path = "hello")
    public String helloPost(@RequestBody String name) {
        return name + " Spring Web POST ";
    }
    @PutMapping(path = "ping")
    public DataStructure helloPost(@RequestBody DataStructure data) {
        data.name = data.name.toUpperCase();
        return data;
    }
}
class DataStructure {
    public String name;
    public long phone;
}
