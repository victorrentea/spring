package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableConfigurationProperties
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Aici aplicatia a pornit cu succes");

        System.out.println("Dar apoi, uite, un req HTTP:");
        fluxChematPesteREst();

//        conversation.start();
        // TODO convince Spring to do for you the line above
    }

    private void fluxChematPesteREst() {
        // e riscant sa faci getBean pt ca iti crapa la APEL, nu la startup
        // de aia preferi sa injectezi pe campuri/ctor.

//        applicationContext.getBean("johnxxoups");
//        applicationContext.getBean(AiUitat.class);
    }
}
class AiUitat {

}
@Configuration
class OClasaConfiguration {

//    @Value("${customer.name}")
//    String johnName;
    @Bean
    public Person john(@Value("${customer.name}") String johnName) { // numele beanului definit = numele metodei
        System.out.println("aici se naste john");
        return new Person(johnName);
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }
    @Bean
    public Conversation conversation(@Value("${customer.name}") String johnName) {
        System.out.println(" creez o cearta ");
        return new Conversation(john(null), jane()); // pANICA! NULL TATA>
    }
    @Bean
    public Conversation impacarea() {
        System.out.println(" creez o impacarea ");
        return new Conversation(john(null), jane()); // desi chem john() de doua ori, metoda efectiva nu ruleaza decat o data

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


