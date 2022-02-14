package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;

@SpringBootApplication // is a @Configuration itself.
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
    private Conversation conversation;

    @Override
    public void run(String... args) throws Exception {
        conversation.start();
    }
//    When there is more than one bean of the same class type, there was an identifier that can be places for the @Bean instance

    @Bean
    public Person john() {
        System.out.println("CALLING john()");
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }
    @Bean
    public Conversation conversation() {
        return new Conversation(john(), jane()); // how the heck ?!!!
    }
    @Bean
    public Conversation conversation2() {
        return new Conversation(john(), jane()); // how the heck ?!!!
    }
}

// every @Configuration is subclassed by spring dynamically . HUH?!
//class Hack extends     BeanApp {
//    @Override
//    public Person john() {
//        if (this bean is alread creeated ) {return the same instance;}
//         else {
//            r = super.john();
//            process @Autowired; @PostConsturt....
//            add to list of singletons
//            return r
//        }
//    }
//}

//  --------- without touching the code below -------------

@Data
class Conversation {
    private final Person one;
    private final Person two;

    public Conversation(Person john, Person jane) {
        this.one = john;
        this.two = jane;
    }

    public void start() {
        System.out.println(one.sayHello());
        System.out.println(two.sayHello());
    }
}


class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }
    @PostConstruct
    public void post() {
        System.out.println("@PostConstruct");
    }
    public String sayHello() {
        return "Hello! Here is " + name + " from " + OldClass.getInstance().getCurrentCountry();
    }

    @EventListener(ApplicationStartedEvent.class)
    public void onStartup() {
        System.out.println("STARTUP " + name);
    }
}


