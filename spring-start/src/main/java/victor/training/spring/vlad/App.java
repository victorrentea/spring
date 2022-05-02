package victor.training.spring.vlad;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class App {
   public static void main(String[] args) {
       SpringApplication.run(App.class, args);
   }

   @Bean
   public Cfg cfgOne(@Value("${cfg.one.x}") int x, @Value("${cfg.one.y}") int y) {
      return new Cfg(x,y);
   }
   @Bean

   public Cfg cfgTwo(@Value("${cfg.two.x}") int x, @Value("${cfg.two.y}") int y) {
      return new Cfg(x,y);
   }
}


@Service
class X1{
   @Autowired
   Cfg cfgOne;

   @PostConstruct
   public void printCfg() {
      System.out.println(cfgOne);
   }
}
@Service
class X2{
   @Autowired
   Cfg cfgTwo;
   @PostConstruct
   public void printCfg() {
      System.out.println(cfgTwo);
   }
}
