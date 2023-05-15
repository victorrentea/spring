package victor.training.spring.bean;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class BeanApp {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }
    @Autowired
    private Conversation conversation;

    @EventListener(ApplicationStartedEvent.class)
    public void onStart() {
//        Conversation conversation = new Conversation(
//                new Person("John"),
//                new Person("Jane"));
        // TODO register the two persons and the conversation as Spring beans
        conversation.start();
    }

}
@Configuration
class ProjectConfiguration {

    @Bean
    public Person john() {
        return new Person("John");
    }
    @Bean
    public Person jane() { // instante configurate de mana (sa fac eu NEW), ev mai de mai multe ori
        return new Person("Jane");
    }

    // instante din librarii pe care nu pot sa le adnotez
    @Bean
    public Conversation conversation(Person john, Person jane) {
        return new Conversation(john, jane);
    }
}
// ------
// librarie jar pe care nu poti sa-l modifici
@RequiredArgsConstructor
@Data
//@Component
class Conversation {
    private final Person john;
    private final Person jane;
    public void start() {
        System.out.println(john.getName() + " talks with " + jane.getName());
    }
}


class Person {
    private final String name;
    public Person(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}


