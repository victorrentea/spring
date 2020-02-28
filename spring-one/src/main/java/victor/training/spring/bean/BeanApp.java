package victor.training.spring.bean;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }


    @Override
    public void run(String... args) throws Exception {
        Conversation conversation = new Conversation(new Person("John"), new Person("Jane"));
        conversation.start();
        // TODO manage all with Spring
    }
}


