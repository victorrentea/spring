package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.ZoneId;

@SpringBootApplication// (proxyBeanMethods = false) disableaza behaviorul explicat in commitul asta
//@Configuration
public class BeanApp {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

//    @Autowired
//    private Conversation conversation;

    @EventListener(ApplicationStartedEvent.class)
    public void onStart() {
        Conversation conversation = new Conversation(new Person("John"), new Person("Jane"));
        // TODO register the two persons and the conversation as Spring beans
        conversation.start();
    }
    @Bean
    public Conversation cearta() {
        System.out.println("Se cearta?");
        // apelurile dintr-un @bean in alt @bean sunt interceptate de spring, iti trage o tzeapa
        // ⚠️⚠️⚠️⚠️⚠️ singurul loc in tot springu unde metodele chemate local sunt interceptate
        return new Conversation(john(null), jane());
    }
    @Bean
    public Person john(@Value("${db.password}") String pass) {
        System.out.println("De cate ori se naste John: " + pass);
        return new Person("John");
    }

    @Bean
    public Person jane() {
        return new Person("Jane");
    }
    @Bean
    public Conversation impacarea() {
        System.out.println("Se impaca?");
        return new Conversation(john(null), jane());
    }

    @Bean
    public Clock clock() {
        return Clock.system(ZoneId.systemDefault());
    }
}

//class SpringSubCapot extends BeanApp {
//    @Override
//    public Person john(String pass) {
//        if (am in cache pe john) return din cache;
//          ia pass din props
//        return super.john(pass);
//        put in cache
//    }
//}

// -- intr-un jar ce-i mai jos
// vvvvv nu pot pune @Component
@Data
class Conversation {
    private final Person one;
    private final Person two;

    public Conversation(Person one, Person two) {
        this.one = one;
        this.two = two;
    }

    public void start() {
        System.out.println(one.getName() + " talks with " + two.getName());
    }
}


class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}


