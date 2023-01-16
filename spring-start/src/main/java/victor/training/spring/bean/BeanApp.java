package victor.training.spring.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class BeanApp {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class);
    }

    @Bean
    public Person john() {// inca singleton. spring iti va da mereu aceeasi instanta !
        // da; eu stiam din facultate ca o metoda chemata de 2 ori chiar ruleaza de 2 ori
        // sr: .........
        System.out.println("De cate ori, iubito?");
        return new Person("John");
    }
    @Bean
    public Person jane() {
        return new Person("Jane");
    }

    @Bean
    public Conversation cearta() { // injectie de dep in param metodei @Bean
        System.out.println("Cearta ACUM!");
        return new Conversation(john(), jane());

        // in practica cand vezi un apel la o met @Bean din alta metoda bean, consider-o o 'referinta' nu un apel.
    }
    @Bean
    public Conversation impacarea() {
        System.out.println("Impacarea ACUM!");
        return new Conversation(john(), jane());
    }
}
// magie: Spring, la pornire, genereaza o subclasa
class HackTata extends BeanApp {
    @Override
    public Person john() {
        // if (singletomap.contains("john")) return din mapa;
        return super.john();
        // put in map
    }
}

@Component
class ModuPericulosDeAObtReferinteLaBean {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Person john; // bun< pt ca iti crapa mai repede! la startup

    public void method() {
        Person john = applicationContext.getBean("john", Person.class);
        // rau ca-mi crapa cand/daca se apeleaza
        // surpriza dupa 2 h.
    }
}

@Component
class CandCoduSeImbacsesteMereuEBineSaIeiOGuraDeAerProaspat_ClasaNoua implements CommandLineRunner{
    @Autowired
    private Conversation impacarea;

    @Override
    public void run(String... args) throws Exception {
        impacarea.start();
    }
}
/// ---- sub linie consideram librarie NU POTI modifica

@Data
class Conversation {
    private final Person one;
    private final Person two;

    public Conversation(Person one, Person two) {
        this.one = one;
        this.two = two;
    }

    public void start() {
        System.out.println("Starting conversation...");
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


