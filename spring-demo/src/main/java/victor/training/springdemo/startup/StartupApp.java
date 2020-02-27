package victor.training.springdemo.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

@SpringBootApplication
public class StartupApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(StartupApp.class, args);
    }

    @Autowired
    B b;

    public StartupApp() {
        // aici NU POT sa folosesc b pentru ca b nu a fost inca injectat. E prea devreme.
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("CommandLineRunner");
    }
    @PostConstruct
    public void ceva() {
        System.out.println("PostConstruct");
    }

}
@Service
class DummyDataInserter {
    private DataSource ds;
    static {
        // INSERT nu merg ca nu ai cum sa obt conex la DB
    }

    //@Transactional
    @EventListener
    public void onInit(ApplicationReadyEvent event) {
        // ai ds si esti intr-o tx.
        System.out.println("ApplicationReadyEvent");
//       throw  new IllegalArgumentException();
    }
    @PreDestroy
    public void curat() {
        System.out.println("Curat");
    }
}
@Service
class B {
    public void m() {

    }
}
