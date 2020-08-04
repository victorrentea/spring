package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class PoetApp implements CommandLineRunner {
   public static void main(String[] args) {
      SpringApplication.run(PoetApp.class);
   }

   @Autowired
   Poet poet;
   @Override
   public void run(String... args) throws Exception {
      System.out.println(poet);
   }
}

@Data
@Component
class Poet {
   private final String name;

   public Poet(@Value("${poet.name}") String name) {
      this.name = name;
   }
}