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
    private Conversation conversation;

    @Override
    public void run(String... args) throws Exception {
        conversation.start();
        // TODO manage all with Spring

        System.out.println(spring.getBean("john"));
        System.out.println(spring.getBean("labelStuff"));
//        System.out.println(spring.getBean(Person.class)); // crapa ca-s doua dupa tip
    }
    @Bean
    public Conversation conversation() {
        System.out.println("Se incepe o conversatie din "  +this.getClass());
        Conversation conversation = new Conversation(john(), jane());
        System.out.println("Am construit conv");
        return conversation;
    }
    @Bean
    public Conversation argue() {
        System.out.println("Se isca o galceava");
        return new Conversation(john(), jane());
    }


    @Bean
    public Person john() {
        System.out.println("John se naste");
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


