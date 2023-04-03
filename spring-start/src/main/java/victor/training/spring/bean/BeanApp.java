package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

        // TODO register the two persons and the conversation as Spring beans
        conversation.start();
    }

}
@Configuration
class MyConfig {
    @Bean
    public Person john() { // Beans have names! here the name is "john"
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }
}
@Component
@Data
class Conversation {
    private final Person one;
    private final Person two;

    public Conversation(@Qualifier("john") Person one,
                        @Qualifier("jane") Person two) {
        this.one = one;
        this.two = two;
    }

    public void start() {
        System.out.println(one.getName() + " talks with " + two.getName());
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


