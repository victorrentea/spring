package victor.training.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
    ConverSation conversation;
    @Autowired
    ApplicationContext spring;
    @Autowired
    private UserClient client;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Until you do .getBean('str') the app works with a bug in prod");
            conversation.start();;
        System.out.println(spring.getBean("converSation"));
        System.out.println(client.loadUser("jdoe"));

        System.out.println(spring.getBean("imperfect",CD.class));
        System.out.println(spring.getBean("imperfect",CD.class));
        System.out.println(spring.getBean("imperfect",CD.class));
        System.out.println(spring.getBean("imperfect",CD.class));
    }

    @Bean
    public Person john() {
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }
    @Bean
    public Singer carla() {
        return new Singer("Carla's Dream");
    }
    @Bean
    @Scope("prototype")
    public CD imperfect() {
        return new CD("Imperfect", carla());
    }
}

//class HACK extends BeanApp {
//    @Override
//    public Singer carla() {
//        if (singleton ) return da din cache
//        Singer carla = super.carla();
//        rulPostConstruct(carla)
//        return carla;
//    }
//}



class Singer {
    private final String name;

    Singer(String name) {
        this.name = name;
        System.out.println("Se naste o stea");
    }
    @PostConstruct
    public void init() {
        System.out.println("UAAAA");
    }
}
class CD {
    private final String title;
    private final Singer singer;

    CD(String title, Singer singer) {
        this.title = title;
        this.singer = singer;
        System.out.println("Generez o instanta de CD");
    }
}


interface UserClient {
    int loadUser(String username);
}
@Component
class UserManagementSystemClient implements UserClient{
    @Override
    public int loadUser(String username) {
        // REST call
        return 1;
    }
}

@Primary
@Profile("local")
@Component
class DummyUserClient implements UserClient{
    public int loadUser(String username)  {
        // load din properties file
        return -1;
    }
}






class Person {
    private final String name;

    public Person(String name) {
        System.out.println("new " + name);
        this.name = name;
    }
    @PostConstruct
    public void senaste() {
        System.out.println("Uaaa");
    }

    public String sayHello() {
        return "Hello! Here is " + name + " from " + OldClass.getInstance().getCurrentCountry();
    }
}

@Component
class ConverSation {
    private final Person one;
    private final Person two;

    public ConverSation(Person jane,  Person john) {
        this.one = jane;
        this.two = john;
    }

    public void start() {
        System.out.println(one.sayHello());
        System.out.println(two.sayHello());
    }
}
