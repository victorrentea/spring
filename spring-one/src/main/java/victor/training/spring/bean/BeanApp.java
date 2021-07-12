package victor.training.spring.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

//    @Autowired
//    private Conversation conversation;

    @Autowired
    private ApplicationContext spring;

    @Override
    public void run(String... args) throws Exception {
//        Conversation conversation = new Conversation(new Person("John"), new Person("Jane"));
//        Conversation conversation = (Conversation) spring.getBean("conversation");
        Conversation conversation = spring.getBean(Conversation.class);
        conversation.start();
    }

    @Bean // o singura instanta - singleton
    public Person john() {
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }
    @Bean
    public Conversation conversation(Person john, Person jane) {
        return new Conversation(john, jane);
    }
}
// nu am acces la codul de mai jos ----------------------- Intr-un jar.

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


