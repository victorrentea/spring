package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication()
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
    Conversation conversation;
    @Autowired
    ApplicationContext spring;
//    @Autowired
//        @Qualifier("Jane")
//    Person jane;

    @Override
    public void run(String... args) throws Exception {
//        Conversation conversation = new Conversation(new Person("John"), new Person("Jane"));
        conversation.start();
        // TODO manage all with Spring
//        System.out.println("TOTU BUN PANA AICI--");

//        System.out.println(spring.getBean("Jane"));
//        System.out.println(jane);
        // TODO alternative: "Mirabela Dauer" story :)
    }
    @Bean
//    @Primary
    public Person john() {
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }

    @Bean
    public Song vals() {
        System.out.println("call 1");
        return new Song("Vals", smiley());
    }
    @Bean
    public Singer smiley() {
        return new Singer("Smiley");
    }

    @Bean
    public Song acasa() {
        System.out.println("call 2");
        return new Song("Acasa", smiley());
    }


    @Autowired
    UnaDinBorcan cireasa;
    @Bean
    public UnaDinBorcan method() {
        return new UnaDinBorcan().setX("custom");
    }
}

class UnaDinBorcan {
    private String x;

    public UnaDinBorcan setX(String x) {
        this.x = x;
        return this;
    }
}



@Data
@Component("component")
class Conversation {
    private final Person john;
    private final Person jane;

    public Conversation(Person john, Person jane) {
        this.john = john;
        this.jane = jane;
    }

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
        return "Hello! Here is " + name + " from " + OldClass.getInstance().getCurrentCountry();
    }
}


