package victor.training.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
public class CasaDeDiscuriApp {
   public static void main(String[] args) {
      SpringApplication.run(CasaDeDiscuriApp.class, args);
   }

   //   <bean id=carla class=Singer>
//   <bean id=delia class=Singer>
//   <bean id=smiley class=Singer>
   @Bean
   public Singer carla() {
      return new Singer("Carla's Dream");
   }

   @Bean // by default o sg instanta din Delia se creeaza
   public Singer delia() {
      System.out.println("Se naste Delia");
      return new Singer("Delia");
   }

   @Bean
   public Song laTara() {
      System.out.println("La tara e la moda");
      return new Song("Vreau la Tara", delia());
   }
   @Bean
   public Song acadele() {
      System.out.println("La Dentist");
      return new Song("Acadele", delia());
   }

   @Bean
   public OldClass oldClass() {
      return OldClass.getInstance();
   }
}

// doar pt @Bean dintr-o @Configuration, spring face proxy-uri care intercepteaza si apeluri locale de metode !!!!

// pentru a onora: lifecycle, @Autowired, @PostConstruct sau chiar pt a-ti servi un Proxy la Delia (impresar) nu direct pe Delia)

@Component
class A {
   // o vreau pe Delia
   @Autowired
   private OldClass oldClass;

   // A (field injection)
   @Autowired
   private Singer delia; // daca field name = bean name, iti da acel bean name <- periculos (IMHO)

   @Autowired
   ApplicationContext context;

   @PostConstruct
   public void init() {
      // B: dynamic lookup
//      Singer delia = context.getBean("Delia", Singer.class);
      if (false) {
         System.out.println(delia);
      }

      System.out.println(context.getBean("oarecareFiecare"));
      System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
   }
}