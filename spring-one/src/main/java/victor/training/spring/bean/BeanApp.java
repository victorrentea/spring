package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {

   @Autowired
   private ApplicationContext spring;
   @Override
   public void run(String... args) throws Exception {
      System.out.println("concert: " + spring.getBean("inCort"));
      System.out.println("concert: " + spring.getBean("inCort"));
   }

   public static void main(String[] args) {
      SpringApplication.run(BeanApp.class, args);
   }

   @Bean
   public OldClass oldClass() {
      return OldClass.getInstance();
   }

   @Bean
   public Concert driveIn() {
      System.out.println("Se anunta concertul driveIn");
      return new Concert("Drive-In-Carla", carlaX());
   }

   @Bean
   @Scope("prototype")
   public Concert inCort() {
      System.out.println("Se anunta concertul inCort");
      return new Concert("Carla-n-Cort", carlaX());
   }

   @Bean
   public Artist carlaX() { // numele beanului -> numele metodei
      return new Artist("Carla's Dreams");
   }
}
//
//class Hack extends BeanApp {
//   @Override
//   public Artist carlaX() {
//      if (daca nu-l a, inca) {
//         v = super.carlaX();
//processautowired(v)
//processpostconstruct(v)
//return v
//      } else {
//         return din cache, singletonul unic.
//      }
//   }
//}

@Service
class CasaDeDiscuri {
   public CasaDeDiscuri() {
      System.out.println("New casa de discuris");
   }
}

@Data
class Artist {
   private final String name;
   @Autowired
   private CasaDeDiscuri casaDeDiscuri;

   public Artist(String name) {
      this.name = name;
      System.out.println("S-a nascut o stea cu : " + casaDeDiscuri);

   }
   @PostConstruct
   public void init() {
      System.out.println("Steaua are : " + casaDeDiscuri);
   }
}

// asta ar permite creerea a oricate instante. clientul va face getBean(ArtistProto.class).setName("Carla Clona")
//@Component @Scope("prototype") @Data
//class ArtistProto {
//   private String name;
//}

class Concert {
   private final String name;
   private final Artist artist;
   private LocalDate date;

   Concert(String name, Artist artist) {
      this.name = name;
      this.artist = artist;
   }
}

