package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@SpringBootApplication //@Configuration
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
}

@Data
@Service
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
    // logica
}


