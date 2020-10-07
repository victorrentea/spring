package victor.training.spring.bean;


import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CasaDeDiscuri implements CommandLineRunner {

   public static void main(String[] args) {
      SpringApplication.run(CasaDeDiscuri.class);
   }

   @Autowired
   private ApplicationContext spring;

   @Override
   public void run(String... args) throws Exception {
      System.out.println(spring.getBean("smiley"));
      System.out.println(spring.getBean("carla"));
      System.out.println(spring.getBean("songConfig"));
   }
}

@Configuration
class SongConfig {
   @Bean
   public Cantaretz smiley() {
      return new Cantaretz("Smiley");
   }
   @Bean
   public Cantaretz carla() {
      return new Cantaretz("Carla's Dreams");
   }
}

@Component
@RequiredArgsConstructor
class ConcertCarla {
   @Autowired
//   @Qualifier("carla")
   private Cantaretz carla;
   @PostConstruct
   public void canta() {
      System.out.println("Canta " + carla);
   }
}

@ToString
class Cantaretz {
   private final String name;

   public Cantaretz(String name) {
      this.name = name;
   }
}