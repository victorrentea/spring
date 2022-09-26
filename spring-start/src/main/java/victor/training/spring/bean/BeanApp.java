package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Override
    public void run(String... args) throws Exception {
        Conversation conversation = new Conversation(new Person("John"), new Person("Jane"));
        // TODO convince Spring to do for you the line above
        conversation.start();
    }

    @Bean
    public Person john() {
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }

}

@RestController
class MyController {
    private final Person person;

    MyController( @Qualifier("jane") Person person) {
        this.person = person;
    }
    @GetMapping
    public String method() {
        return "different instance for each call ? " + person;
    }
}

@Data
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


