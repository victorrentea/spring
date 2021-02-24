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
    }

    @Bean
    public Person john() {
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
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
