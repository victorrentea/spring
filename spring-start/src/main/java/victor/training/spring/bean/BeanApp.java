package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import victor.training.spring.supb.Deep;

@SpringBootApplication
@ComponentScan(basePackages = {"victor.training.spring.supb","victor.training.spring.bean"})
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
    private Deep deep;

    // 2 instances of the same class differently configured
    @Override
    public void run(String... args) throws Exception {
        conversation.start();
    }

    // in a class annotated with @Configuration (in this case, meta-annotated, inherited via annotations)
    @Bean
    public Person john() {
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }

    @Lazy
    @Autowired
    private Conversation conversation;
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
