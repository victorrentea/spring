package victor.training.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
    ApplicationContext spring;
    @Autowired
//        @Qualifier("fileBasedConfigProvider")
    ConfigProvider configProvider;
    @Override
    public void run(String... args) throws Exception {
        Conversation conversation = new Conversation(new Person("John"), new Person("Jane"));
        conversation.start();
        // TODO manage all with Spring

        System.out.println(configProvider.getConfig());

        // TODO alternative: "Mirabela Dauer" story :)
        System.out.println(spring.getBean("bicicleta"));
        System.out.println(spring.getBean("bicicleta"));
        System.out.println(spring.getBean("bicicleta"));
        System.out.println(spring.getBean("bicicleta"));
    }

    @Bean
    public Person john() { // bean named "john"
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }

    @Bean
    public Singer shakira() {
        System.out.println("shakira facttory");
        return new Singer("Shakira");
    }

    @Bean
    public Song wherever() {
        return new Song("Wherever", shakira());
    }
    @Bean
    public Song bicicleta() {
        return new Song("Bicicleta", shakira());
    }
}

interface ConfigProvider {
    String getConfig();
}

@Component

@Profile("local")
@Primary
class FileBasedConfigProvider implements ConfigProvider {
    public String getConfig() {
        return "From file";
    }
}
//@Profile("!local")
@Component
class MongoBasedConfigProvider implements ConfigProvider {
    public String getConfig() {
        return "From Mongo";
    }
}

class X extends BeanApp {
//    @Override
//    public Singer shakira() {
//        if (shakira was born) return shakira from cache;
//        shakira = super.shakira();
//        shakira.inject other @Autowired
//    }
}

class Singer {
    private final String name;

    Singer(String name) {
        System.out.println(name + " is born");

        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Song { // NOT a singleton but a multi-ton
    private final String name;
    private final Singer singer;

    Song(String name, Singer singer) {
        System.out.println(name + " by " + singer.getName());
        this.name = name;
        this.singer = singer;
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
@Service
class Conversation {
    private final Person john;
    private final Person jane;

    public Conversation(/*@Qualifier("john")*/ Person john, /*@Qualifier("jane")*/Person jane) {
        this.john = john;
        this.jane = jane;
    }

    public void start() {
        System.out.println(john.sayHello());
        System.out.println(jane.sayHello());
    }
    @Override
    public String toString() {
        return String.format("Conversation{one=%s, two=%s}", john, jane);
    }

}



