package victor.training.spring.bean;


import javafx.scene.canvas.Canvas;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Scanner;

@SpringBootApplication
public class CasaDeDiscuri implements CommandLineRunner {

   public static void main(String[] args) {
      SpringApplication.run(CasaDeDiscuri.class);
   }

   @Autowired
   private ApplicationContext spring;

   @Override
   public void run(String... args) throws Exception {
//      new Scanner(System.in).nextLine();
      System.out.println(spring.getBean("smiley"));
      System.out.println(spring.getBean("carla"));
      System.out.println(spring.getBean("carla")); // acelasi
      System.out.println(spring.getBean("songConfig"));

      System.out.println(spring.getBean("scara2"));
      System.out.println(spring.getBean("scara2")); // alt obiect
   }
//   @Autowired
//   private Cantec scara2;
}

@Configuration
class SongConfig {
   @Bean
   public Cantaretz smiley() {
      return new Cantaretz("Smiley");
   }
   @Bean
   public Cantaretz carla() {
      System.out.println("Cuiva ii vine ideea sa fac un duet de baieti"); // NU de 3 ori, ci 1 sngura data
      return new Cantaretz("Carla's Dreams");
   }
   @Bean
   @Scope("prototype")
   public Cantec scara2() {
      System.out.println("Generez cantec "); //2 ori
      return new Cantec(carla(), "Scara 2"); // apelul catre carla() nu se intampla de fapt, ci
      // apelul tau merge in SUBCLASA GENERATA DINAMIC IN CARE RULEZI, CARE FACE @OVERRIDE la toate metodele @Bean
   }
   @Bean
   @Scope("prototype")
   public Cantec secrete() {
      return new Cantec(carla(), "Secrete");
   }
   @Bean
   public OldClass old() {
      return OldClass.getInstance();
   }
}
class Cantec {
   private final Cantaretz cantaretz;
   private final String name;

   Cantec(Cantaretz cantaretz, String name) {
      this.cantaretz = cantaretz;
      this.name = name;
   }
}
@Component
@RequiredArgsConstructor
class ConcertCarla {
   private final Cantaretz carla;
   private final OldClass clasaPeCareNuPotSoSchimbDintrunJarVechi;
   @PostConstruct
   public void canta() {
      System.out.println("Canta " + carla);
      System.out.println(clasaPeCareNuPotSoSchimbDintrunJarVechi);
   }
}

@ToString
class Cantaretz {
   private final String name;

   public Cantaretz(String name) {
      this.name = name;
   }
}