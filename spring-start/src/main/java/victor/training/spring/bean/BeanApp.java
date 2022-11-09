package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SpringBootApplication
//@Configuration // in cele din e si asta
public class BeanApp  {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Bean
    public Conversation conversation() {
        return new Conversation(new Person("John"), new Person("Jane"));
    }
}
@Component
class Startup {

    @Autowired
    private Conversation conversation;

    @PostConstruct
    public void run() throws Exception {
        // TODO convince Spring to do for you the line above
        conversation.start();
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


