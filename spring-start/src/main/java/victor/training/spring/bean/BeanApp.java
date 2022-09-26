package victor.training.spring.bean;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Optional;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }


//    @Bean
//    HikariDataSource dataSource(DataSourceProperties properties) {
//        HikariDataSource dataSource = .// my stuff roperties, HikariDataSource.class);
//        if (StringUtils.hasText(properties.getName())) {
//            dataSource.setPoolName(properties.getName());
//        }
//        return dataSource;
//    }

    @Value("${another.prop}")
    private String unchanged;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("unchanged " + unchanged);
//        Conversation conversation = new Conversation(new Person("John"), new Person("Jane"));
        // TODO convince Spring to do for you the line above
//        conversation.start();
    }

    ;
    @Bean
    public Person john(@Value("${john.name}") String johnName) {
        System.out.println("John is born! : " + johnName);
        return new Person(johnName);
    }
    @Bean
    public Person spy() {
        return new Person("Spy");
    }
    @Bean
    @Primary
    public Person jane() {
        return new Person("Jane");
    }
    @Bean
    public Conversation conversation() {
        System.out.println("Conv1");
        Conversation conversation = new Conversation(john(null), jane());
        // #1 how is this possible? I pass null to a @Bean method, and that method actually runs with a non-null param
        conversation.start();
        return conversation;
    }
    @Bean
    public Conversation conversation2() {
        System.out.println("Conv2");
        Conversation conversation = new Conversation(john(null), jane());
        // #2 no matter how many times i call john(), the john() method runs a single time ?!?!?!!
        conversation.start();
        return conversation;
    }

    // #1 and #2 are possible because spring creates a subclass of your @Configuration classes so that local method calls
    // get intercepted !! ONLY HERE does a PROXY intercept LOCAL METHOD CALLS.!!

}
//class SpringUnderTheHood extends BeanApp {
//    @Override
//    public Person john(String johnName) {
//        //heheheh
//        if john is created already { return from cache }
//         else
//        return super.john(johnName from properties files);
//    }
//}


@RestController
class MyController {
    private final Person john;

private String currentUsername;

    MyController( Person john) {
        this.john = john;
    }
    @GetMapping
    public String method() {
        System.out.println(currentUsername);
        return "different instance for each call ? " + john;
    }
}


// imagine this class can't be changed:
class Conversation {
    private final Person jane;
    private final Person john;

    public Conversation(Person jane, Person john) { // @Qualifier / injection point name wins over @Primary
        this.jane = jane;
        this.john = john;
    }

    @PostConstruct
    public void start() {
        System.out.println("Chat start ");
        System.out.println(jane.sayHello());
        System.out.println(john.sayHello());
    }
}


class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }
    public String sayHello() {
        return "Hello! Here is " + name.toUpperCase();
    }

    @Override
    public String toString() {
        return "Person{" +
               "name='" + name + '\'' +
               '}';
    }
}


