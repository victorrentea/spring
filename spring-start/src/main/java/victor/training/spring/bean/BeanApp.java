package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Override
    public void run(String... args) throws Exception {


//        conversation.start();
        // TODO convince Spring to do for you the line above
    }
}

@Configuration
class OClasaConfiguration {
    @Bean
    public Person john() { // numele beanului definit = numele metodei
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }
}

@Data
@Component
class Conversation {
    private final Person one;
    private final Person two;

    public Conversation( Person john, @Qualifier("jane") Person two) {
        this.one = john;
        this.two = two;
    }

//    @PostConstruct
    @EventListener(ApplicationStartedEvent.class) // poate sapa aici daca vrei control mai fin asupra CAND rulezi in startup-ul springuluil
    public void start() {
        System.out.println("Incepe discutia");
        System.out.println(one.sayHello());
        System.out.println(two.sayHello());
    }
}

// ne imaginam ca Person este dintr-un JAR, si nu poti sa ii modifici codul
class Person {
    private final String name;
    public Person(String name) {
        this.name = name;
    }
    public String sayHello() {
        return "Hello! Here is " + name;
    }
}


