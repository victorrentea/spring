package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
    Conversation conversation;

    @Autowired
    ConfigProvider configProvider;

//    @Bean
//    public OldClass method() {
//        return OldClass.getInstance();
//    }

    @Override
    public void run(String... args) throws Exception {
//        Conversation conversation = new Conversation(new Person("John"), new Person("Jane"));
        conversation.start();
        System.out.println(configProvider.getConfig());
        // TODO manage all with Spring

        // TODO alternative: "Mirabela Dauer" story :)
    }

}
class X extends DefiningBeanInNonConfig {
    @Override
    public Person john() {
        // will return the same (singleton) instance
        return super.john(); // spring will do tihs automatically for any Configuration !

        // to run @PostConstruct
        // to wrap it in a proxy
        //

        // The only place in Spring in which "LOCAL METHOD CALLS ARE PROXIED"
    }
}
@Configuration
class DefiningBeanInNonConfig {
    @Bean
    public Person john() {
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }
//    @Bean
//    public Conversation conversation() {
//        return new Conversation(john(), jane());
//    }
//    @Bean
//    public Conversation conversation2() {
//        return new Conversation(john(), jane());
//    }
}


interface ConfigProvider {
    String getConfig();
}

//@Profile("!local")
@Component
class ProdConfigProvider implements ConfigProvider{
    public String getConfig() {
        return "from the vault";
    }
}
@Component
@Profile("local")
@Primary
class DummyConfigProvider implements ConfigProvider{
    public String getConfig() {
        return "from the file system";
    }
}


class Person {
    private final String name;
    public Person(String name) {
        System.out.println(name + " is born");
        this.name = name;
    }
    @PostConstruct
//    @Cacheable
//    @Transactional
    public String sayHello() {
        return "Hello! Here is " + name + " from " + OldClass.getInstance().getCurrentCountry();
    }
}
@Component
@Data
class Conversation {
    private final Person one;
    private final Person two;

    public Conversation(Person john, Person jane) {
        this.one = john;
        this.two = jane;
    }
    public void start() {
        System.out.println(one.sayHello());
        System.out.println(two.sayHello());
    }

}



