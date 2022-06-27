package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Override
    public void run(String... args) throws Exception {


//        conversation.start();
        // TODO convince Spring to do for you the line above
    }
}

@Configuration
class OClasaConfiguration {
    @Bean
    public Person john() { // numele beanului definit = numele metodei
        System.out.println("aici se naste john");
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }
    @Bean
    public Conversation conversation() {
        System.out.println(" creez o cearta ");
        return new Conversation(john(), jane());
    }
    @Bean
    public Conversation impacarea() {
        System.out.println(" creez o impacarea ");
        return new Conversation(john(), jane()); // desi chem john() de doua ori, metoda efectiva nu ruleaza decat o data

    }
}
// in tot springu, doar AICI se intampla ca apeluri locale de metode din aceeasi clasa sa fie INTERCEPTATE.
// springu
//class Tzeapa extends OClasaConfiguration {
//    @Override
//    public Person john() { // iti fura apelul!
//        if (in mapa lu Andrei am singleton cu numele john, ) return
//        Person obj = super.john();
//        obj.ruleaza @postconstruct, @Autowire
//        mapaLuAndr3ei.put(obj)
//        return obj;
//    }
//}




@Data
//@Component // il sterg ca asa vreau eu
class Conversation {
    private final Person one;
    private final Person two;

    public Conversation( Person john, @Qualifier("jane") Person two) {
        this.one = john;
        this.two = two;
    }

//    @PostConstruct
    @EventListener(ApplicationStartedEvent.class) // poate sapa aici daca vrei control mai fin asupra CAND rulezi in startup-ul springuluil
    public void start() {
        System.out.println("Incepe discutia");
        System.out.println(one.sayHello());
        System.out.println(two.sayHello());
    }
}

// ne imaginam ca Person este dintr-un JAR, si nu poti sa ii modifici codul
class Person {
    private final String name;
    public Person(String name) {
        this.name = name;
    }

    @PostConstruct
    public void maNasc() {
        System.out.println("Uaaa " +name);
    }

    public String sayHello() {
        return "Hello! Here is " + name;
    }
}


