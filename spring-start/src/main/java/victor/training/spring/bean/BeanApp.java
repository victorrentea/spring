package victor.training.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
    Conversation impacarea;
    @Override
    public void run(String... args) throws Exception {
//        Conversation conversation = new Conversation(new Person("John"), new Person("Jane"));
        impacarea.start();
        // TODO manage all with Spring
    }
}

//@Retention(RetentionPolicy.RUNTIME)
//@Qualifier
//@interface Galben{
//
//}

@Configuration
class MyConfiguration {
    @Bean
    public Person john(@Value("${person.john.name:NuMaFolosi}") String name) { // creezi manual un bean de tip Person numit "john"
        System.out.println("Se naste John 👶");
        return new Person(name);
    }

    @Bean
//    @Galben
    public Person jane(@Value("${person.jane.name}") String name) {
        return new Person(name /*si alte prop*/);
    }

    @Bean
    public Conversation cearta() {
        System.out.println("^!%$@!^(%*&!*^!");
        return new Conversation(john(null), jane(null));
    }

    @Bean
    public Conversation impacarea() {
        System.out.println("💘💘💘");
        return new Conversation(john(null), jane(null));
    }
}

//class HackarealaGenerataLaRuntime extends MyConfiguration {
//    @Override
//    public Person john() {
//        if (am deja creat pe john) return din cache
//             else
//        return pun in mapa si intorc pe (super.john()) dupa ce-l injecteaza cum se cuvine;
//    }
//}


// nu ai voie sa atingi clasa de mai jos
@Component
class Conversation {
    private final Person one;
    private final Person two;

    public Conversation(Person john,/*@Galben*/ Person jane) {
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
        this.name = name;
    }
    public String sayHello() {
        return "Hello! Here is " + name + " from " + OldClass.getInstance().getCurrentCountry();
    }
}


