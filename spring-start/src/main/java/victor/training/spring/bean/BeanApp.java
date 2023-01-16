package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class BeanApp {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Bean
    public Person john() {
        return new Person("John");
    }

    @Bean
    public Person jane() {
        return new Person("Jane");
    }

    @Bean
    public Conversation conversation(Person john, Person jane) { // injectie de dep in param metodei @Bean
        return new Conversation(john, jane);
    }




}
@Component
class CandCoduSeImbacsesteMereuEBineSaIeiOGuraDeAerProaspat_ClasaNoua implements CommandLineRunner{
    @Autowired
    private Conversation conversation;

    @Override
    public void run(String... args) throws Exception {
        conversation.start();
    }
}
/// ---- sub linie consideram librarie NU POTI modifica

@Data
class Conversation {
    private final Person one;
    private final Person two;

    public Conversation(Person one, Person two) {
        this.one = one;
        this.two = two;
    }

    public void start() {
        System.out.println("Starting conversation...");
        System.out.println(one.sayHello());
        System.out.println(two.sayHello());
    }
}


class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }
    public String sayHello() {
        return "Hello! Here is " + name;
    }
}


