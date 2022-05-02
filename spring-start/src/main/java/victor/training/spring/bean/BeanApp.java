package victor.training.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
    Conversation conversation;
    @Override
    public void run(String... args) throws Exception {
//        Conversation conversation = new Conversation(new Person("John"), new Person("Jane"));
        conversation.start();
        // TODO manage all with Spring
    }
}

@Configuration
class MyConfiguration {
    @Bean
    public Person john() { // creezi manual un bean de tip Person numit "john"
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }

}


@Component
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


