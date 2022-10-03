package victor.training.spring.bean;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
//    @Lazy // nu e bine pe app noi, ca designul e praf => spargi in clase mai mici
    private Conversation cearta;

    @Override
    public void run(String... args) throws Exception {
//        Conversation conversation = new Conversation(/*new Person("John"), new Person("Jane")*/);
        // TODO convince Spring to do for you the line above
        cearta.start();
    }
}
@Configuration
class MyConfig {

    @Bean
    public Person jane() {
        return new Person("Jane");
    }
    @Bean
    public Person john(@Value("${john.name}") String johnName) { // singleton
        System.out.println("Aici se naste John👶");
        return new Person(johnName);
    }
    @Bean
    public Conversation cearta() {
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


