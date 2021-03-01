package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        Conversation conversation = applicationContext.getBean(Conversation.class);
        conversation.start();
        Conversation conversation2 = (Conversation) applicationContext.getBean("conversation");
        conversation2.start();
        // TODO manage all with Spring

        // scop: sa poti obtine conversatii intre John si Jane cate vrei, si sa le poti da .start()
    }

    @Bean
    public OldClass oldClass() {
        return OldClass.getInstance();
    }

    @Bean
    // <bean id="john" class="victor.training.spring.bean.Person" c:name="John" />
    public Person john() {
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }
    @Bean
    @Scope("prototype")
    public Conversation conversation() {
        Person john = john();
        System.out.println("Ce fel de jhon esti tu? " + john.getClass());
        return new Conversation(john, jane());
    }
}

//class DusmanuSpring extends BeanApp {
//    @Override
//    public Person jane() {
//        return jane din cache;
//    }
//}


class Conversation {
    private final Person john;
    private final Person jane;

    public Conversation(Person john, Person jane) {
        System.out.println("New conver  "+ hashCode());
        this.john = john;
        this.jane = jane;
    }

    public void start() {
        System.out.println("Incepe conversatia " + hashCode());
        System.out.println(john.sayHello());
        System.out.println(jane.sayHello());
    }
}


class Person {
    private final String name;

    public Person(String name) {
        System.out.println("Creez pe " + name);
        this.name = name;
    }
    @PostConstruct
    public void method() {
        System.out.println("Oare eu cand rulez");
    }

    public String sayHello() {
        return "Hello! Here is " + name + " from " + OldClass.getInstance().getCurrentCountry();
    }
}


