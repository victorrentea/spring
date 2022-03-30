package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@SpringBootApplication
public class DemoApplication {

   @Value("${name}")
   String name;

   @GetMapping("hello")
   public String method() {
      return "Hello world devtools XsYzTsY !" + name;
   }

   @Autowired
   ConfigurableEnvironment environment;

   @PostConstruct
   public void first() {
//      environment.getPropertySources().addFirst(
//          new MapPropertySource("wth", Map.of("name","stuff")));
   }

   public static void main(String[] args) {

//    SpringApplication.run(DemoApplication.class, args);
      new SpringApplicationBuilder(DemoApplication.class)
          .initializers(new MyInitializer())
          .run(args);
   }
}

