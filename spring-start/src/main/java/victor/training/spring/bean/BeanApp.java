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
public class BeanApp  {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }
    // 2 instances of the same class differently configured
    // in a class annotated with @Configuration (in this case, meta-annotated, inherited via annotations)
    @Bean
    public Person john() {
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }
//    @Lazy // dark, avoid if you can solve the cycle by improving design
    // or spring.main.allow-circular-references=true  (worse!)

}

@Component
class RunMe implements CommandLineRunner{
    @Autowired
    private Conversation conversation;
    @Override
    public void run(String... args) throws Exception {
        conversation.start();
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
