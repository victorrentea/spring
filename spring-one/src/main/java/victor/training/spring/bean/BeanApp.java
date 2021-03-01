package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

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
    // <bean id="john" class="victor.training.spring.bean.Person" c:name="John" />
    public Person john() {
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }

}


@Service
@Data
class Conversation {
    private final Person one;
    private final Person two;

    public Conversation(@Qualifier("john") Person one, @Qualifier("jane")Person two) {
        this.one = one;
        this.two = two;
    }

    public void start() {
        System.out.println("Incepe conversatia " + hashCode());
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


