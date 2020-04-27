package bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;


@SpringBootApplication
public class App2 implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(App2.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
    @Autowired
    private DataSource ds;
    public void m() {
        LegacyClass legacyClass = new LegacyClass(ds); // dependinte pe care le cere
        legacyClass.init();// protocol de interactiune complex
        legacyClass.varzaMurata(); // daca as vrea as interceptez acest apel cu Spring AOP
    }
}

class ClasaMea {
    @Autowired
    private DataSource ds;
    // am nevoie de LegacyClass
    public void m() {
        LegacyClass legacyClass = new LegacyClass(ds);
        legacyClass.init();
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

    }
}

@Service
class DataSource {

}

