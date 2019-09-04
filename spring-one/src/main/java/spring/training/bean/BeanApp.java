package spring.training.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
    private ApplicationContext spring;

    @Autowired
    @Qualifier("jane")
    private Person janePerson;

    @Override
    public void run(String... args) throws Exception {
        Conversation conversation = new Conversation(
                new Person("John"),
                new Person("Jane"));
        conversation.start();
        // TODO manage all with Spring

        System.out.println(spring.getBean("john"));
        System.out.println(spring.getBean("labelStuff"));
//        System.out.println(spring.getBean(Person.class)); // crapa ca-s doua dupa tip


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

@Service
class LabelStuff {

}


