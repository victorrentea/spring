package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
//    @Lazy // nu e bine pe app noi, ca designul e praf => spargi in clase mai mici
    private Conversation conversation;

    @Override
    public void run(String... args) throws Exception {
//        Conversation conversation = new Conversation(/*new Person("John"), new Person("Jane")*/);
        // TODO convince Spring to do for you the line above
        conversation.start();
    }
}
@Configuration
class MyConfig {

    @Bean
    public Person john() {
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }
}

@Data
@Component
class Conversation {
    private final Person one;
    private final Person two;

    public Conversation(@Qualifier("john") Person one, @Qualifier("jane") Person two) {
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


