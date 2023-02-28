package victor.training.spring.bean;

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

    @Bean
    public Conversation conversation() {
        return new Conversation(new Person("John"), new Person("Jane"));
    }
}

@Component
class ConversationUsers {
    private final Conversation conversation;
//    private final Person myJohn;

    ConversationUsers(Conversation conversation) {
        this.conversation = conversation;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void onStart() {
        conversation.start();
    }
}

// you can't touch whatever is under this line ----------------------
//// it's some library code

class Conversation {
    private final Person one;
    private final Person two;

    public Conversation(Person one, Person two) {
        this.one = one;
        this.two = two;
    }

    public void start() {
        System.out.println(one.getName() + " talks with " + two.getName());
    }

    @Override
    public String toString() {
        return "Conversation{" +
               "one=" + one +
               ", two=" + two +
               '}';
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


