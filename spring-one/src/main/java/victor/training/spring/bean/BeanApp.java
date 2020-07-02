package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@SpringBootApplication
public class BeanApp {
   public static void main(String[] args) {
      SpringApplication.run(BeanApp.class, args);
   }

   @Bean
   public Artist carlaX() { // numele beanului -> numele metodei
      return new Artist("Carla's Dreams");
   }

   @Autowired
   Artist car;

   @Bean
   public Concert driveIn() {
      return new Concert("Drive-In-Carla", car);
   }

   @Bean
   public Concert inCort() {
      return new Concert("Carla-n-Cort", car);
   }


}

@Data
class Artist {
   private final String name;
   @PostConstruct
   public void init() {
      System.out.println("S-a nascut o stea");
   }
}

// asta ar permite creerea a oricate instante. clientul va face getBean(ArtistProto.class).setName("Carla Clona")
//@Component @Scope("prototype") @Data
//class ArtistProto {
//   private String name;
//}

@Data
class Concert {
   private final String name;
   private final Artist artist;
}

