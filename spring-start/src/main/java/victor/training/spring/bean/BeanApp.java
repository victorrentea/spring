package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class BeanApp {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }


    @Bean
    public Person john() {
        return new Person("John");
    }
    @Bean
    public Person jane() { // method name = bean name
        return new Person("Jane");
    }

    @Bean
    public Conversation conversation() {
        return new Conversation(john(), jane());
    }
}
@Configuration
class SecondConfig implements CommandLineRunner{
    @Autowired
    private Conversation conversation;
    @Override
    public void run(String... args) throws Exception {
        // TODO convince Spring to do for you the line above
        conversation.start();
    }
}

//@Qualifier
//@Retention(RetentionPolicy.RUNTIME)
//@interface First {}
//

@Data
//@Component
class Conversation { // by default the name of this bean is = "conversation"
    private final Person john;
    private final Person jane;

//    public Conversation(Person john,  Person jane) { // if the injection point name matches a bean name => no @Qualifier is needed is needed
//        this.one = john;
//        this.two = jane;
//    }

    public void start() {
        System.out.println(john.sayHello());
        System.out.println(jane.sayHello());
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


