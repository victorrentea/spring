package victor.training.spring.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
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
    @Primary
    public Conversation impacare() { // numele bean = numele metodei
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
    private ConfigResolver configResolver;
    @Autowired
//    @Qualifier("cearta")
    private Conversation orice; // recent @Qualifier nu mai e necesar dc numele
    // 'punctului de injectie' matcheuieste numele beanului -> se prinde

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void run() throws Exception {
//        ConfigResolver c = applicationContext.getBean("nume", ConfigResolver.class); // riscant pentru ca nu e
        // deploy-time checked cum sunt injectiile

        System.out.println("config=" + configResolver.config());
        System.out.println("Toate beanurile de tipul Person in lista: " + people);
        orice.start();
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

interface ConfigResolver {
    String config();
}
// Use-case: daca in functie de unde deployez vreau una sau alta, folsoesc profile
// polymorphism
@Primary
//@Profile("prod")  NEVER// mesaj: # I don’t always test my code, but when I do, I do it in production. YOLO
// R: te dau afar: prea riscant.
@Profile("!local")
@Component
class COnfigResolverDeProd implements ConfigResolver{
    @Override
    public String config() {
        return "prod";
    }
}

@Component
class ConfigResolverDeLocal implements ConfigResolver{
    @Override
    public String config() {
        return "HAPPY";
    }
}
