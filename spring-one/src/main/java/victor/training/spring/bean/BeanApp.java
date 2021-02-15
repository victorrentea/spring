package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

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

        // TODO alternative: "Mirabela Dauer" story :)
    }
}

@Data
class Conversation {
    private final Person one;
    private final Person two;

    public Conversation(Person one, Person two) {
        this.one = one;
        this.two = two;
    }

    public void start() {
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
        return "Hello! Here is " + name + " from " + OldClass.getInstance().getCurrentCountry();
    }
}






@Component
class Tata {
    @Autowired
    private Copil mihai;
    @Autowired
    private List<Copil> toti;

    @PostConstruct
    public void method() {
        System.out.println("Copilul servit este: " + mihai);
        System.out.println("Toti copiiiiiii: " + toti);
    }
}

interface Copil {
}
@Component
@Profile("localDev")
class MihaiViteazu implements Copil{} // e preferatu acasa, pe masina, pe local
@Component
@Profile("!localDev")
class Maria implements Copil {} // e preferata in PROD


@Profile("localDev")
@Configuration
class DummySecurityConfig {

}
@Profile("!localDev")
@Configuration
class SSOSecureLdapJWTSecurityConfig {

}


@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@interface Preferat {
}