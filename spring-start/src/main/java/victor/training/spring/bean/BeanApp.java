package victor.training.spring.bean;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class BeanApp {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
    private Conversation makeup;

    @EventListener(ApplicationStartedEvent.class)
    public void onStart() {
        makeup.start();
    }

}
@Configuration// (proxyBeanMethods = false)
class MyConfig {
    @Bean
    public Person john(@Value("${john.name}") String johnName) { // Beans have names! here the name is "john"
        System.out.println("THIS RUNS");
        return new Person(johnName);
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }
    @Bean
    public Conversation breakup() {
        System.out.println("BREAKUP");
        // calling other local @Bean methods inside the same @Configuration
        return new Conversation(john(null), jane());
    }
    @Bean
    public Conversation makeup() {
        System.out.println("MAKEUP");
        return new Conversation(john(null), jane());
    }
}
//class HackAtRuntime extends MyConfig {
//    @Bean
//    public Person john() {
//        // spring intercepts the call to john() and
//        // looks up the john instance in the singletonMap
// in case is not in the map calls super.john()
//    }
//}
// for @Configuration classes only, Spring creates a proxy
// to your class in order to intercept the @Bean methods calls
// and cache the results and enforce the lifecycle of the beans

// in a jar you cannot change-------
@Data
@RequiredArgsConstructor
class Conversation {
    private final Person john;
    private final Person jane;

//    public Conversation(Person jane,
//// if the NAME of the param matches the bean name you want,
//            // the injection is not ambiguous anymore
//                        Person john) {
//        this.one = jane;
//        this.two = john;
//    }

    public void start() {
        System.out.println(john.getName() + " talks with " + jane.getName());
    }
}


//@Component // i need this class to be a MULTI-TON: 2 instances per app
class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}


