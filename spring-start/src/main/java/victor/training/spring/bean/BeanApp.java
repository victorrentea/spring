package victor.training.spring.bean;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
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
        conversation.start();
    }

    @Bean
    public Person john() {
        return new Person("John");
    }
    @Bean // 2... instances of one type, manually configured
    public Person jane() {
        return new Person("Jane");
    }
    @Bean//I can't touch the code of Conversation.java
    public Conversation conversation(Person john, Person jane) {
        return new Conversation(john, jane);
    }
}
// -- imagine you don't have acecss to the code of Conversation
@RequiredArgsConstructor
@Data
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


