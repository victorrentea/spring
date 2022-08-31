package victor.training.spring.bean;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.Clock;
import java.util.List;

@SpringBootApplication
public class BeanApp {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }


    @Bean
    public Person john(@Value("${john.name}") String johnName) {
        System.out.println("in the john @Bean method");
        return new Person(johnName);
    }
    @Bean
    public Person jane() { // method name = bean name
        return new Person("Jane");
    }

    @Bean
    public Conversation conversation1() {
        System.out.println("Conv1");
        return new Conversation(john(null), jane()); //
        // the onmly place in spring where a local method call is intercepted
    }
    @Bean
    public Conversation conversation2() {
        System.out.println("Conv2");
        return new Conversation(john(null), jane());
    }

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}

//class WTF extends BeanApp{
//    @Override
//    public Person jane() {
//        return super.jane();
//    }
//
//    @Override
//    public Person john() {
//        // first i look in my singleton map. if i find john already created -> i'll give you that.
//        return super.john();
//    }
//}
@RequiredArgsConstructor
@Configuration
class SecondConfig implements CommandLineRunner{
    private final List<Conversation> conversations;

    private final ApplicationContext context;

    @Override
    public void run(String... args) throws Exception {
        Person john = context.getBean("john", Person.class);
        System.out.println(john);
        // TODO convince Spring to do for you the line above
        System.out.println("Converasartion list " + conversations);
    }
}

//@Qualifier
//@Retention(RetentionPolicy.RUNTIME)
//@interface First {}
//

@Data
//@Component
class Conversation { // by default the name of this bean is = "conversation"
    private final Person john;
    private final Person jane;

//    public Conversation(Person john,  Person jane) { // if the injection point name matches a bean name => no @Qualifier is needed is needed
//        this.one = john;
//        this.two = jane;
//    }

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

    @PostConstruct
    public void method() {
        System.out.println(name + " is born");
    }

    public String sayHello() {
        return "Hello! Here is " + name;
    }
}


