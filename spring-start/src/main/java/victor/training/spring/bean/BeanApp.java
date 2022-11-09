package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SpringBootApplication
//@Configuration // in cele din e si asta
public class BeanApp {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Bean
    public Conversation conversation() { // numele bean = numele metodei
        return new Conversation(a(), b());
    }
    @Bean
    public Conversation cearta() {
        return new Conversation(a(), b());
    }

    @Bean
    public Person a() {
        return new Person("John");
    }

    @Bean
    public Person b() {
        return new Person("Jane");
    }
// in realitate
//    @Bean
//    public ThreadPoolTaskExecutor method() {
//        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
//        threadPool.setCorePoolSize(10);
//        threadPool.setQueueCapacity(100);
//        return threadPool;
//    }
}

@Component
class StartUp { // numele ei default este = "startUp" (exactNumele clasei cu lower lainceput

    @Autowired
//    @Qualifier("cearta")
    private Conversation cearta; // recent @Qualifier nu mai e necesar dc numele
    // 'punctului de injectie' matcheuieste numele beanului -> se prinde

    @PostConstruct
    public void run() throws Exception {
        cearta.start();
    }
}

@Data
class Conversation {
    private final Person one;
    private final Person two;

    public Conversation(Person one, Person two) {
        this.one = one;
        this.two = two;
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
        return "Hello! Here is " + name;
    }
}


