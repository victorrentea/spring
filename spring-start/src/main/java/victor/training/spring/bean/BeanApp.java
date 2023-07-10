package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@SpringBootApplication //@Configuration
public class BeanApp {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
    private Conversation conversation;

    @EventListener(ApplicationStartedEvent.class)
    public void onStart() {
        conversation.start();
    }

}
@Configuration// (proxyBeanMethods = false)
class MyConfig {

    @Bean
    public Person john(@Value("${john.name}") String johnName) {
        System.out.println("John se naste : " +johnName);
        return new Person(johnName);
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }
    @Bean
    public Conversation conversation(Person john, Person jane) {
        System.out.println("Conversatia");
        return new Conversation(john, jane);
        // cand dintr-o metoda @bean apelezi alta metoda @Bean intr-un @Configuration,
        // => nu chemi de fapt metoda reala ci Spring iti prinde apelul
    }
    @Bean
    public Conversation impacarea() {
        System.out.println("IMpacarea");
        return new Conversation(john(null), jane());
    }
}
// spring, la runtime
//class Hack extends MyConfig {
//    @Override
//    public Person john() {
//        // daca am in cahce de singleton >> return
//        altfel
//        return super.john();
// ruleaza post construct
//        put in cache ...
//    }
//}

//// --- linie : nu ai voie sa modifici nimic sub (.jar)
@Data
class Conversation {
    private final Person john;
    private final Person jane;

    public void start() {
        System.out.println(john.getName() + " talks with " + jane.getName());
    }
}


class Person {
    private final String name;
    public Person(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    // logica
}


