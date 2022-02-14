package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication // is a @Configuration itself.
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
    private Conversation conversation;

    @Override
    public void run(String... args) throws Exception {
//        Conversation conversation = new Conversation(new Person("John"), new Person("Jane"));
        conversation.start();
        // TODO manage all with Spring
    }
//    When there is more than one bean of the same class type, there was an identifier that can be places for the @Bean instance

    @Bean
    public Person john() {
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }


//    @Autowired
//    @Qualifier("john")
//    Person personA;
//    @Autowired
//    @Qualifier("jane") // 2+ matches for this injection point.
//    Person personB;
//    @Bean
//    public Conversation conversation(/*Person personA, Person personB*/) {
//        return new Conversation(personA, personB);
//    }
}

//  --------- without touching the code below -------------

@Component
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
    public String sayHello() {
        return "Hello! Here is " + name + " from " + OldClass.getInstance().getCurrentCountry();
    }
}


