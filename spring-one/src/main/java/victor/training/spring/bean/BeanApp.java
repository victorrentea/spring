package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
// @Configuration
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
    public Person john(@Value("${john.name}")String johnName) {
        System.out.println(johnName+" is born!");
        return new Person(johnName);
    }
    @Bean
    public Person jane() {
        System.out.println("Jane is born!");
        return new Person("Jane");
    }
    @Bean
    public Conversation conversation() {
        System.out.println("now new Conversation");
        return new Conversation(john(null), john(null)); // how the hell these 2 method calls lead to one single "John is born!" message in the console

        // THE MAGIC of @Bean in a @Configuration:
        // any other @Bean method you call, you get into a @OVerride that Spring generated for THAT method.
    }
}

//class SpringHahahaha extends BeanApp {
//    @Override
//    public Person john() {
//        if (already there) return that one;
//        ....
//        return super.john();
//    }
//}


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


