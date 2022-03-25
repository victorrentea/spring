package victor.training.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
    private Conversation conversation;

    @Value("#{john.name}") // SpEL
    private String johnName;
//    @Autowired
//    public void setJohnName(Person john) {
//        this.johnName = john.getName();
//    }

    @Override
    public void run(String... args) throws Exception {
        conversation.start();
        System.out.println("name : " + johnName);

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
    @Bean // also singleton be default
    public Conversation conversation(@Value("${john.name}") String name) {
        System.out.println("Dinner time : " + name);
        return new Conversation(john(), jane());
    }
    @Bean // also singleton be default
    public Conversation monologueInBathroom() { // name of the bean =  "monologueInBathroom"
        System.out.println("Shower time");
        return new Conversation(john(), john()); // calls to john() don't really go to line :27. Instead
        // instead they are captured by Spring somehow
    }
    @Bean // also singleton be default
//    public Conversation chatInCar(@Qualifier("john") Person one,@Qualifier("jane")  Person two) { // also works, but more clutter
    public Conversation chatInCar(Person john, Person jane) { // simpler.
        System.out.println("In car");
        return new Conversation(john, jane);
    }
}

//class RuntimeSubclass extends MyConfig {
//    @Override
//    public Person john() {
//    @Override
//    public Person john() {
//    @Override
//    public Person john() {
//        if john is already created, retrun from cache
//        return super.john();
//    }
//}
// spring never instantiates MyConfig directly, but only new RuntimeSubclass()
//===================================================
// YOU ARE NOT ALLOWED TO CHANGE CODE BELOW (JAR)

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

    public String toString() {
        return "Conversation(one=" + this.one + ", two=" + this.two + ")";
    }
}

// you need more than 1 instance of a class, but differently configured
class Person {
    private final String name;

    public Person(String name) {
        System.out.println( name + " is oborn");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String sayHello() {
        return "Hello! Here is " + name + " from " + OldClass.getInstance().getCurrentCountry();
    }
}


