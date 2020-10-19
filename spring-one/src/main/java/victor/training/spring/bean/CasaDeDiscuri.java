package victor.training.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CasaDeDiscuri {
   public static void main(String[] args) {
      SpringApplication.run(CasaDeDiscuri.class);
   }
   @Value("${welcome.welcomeMessage}")
   private String welcome;
   @Lazy
   @Bean
   public Cantaret smiley(@Value("${welcome.welcomeMessage}") String welcome) {
      System.out.println("Se naste o stea " + welcome);
      return new Cantaret("Smiley");
   }
   @Lazy
   @Bean
   public Cantec ceMaiFaciStraine(Cantaret smiley,  @Value("${welcome.welcomeMessage}") String welcome) {
      System.out.println("ce mai faci " + smiley);
      return new Cantec(smiley, "Ce mai faci, straine? + " + welcome);
   }
   @Lazy
   @Bean
   public Cantec adeline() {
      System.out.println("adeline");
      return new Cantec(smiley(null), "Adeline");
   }

//   @Bean
//   public Interface anInterface() {
//      ifaraie ~= firaraie
//      if (System.getProperty("daca aia") == "aia") {
//         return Dala
//      }
//   }
}
//@Configuration
//class AltaConfiguration {
//   @Bean
//   public Cantec adeline2(Cantaret smiley) {
//      System.out.println("adeline2");
//      return new Cantec(smiley, "Adeline2");
//   }
//}

@Component
@Profile("prod")
class SSOAuth implements  Interface {
   @Override
   public void authenticate(String username) {
      System.out.println("pe bune " + username);
   } // by default are numele de bean "myCompo"
}
@Component
//@Primary
@Profile("!prod")
class DummyAuth implements  Interface {
   @Override
   public void authenticate(String username) {
      System.out.println("Dummy " + username);
   } // by default are numele de bean "myCompo2"
}
interface Interface {
   void authenticate(String username);
}

@Component
class Play{
   @Autowired
   private ApplicationContext spring;
   @Autowired
   Cantaret cantaret;
   @Autowired
   Interface compo;

   @Autowired
//    @Qualifier("adeline")
   Cantec adeline;

   @PostConstruct
   public void test() {
      System.out.println("Start");
      System.out.println(cantaret);
      System.out.println(adeline);
      compo.authenticate("u");
   }

}


// nu am voie sa ating clasele de mai jos

class Cantec {
   private final Cantaret cantaret;
   private final String titlu;

   Cantec(Cantaret cantaret, String titlu) {
      this.cantaret = cantaret;
      this.titlu = titlu;
   }

   @Override
   public String toString() {
      return "Cantec{" +
             "cantaret=" + cantaret +
             ", titlu='" + titlu + '\'' +
             '}';
   }
}
class Cantaret {
   private final String nume;

   Cantaret(String nume) {
      this.nume = nume;
   }
}