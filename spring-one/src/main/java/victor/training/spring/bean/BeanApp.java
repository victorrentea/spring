package victor.training.spring.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

//    @Autowired
//    private Conversation conversation;

    @Autowired
    private ApplicationContext spring;

    @Override
    public void run(String... args) throws Exception {
//        Conversation conversation = new Conversation(new Person("John"), new Person("Jane"));
//        Conversation conversation = (Conversation) spring.getBean("conversation");

        Conversation conversation = spring.getBean(Conversation.class);
        conversation.start();

        Conversation conversation2 = spring.getBean(Conversation.class);
        conversation2.start();
    }

    @Bean // o singura instanta - singleton
    public Person john() {
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }
    @Bean
    @Scope("prototype")
    public Conversation conversation() {
        System.out.println("Cum mama masii merge ?");
        return new Conversation(john(), jane()); // desi aici john() se cheama de 2 ori (pt ca sunt 2 convrsatii pornite),
        // metoda @Bean john de mai sus nu se invoca decat o singura data!
        // CUM?
        // Metoda @Bean este overriden de catre spring (hackuita) pentru a NU chema direct new Person()


        //Adica

    }
}
// nu am acces la codul de mai jos ----------------------- Intr-un jar.

@Data
class Conversation {
    private final Person one;
    private final Person two;

    public Conversation(Person john, Person jane) {
        System.out.println("O noua instanta de conversatie");
        this.one = john;
        this.two = jane;
    }

    public void start() {
        System.out.println(one.sayHello());
        System.out.println(two.sayHello());
    }
}

class Person {
    private final String name;

    public Person(String name) {
        System.out.println("Mama il naste pe " + name);
        this.name = name;
    }
    public String sayHello() {
        return "Hello! Here is " + name + " from " + OldClass.getInstance().getCurrentCountry();
    }
}

class SubclasaFacutaDeSpring extends BeanApp {
    @Override
    public Person john() {
        // if (e singleton si deja il am) {return din context }
        // else
            return super.john();
        // pune in context si intoarce
    }
}

