package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class BeanApp {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

//    @Autowired
//    private Conversation conversation;

    @EventListener(ApplicationStartedEvent.class)
    public void onStart() {
        Conversation conversation = new Conversation(new Person("John"), new Person("Jane"));
        // TODO register the two persons and the conversation as Spring beans
        conversation.start();
    }

    @Bean
    public Person john() {
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }
}
@Data
@Component
class Conversation {
    private final Person one;
    private final Person two;

    public Conversation(@Qualifier("john") Person one, @Qualifier("jane") Person two) {
        this.one = one;
        this.two = two;
    }

    public void start() {
        System.out.println(one.getName() + " talks with " + two.getName());
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


