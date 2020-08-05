package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication
public class PoetApp implements CommandLineRunner {
   public static void main(String[] args) {
      SpringApplication.run(PoetApp.class);
   }

   @Autowired
   ApplicationContext totSpringu;
   @Override
   public void run(String... args) throws Exception {
      System.out.println("Noua: " + poezieNoua);
      System.out.println(totSpringu.getBean("luceafarul", Poezie.class));
      System.out.println(totSpringu.getBean("luceafarul", Poezie.class));
      System.out.println(totSpringu.getBean("luceafarul", Poezie.class));

      System.out.println(deadPoetsSociety);
   }

//   class MyPoetFactory {
//      Map<url, Poet>
//      public Poet createForUrl(String url) {
//
//      }
//   }

   @Value("#{eminescu.scrie().toUpperCase()}")
   private String poezieNoua;

   @Autowired
    @Qualifier("eminescu")
   Poet geniu;

   @Autowired
   List<Poet> deadPoetsSociety;

   @Bean
   public Poet eminescu() {
      return new Poet("Eminescu");
   }
   @Scope("prototype")
   @Bean
   public Poezie luceafarul() {
      System.out.println("Tiparesc o poezie");
      return new Poezie("Luceafarul", eminescu()); // acest apel chiar E proxiat!!! WTH?!
      // Doar in metode @Bean din @Configuration
   }
   @Bean
   public Poet bacovia() {
      return new Poet("Bacovia");
   }
//   //cand pornest app, spring face
//   { new PoetApp(){
//      @Override
//      public Poet eminescu() {
//         // singleton stuff
//         // proceseaza @Autowired, @Value, aspecte
//         return super.eminescu();
//      }
//   }}
}

@Data
class Poezie {
   private final String title;
   private final Poet autor;
}

@Data
class Poet {
   private final String name;

   public Poet(String name) {
      System.out.println("Se naste un geniu: " + name);
      this.name = name;
   }
   public String scrie() {
      return "aaa";
   }
}