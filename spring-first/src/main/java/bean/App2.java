package bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class App2 implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(App2.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
    @Bean//(name = "legacy")
    public LegacyClass legacy() {
        System.out.println("Instantiez");
        LegacyClass legacyClass = new LegacyClass(ds); // dependinte pe care le cere
        legacyClass.init();// protocol de interactiune complex
        return legacyClass;
    }

    @Autowired
    private DataSource ds;


    @Autowired
    LegacyClass legacyClass;
    @PostConstruct
    public void m() {
        legacyClass.varzaMurata();
    }
}
@Service
class ClasaMea {
    @Autowired
    LegacyClass legacyClass;
    @PostConstruct
    public void m() {
        legacyClass.varzaMurata();
    }
}

// e intr-un jar pe care nu poti sa il modifici.
// eg. librarie, sau app veche pe care nu vrei/poti sa o faci sa stie de Spring
class LegacyClass {
    private final DataSource ds;

    LegacyClass(DataSource ds) {
        this.ds = ds;
    }
    public void init() {
    }

    public void varzaMurata() {
        System.out.println("Sarmale");
    }
}

@Service
class DataSource {

}

