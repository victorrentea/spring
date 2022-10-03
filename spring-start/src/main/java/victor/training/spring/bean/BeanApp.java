package victor.training.spring.bean;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@EnableAsync
//@EnableScheduling
//@EnableCaching
@SpringBootApplication
public class BeanApp /*implements CommandLineRunner*/ {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
////    @Lazy // nu e bine pe app noi, ca designul e praf => spargi in clase mai mici
    private Conversation blamestorming;

    @Value("${alta.prop}")
    int x;
    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping
    public void apiRest() {
        // risc: app porneste, dar crapa la call mai tarziu.
//        Conversation cearta = applicationContext.getBean("cearta", Conversation.class);
        blamestorming.start();
    }
}
@Configuration
class MyConfig {
    @Bean
    public Person john(@Value("${john.name}") String johnName) { // singleton
        System.out.println("Aici se naste " + johnName + "👶");
//        System.getPr
        return new Person(johnName);
    }
    // DOar intre apelurile de metode @Bean se intampla ca SPring sa-ti intercepteze apelurile si sa le fure sa faca magie. In nici un alt punct dinSpring, apeluri locale nu pot fi interceptate.
    @Bean
    public Person jane() {
        return new Person("Jane");
    }
    @Bean
    public Conversation blamestorming() {
        System.out.println("cearta");
        return new Conversation(john(null), jane());
    }
    @Bean(name = "impacarea❤️")
    public Conversation impacarea() {
        System.out.println("Impacare");
        return new Conversation(john(null), jane());
    }
}
//class SpringPeAscuns extends MyConfig {
//    @Override
//    public Person john(String whatever) {
//        // daca deja e creat, return din SingletonCacheMap
//        else
//        return super.john(environment.getPropery("john.name"));
//        // apoi inejcteaza-l, proxiaza-l, rupel
//        // pune-l in cache
//    }
//}


// --------------- linie. sub aceasta linie nu ai voie sa modifici cod
@RequiredArgsConstructor
@Data
class Conversation {
    private final Person john;
    private final Person jane;

    public void start() {
        System.out.println(john.sayHello());
        System.out.println(jane.sayHello());
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


