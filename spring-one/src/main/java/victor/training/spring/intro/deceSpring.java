package victor.training.spring.intro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class deceSpring implements CommandLineRunner {
   public static void main(String[] args) {
       SpringApplication.run(deceSpring.class, args);
   }
   @Autowired A a;

//   @Transactional(isolation = )
   @Override
   public void run(String... args) throws Exception {
      System.out.println("Call: " + a.method());
   }
}


//@Component
//@Repository
//@Controller
//@RestController

