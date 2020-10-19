package victor.training.spring.bean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CasaDeDiscuri {
   public static void main(String[] args) {
      SpringApplication.run(CasaDeDiscuri.class);
   }

   @Bean
   public Cantaret smiley() {
      System.out.println("Se naste o stea");
      return new Cantaret("Smiley");
   }

   @Bean
   public Cantec ceMaiFaciStraine() {
      System.out.println("ce mai faci");
      return new Cantec(smiley(), "Ce mai faci, straine?");
   }

   @Bean
   public Cantec adeline() {
      System.out.println("adeline");
      return new Cantec(smiley(), "Adeline");
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
}
class Cantaret {
   private final String nume;

   Cantaret(String nume) {
      this.nume = nume;
   }
}