package victor.training.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CasaDeDiscuriApp {
   public static void main(String[] args) {
       SpringApplication.run(CasaDeDiscuriApp.class, args);
   }
   @Autowired
   private Singer singer;

   @PostConstruct
   public void init() {
      System.out.println("RUN AT INIT: " + singer);
   }

}

@Component
class A {
   private final Singer singer;

//   @Autowired nu mai e nevoie din Spring 4.3
   public A(Singer singer) {
      this.singer = singer;
   }

   @PostConstruct
   public void init() {
      System.out.println("RUN AT INIT2: "+ singer);
   }

}