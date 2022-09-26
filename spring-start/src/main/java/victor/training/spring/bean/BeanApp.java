package victor.training.spring.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Override
    public void run(String... args) throws Exception {
//        Conversation conversation = new Conversation(new Person("John"), new Person("Jane"));
        // TODO convince Spring to do for you the line above
//        conversation.start();
    }

    @Value("${john.name}")
    private String johnName;
    @Bean
    public Person john() {
        return new Person(johnName);
    }
    @Bean
    public Person spy() {
        return new Person("Spy");
    }
    @Bean
    @Primary
    public Person jane() {
        return new Person("Jane");
    }
    @Bean
    public Conversation conversation() {
        Conversation conversation = new Conversation(john(), jane());
        conversation.start();
        return conversation;
    }
}


@RestController
class MyController {
    private final Person john;

private String currentUsername;

    MyController( Person john) {
        this.john = john;
    }
    @GetMapping
    public String method() {
        System.out.println(currentUsername);
        return "different instance for each call ? " + john;
    }
}


// imagine this class can't be changed:
class Conversation {
    private final Person jane;
    private final Person john;

    public Conversation(Person jane, Person john) { // @Qualifier / injection point name wins over @Primary
        this.jane = jane;
        this.john = john;
    }

    public void start() {
        System.out.println("Chat start ");
        System.out.println(jane.sayHello());
        System.out.println(john.sayHello());
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

    @Override
    public String toString() {
        return "Person{" +
               "name='" + name + '\'' +
               '}';
    }
}


