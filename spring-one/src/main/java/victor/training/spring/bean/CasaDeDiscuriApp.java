package victor.training.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
   @Bean
   public Singer delia() {
      return new Singer("Delia");
   }
  @Bean
  public Song laTara() {
     return new Song("Vreau la Tara", delia());
  }


}

@Component
class A {
   // o vreau pe Delia

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
   }
}