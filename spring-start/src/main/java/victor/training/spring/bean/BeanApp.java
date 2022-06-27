package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
    private Person person;
    @Autowired
    private Person person2;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Am primit 2 instante diferite de Person: " + person + " si  " + person2);
        person.setName("John");
        person2.setName("Jane");
        Conversation conversation = new Conversation(person, person2);
//                new Person().setName("John"),
//                new Person().setName("Jane"));
        conversation.start();
        // TODO convince Spring to do for you the line above
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


@Component
@Scope("prototype")
class Person {
    private  String name;
//    public Person(String name) {
//        this.name = name;
//    }
    public void setName(String name) {
        this.name = name;
    }
    public String sayHello() {
        return "Hello! Here is " + name;
    }
}


