package victor.training.spring.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@Configuration // (proxyBeanMethods = false) // in cele din e si asta
public class BeanApp {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Bean
    public Conversation conversation() { // numele bean = numele metodei
        System.out.println("impacarea");
        return new Conversation(john(), jane());
    }
    @Bean
    public Conversation cearta() {
        System.out.println("cearta");
        return new Conversation(john(), jane());
    }

    @Bean
    public Person john() {
        System.out.println("Se naste John"); // cum e posibil ca cele 2 apeluri de sus sa nu ajunga
        // sa cheme de fapt methoda john asta.
        // CUM DE FAPT SE POATE ASA CEVA

        // ATENTIE: aici este singurul loc din Spring incare apeluri LOCALE de metode sunt "furate" de spring
        // (interceptate)
        // IN NICI UN ALT PUNCT DIN SPRING in afara de @Configuration, apelurile locale nu sunt proxyate(interceptate)
        return new Person("John");
    }

    @Bean
    public Person jane() {
        return new Person("Jane");
    }
// in realitate
//    @Bean
//    public ThreadPoolTaskExecutor method() {
//        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
//        threadPool.setCorePoolSize(10);
//        threadPool.setQueueCapacity(100);
//        return threadPool;
//    }
}
class SubclasaPeInserat extends BeanApp {
//    @Override
//    public Person john() {
//        // l-am creat deja pe john? -> return
////        else return super.john(); si pune in cache
//    }
}

@Component
class StartUp { // numele ei default este = "startUp" (exactNumele clasei cu lower lainceput

    @Autowired
    private List<Person> people;

    @Autowired
//    @Qualifier("cearta")
    private Conversation cearta; // recent @Qualifier nu mai e necesar dc numele
    // 'punctului de injectie' matcheuieste numele beanului -> se prinde

    @PostConstruct
    public void run() throws Exception {
        System.out.println("Toate beanurile de tipul Person in lista: " + people);
        cearta.start();
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

@ToString
class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    public String sayHello() {
        return "Hello! Here is " + name;
    }
}


