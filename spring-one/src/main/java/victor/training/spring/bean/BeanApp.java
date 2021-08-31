package victor.training.spring.bean;

import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
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
    private Conversation conversation;

    @Override
    public void run(String... args) throws Exception {
        // TODO manage all with Spring
//        Conversation conversation = new Conversation(new Person("John"), new Person("Jane"));

        conversation.start();

        // TODO alternative: "Mirabela Dauer" story :)
    }
}

@Configuration
class MyConfig {
//    @Autowired
//    @Qualifier("john")
//    private Person john;
//    @Autowired
//    @Qualifier("jane")
//    private Person jane;

    @Bean
    public Conversation conversation(/*Person john, Person jane*/) {
        System.out.println("C1");
        return new Conversation(john(), jane());
    }
    @Bean
    public Conversation conversation2(Person john, Person jane) {
        System.out.println("C2");
        return new Conversation(john(), jane());
    }
    @Bean
    public Person john() {
        System.out.println("John is born");
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }
}
@Aspect
@Component
class ForSimon {
    @Around("execution(* john(..))")
    public Object method(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Invoked john");
        return pjp.proceed();
    }
}
//class OMG extends MyConfig {
//    @Override
//    public Person john() {
//        if (john is not created ){
//            return super.john();
//            store john
//        } else {
//            return from singleton map
//        }
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
        return "Hello! Here is " + name + " from " + OldClass.getInstance().getCurrentCountry();
    }
}





