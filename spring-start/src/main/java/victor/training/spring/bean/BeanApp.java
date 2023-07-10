package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.time.Clock;
import java.time.LocalDateTime;

@SpringBootApplication //@Configuration
public class BeanApp {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
    private Conversation conversation;

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping
    public void onStart() {
        // NU FOLOSI: daca poti face prin @Autowired/constructor injection
//        Conversation conversation = applicationContext.getBean("conversation", Conversation.class);
        conversation.start();
    }

}
@Component
class TimeDependentLogic {
    @Autowired
    private Clock clock;

    public void logica() {
//        var transfer = new Transfer();
//        transfer.setCreateTime(LocalDateTime.now(clock));
//        transferRepo.save(transfer);
    }
}
@Configuration// (proxyBeanMethods = false)
class MyConfig {

    @Bean
    public Person john(@Value("${john.name:#{null}}") String johnName) {
        System.out.println("John se naste : " +johnName + " dar nulu asta are uper ");
        return new Person(johnName);
    }

    @Bean
    public Clock clock () {
        return Clock.systemDefaultZone();
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


