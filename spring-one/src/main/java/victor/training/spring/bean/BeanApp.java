package victor.training.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
   public static void main(String[] args) {
      SpringApplication.run(BeanApp.class);
   }

//   @Autowired // acces by type
////    @Qualifier("john")
//   Person jane; // cauta by default un bean cu numele punctului de injectie.

   @Autowired
   Conversation conversation;

   ApplicationContext context;

   @Override
   public void run(String... args) throws Exception {
      //context.getBean() // folositor de fapt doar cand vrei sa ajungi la un ob din spring, dar esti IN AFARA SPRINGULUI
      // Servlet, Struts, JasperReports, VelocityTemplate ..
      // Regula:daca sunt in vreun @Service/@Component, NU imi injectez AppliationContext

//        Conversation conversation = new Conversation(new Person("John"), new Person("Jane"));
      conversation.start();

//      System.out.println(jane);
      // TODO manage all with Spring
   }

   @Bean
   @Order(20)
   public Person john() {
      return new Person("John");
   }

   @Bean
   @Order(10)
//    @Primary // pentru testare (to be continued Fri)
   public Person jane() {
      return new Person("Jane");
   }

   @Bean
   public Conversation conversation(Person john) {
      return new Conversation(jane(), john); // apelul LOCAL catre jane() este interceptat. (Spring genereaza o subclasa la toate @Configuratin
   }

   @Bean
   public Conversation cearta(Person john) {
      return new Conversation(jane(), john);
   }
}

@Component
class PranzCorporate implements  CommandLineRunner{
   @Autowired
   private List<Person> toti;

   @Transactional
//   @EventListener(ApplicationReadyEvent.class) // tarziu
   @EventListener(ApplicationStartedEvent.class)
   public void startupCuEvent() {

   }
   @Transactional
   @Override
   public void run(String... args) throws Exception {

   }

   @PostConstruct
   public void method() {
      System.out.println("Pranz pe banii lui sefu: " + toti);
   }

}

class SpringGeneratedSubclass extends BeanApp {
//    @Override
//    public Person jane() {
//        if (jane a fost deja creata ) {
//            return din colectia din singletone
//        } else {
//            Person newJane = super.jane();
//            singletons.add(newJane)
//            return newJane;
//
//        }
//    }
}

// NU AI VOIE SA ATINGI SURSA DE MAI JOS ! ---> e dintr-un jar.
class Conversation {
   private final Person one;
   private final Person two;

   public Conversation(Person one, Person two) {
      this.one = one;
      this.two = two;
   }

   public void start() {
      System.out.println(one.sayHello());
      System.out.println(two.sayHello());
   }

   public String toString() {
      return "Conversation(one=" + this.one + ", two=" + this.two + ")";
   }
}

class Person {
   private final String name;

   public Person(String name) {
      System.out.println("Se naste o mica stea : " + name);
      this.name = name;
   }

   public String sayHello() {
      return "Hello! Here is " + name + " from " + OldClass.getInstance().getCurrentCountry();
   }

   @Override
   public String toString() {
      return "Person{" +
             "name='" + name + '\'' +
             '}';
   }
}


