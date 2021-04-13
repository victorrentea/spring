package com.example.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.File;

@SpringBootApplication//(exclude = {JmxAutoConfiguration.class})
public class FirstApplication {

   public static void main(String[] args) {
      SpringApplication.run(FirstApplication.class, args);
   }

}

@Service
@RefreshScope
class RefreshableBean {
   @Value("${welcome.welcomeMessage}")
   private String message;
   @PostConstruct
   public void method() {
      if (message.length() < 5) {
         throw new IllegalArgumentException();
      }
//      if (!file.isFile()) {
//         throw new IllegalArgumentException();
//      }
   }
   @Value("${welcome.help.file}")
   private File file;
   public RefreshableBean() {
      System.out.println("A new instance");
   }

   public String getMessage() {
      return message;
   }
}

@Slf4j
@RequiredArgsConstructor
@RestController
class MyRestController {

   @Autowired
   RefreshableBean service;
   @Autowired
   private final WelcomeInfo welcomeInfo;

   @GetMapping
   public String method() {
      System.out.println("The instance that I call : " + service.getClass());
      log.debug("Some deep dark info about a bug you can't reproduce on your local");
      return service.getMessage();
   }
}