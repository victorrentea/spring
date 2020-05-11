package victor.training.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.persistence.metamodel.SingularAttribute;
import java.sql.DriverManager;
import java.util.Objects;

@SpringBootApplication
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class, args);
    }

    // usecase: o clasa (externa de obicei, framework) pe care nu tre sa o intantiezi si
    // configurezi
    @Bean // recomandat: doar in clase @Configuration
    public Autor eminescu() {
        return new Autor("Eminescu");
    }
    @Bean
    public Poezie luceafarul() {
        return new Poezie("Luceafarul", eminescu());
    }
    @Bean
    public SingletonPrafuit praf() {
        return SingletonPrafuit.getInstance();
    }

    @Autowired
    private ApplicationContext context;
    @Override
    public void run(String... args) throws Exception {

        Poezie luceafarul = (Poezie) context.getBean("luceafarul");
        System.out.println("Citesc un volum de " + luceafarul);
    }
//    TODO litemode
}

class Poezie {
    private final String nume;
    private final Autor autor;
    public Poezie(String nume, Autor autor) {
        this.nume = nume;
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Poezie{" +
                "nume='" + nume + '\'' +
                ", autor=" + autor +
                '}';
    }
}
class Autor {
    private final String name;
    @Autowired
    private void setPraf(SingletonPrafuit praf) {
        System.out.println("Primesc frate " + praf);
    }
    public Autor(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autor autor = (Autor) o;
        return Objects.equals(name, autor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Autor{" +
                "name='" + name + '\'' +
                '}';
    }
}
//cod mostenit, sau oricum cod pe care nu poti sa-l schimbi
class SingletonPrafuit {
    private static SingletonPrafuit INSTANCE;
    public static SingletonPrafuit getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonPrafuit();
        }
        return INSTANCE;
    }
    private SingletonPrafuit() {}
}
