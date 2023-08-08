package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@SpringBootApplication//(proxyBeanMethods = false)
public class BeanApp {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    // i need 2+ instances of the same type as beans
    // i need manual config of the instances
    // i want to add a class from a library to my spring context
    @Bean
    public Person jane() {
        return new Person("Jane");
    }

    ;
    @Bean // in CLASS annotated with @Configuration
//    @Profile()
//    @ConfigurationProperties
//    @Primary
//    @Scope
    public  Person john(@Value("${john.name}") String johnName) { // creates manually a bean named 'john' of type Person
        System.out.println(johnName + " born👶");
        return new Person(johnName);
    }
    @Bean
    public Conversation conversation(Person jane) {
        System.out.println("That's impossible1");
        return new Conversation(john(null), jane);
    }
    @Bean
    public Conversation brainstorm(Person jane) {
        System.out.println("That's impossible2");
        return new Conversation(john(null), jane);
    }
}
// spring creates a subclass of every @Configuration class in order to
// override the public @Bean methods to enforce the spring semantic (singleton, autowqired,
// WARNING: this is THE ONLY place in Spring Framework where LOCAL METHOD CALLS ARE INTERCEPTED
//class SubClass extends BeanApp {
//    @Override
//    public Person john() {
//        // spring is in control: can fetch more @Value properties
// can enforce singleton lifecycle (and instantiate the bean ONCE)
// can call @PostConstruct on Person
//        return super.john();
//    }
//}




@Component
class SecondClass {
    @Autowired
    Conversation conversation;
    @EventListener(ApplicationStartedEvent.class)
    public void onStart() {
        conversation.start();
    }
}

// ----
// below this line the code comes from a library that I cannot touch
@Data
class Conversation {
    private final Person john;
    private final Person jane;

    public Conversation(Person john, Person jane) {
        this.john = john;
        this.jane = jane;
    }

    public void start() {
        System.out.println(john.getName() + " talkss with " + jane.getName());
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
}


