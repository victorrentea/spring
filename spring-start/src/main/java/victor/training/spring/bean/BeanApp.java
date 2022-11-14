package victor.training.spring.bean;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class BeanApp {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }
    @Bean // still a singleton, even if multiple references to this
    // @bean exist in this @Configuration class
    public Person john() {
        System.out.println("John gets born!");
        return new Person("John"); // create manually an object
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }

    @Bean
    public Conversation conversation() {
        return new Conversation(john(), jane());// don't consider these method calls,
        // but bean references
    }
    @Bean
    public Conversation fight() {
        return new Conversation(john(), jane());
    }
}
// spring does this to all @Configuration classes
class WelcomeToJava extends BeanApp {
    @Override
    public Person john() {// dynamic override of all @Bean methods
//        if (singletonCache.contains (john)) return from cache
        return super.john();
//        put in cache
    }
}
@Component
class MoreBreadownOfClasses implements CommandLineRunner{
    @Autowired
    private Conversation conversation;

    @Override
    public void run(String... args) throws Exception {
        conversation.start();
    }
}

@Data
@RequiredArgsConstructor
class Conversation {
    private final Person john;
    private final Person jane;

    private String state;


    public void start() {
        System.out.println(john.sayHello());
        System.out.println(jane.sayHello());
    }
}


class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }
    public String sayHello() {
        return "Hello! Here is " + name;
    }
}


