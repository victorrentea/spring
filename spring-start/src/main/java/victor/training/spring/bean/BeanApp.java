package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.beans.Transient;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Override
    public void run(String... args) throws Exception {
//        Conversation conversation = new Conversation(new Person("John"), new Person("Jane"));
//        // TODO convince Spring to do for you the line above
//        conversation.start();
    }
    
    @Bean
//    @Transactional
    public Person john(@Value("${john.name}") String name) {
        System.out.println("John se naste " + name);
        return new Person(name);
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }

    @Bean
    public Conversation cearta(Person john) {
        return new Conversation(john, jane()); // SINGURUL LOC DIN SPRING UNDE APELURI DE METODE LOCALE SUNT INTERCEPTATE
    }
    @Bean
    public Conversation impacarea() {
        return new Conversation(john("null"), jane());
    }
}

//class SpringPeSubVineSiTrageUnExtends extends BeanApp {
//    @Override
//    public Person jane() {
//        System.out.println("Te-am prins");
//        hashMap.get(amdin asta)?return
//            fal
//        injecteazal cu vaccin
//        put in map
//        return super.jane();
//    }
//}

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


