package victor.training.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

//class Tzeapa extends BeanApp {
//    @Override
//    public Autor eminescu() {
//        return super.eminescu();
//    }
//}
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
public class BeanApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class, args);
    }

    // usecase: o clasa (externa de obicei, framework) pe care nu tre sa o intantiezi si
    // configurezi
    @Bean // recomandat: doar in clase @Configuration
    public Autor eminescu() {
        System.out.println("Nasc un geniu");
        return new Autor("Eminescu");
    }
    @Bean
    @Profile("!digital")
    @Scope("prototype")
    public Poezie luceafarul() {
        System.out.println("Printez poezia");
        //cand chem aicea eminescu de fapt nu se invoca metoda de sus, ci
        // Springu iti subleaseaza si hackuie clasa BeanApp
        // ca orice metoda publica chemi sa rule el:
        // nu va chema de fapt functia aceea cand vrei tu
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

        // language=sql
        String sql = "SELECT * FROM TEACHER " +
                " LEFT JOIN TRAINING T on TEACHER.ID = T.TEACHER_ID";

        System.out.println("Citesc un volum de " + context.getBean("luceafarul"));
        System.out.println("Citesti si tu un ALT volum de " + context.getBean("luceafarul"));
    }
//    TODO litemode
}

@Profile("digital")
@Configuration
class EraDigitala {
    @Autowired
    Autor eminescu;
    @Bean
    @Scope("prototype")
    public PostareLirica luceafarul() throws MalformedURLException {
        return new PostareLirica("Luceafarul", eminescu, new URL("http://google.com"));
    }
    @Bean
    @Scope("prototype")
    public PostareLirica baietEram() throws MalformedURLException {
        return new PostareLirica("Baiet Eram", eminescu, new URL("http://google.com"));
    }
}
class PostareLirica {
    private final String nume;
    private final Autor autor;
    private final URL url;
    public PostareLirica(String nume, Autor autor, URL url) {
        this.nume = nume;
        this.autor = autor;
        this.url = url;
    }
}
class Poezie {
    private final String nume;
    private final Autor autor;
    public Poezie(String nume, Autor autor) {
        this.nume = nume;
        this.autor = autor;
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

    @PostConstruct
    public void m() {
        System.out.println("PostConstruct in singleton");
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
