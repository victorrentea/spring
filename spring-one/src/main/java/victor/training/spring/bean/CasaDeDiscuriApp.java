package victor.training.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
   @Autowired
   @Qualifier("delia")
   private Singer ceNumeVreauEu;

   @PostConstruct
   public void init() {
      System.out.println(ceNumeVreauEu);
   }
}