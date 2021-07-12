package victor.training.spring.polimorfism;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class PolySpring implements CommandLineRunner {
   public static void main(String[] args) {
       SpringApplication.run(PolySpring.class, args);
   }

//   @Autowired
   //opt1
//   @Qualifier("filozofie")
//   private Curs curs;

   //opt2
//   private Curs filozofie;

   //opt3
//   private Filozofie filozofie;

   // opt 4
//   @Autowired
//   private List<Curs> cursuri;

   // opt5 :
   @Autowired
   private Curs curs;

//   public PolySpring() {
//      curs.preda();
//   }

   @PostConstruct
   public void init() {
      curs.preda();
   }

   @Override
//   @Transactional
   public void run(String... args) throws Exception {
   }
}

interface Curs {
   void preda();
}

//@Profile("local")
//@Configuration
//class DummySecurityConfig {
//@Bean
//}

//class DummyEmailNotificator implements EmailNotificator {
//class SMTPEmailNotificator implements EmailNotificator {
@Component
@Profile("p1")
class Filozofie implements Curs {
   @Autowired
   ClasaCareDeCareNUStieSpring_CRAPA c;
   @Override
   public void preda() {
      System.out.println("ceva doar pt confortul dev (TM) zzzZZZ BUG!");
   }
}
@Component
@Profile("!p1")
class SistemeDeOperare implements Curs {
   @Override
   public void preda() {
      System.out.println("Media de trecere 6.00");
   }
}

class ClasaCareDeCareNUStieSpring_CRAPA {

}