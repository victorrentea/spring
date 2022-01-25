package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

//    @Autowired
//    private Person john;
//    @Autowired
//    private Person jane;
    @Autowired
    private Conversation conversation;

    @Override
    public void run(String... args) throws Exception {
        conversation.start();
    }
    @Bean
    public Person john() {
        System.out.println("John is born!");
        return new Person("John");
    }
    @Bean
    public Person jane() {
        System.out.println("Jane is born!");
        return new Person("Jane");
    }
    @Bean
    public Conversation conversation(Person john, Person jane) {
        return new Conversation(john, jane);
    }
}


// ------------- you are not allowed to edit code below, because it's a library code --------------

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
        return "Hello! Here is " + name + " from " + OldClass.getInstance().getCurrentCountry();
    }
}


