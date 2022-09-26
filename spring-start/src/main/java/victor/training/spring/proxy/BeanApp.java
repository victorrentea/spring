package victor.training.spring.proxy;

import lombok.Data;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public void run(String... args) throws Exception {
        Person p1 = applicationContext.getBean(Person.class); // dangerous
        System.out.println(p1);
        Person p2 = applicationContext.getBean(Person.class);
        System.out.println(p2);
        Conversation conversation = new Conversation(new Person(), new Person());
        // TODO convince Spring to do for you the line above
        conversation.start();
    }
}

@RestController
class MyController {
    private final ObjectFactory<Person> personFactor; // Pitfall: injecting a prototype-scoped into a singleton! => only creates a single prototype

    MyController(ObjectFactory<Person> personFactor) {
        this.personFactor = personFactor;
    }

    @Autowired
    private HttpServletRequest httpServletRequest;

    @GetMapping
    public String method() {
        Person p = personFactor.getObject(); //~ applicationContext.getBean
        System.out.println("The injected request is injected a single time. However, when I get data from, it gets me the ```current``` request: \n"
                           + System.identityHashCode(httpServletRequest));
        System.out.println("It's a  proxy: " + httpServletRequest.getClass());
        return "different instance for each call ? " + p + "     request=  " + httpServletRequest.getRequestURI();
    }
    @GetMapping("two")
    public String method2() {
        Person p = personFactor.getObject(); //~ applicationContext.getBean
        System.out.println("The injected request is injected a single time. However, when I get data from, it gets me the ```current``` request: \n"
                           + System.identityHashCode(httpServletRequest));
        System.out.println("It's a  proxy: " + httpServletRequest.getClass());
        return "different instance for each call ? " + p + "     request=  " + httpServletRequest.getRequestURI();
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


@Component
@Scope("prototype")
class Person {
    private final String name;



    public Person() {
        this.name = "name";
    }
    public String sayHello() {
        return "Hello! Here is " + name;
    }
}

