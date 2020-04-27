package bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.security.auth.login.AppConfigurationEntry;

@Configuration
@Profile("!prod") // NICIOADATA SA NU DEF UN PROFIL PROD.
// inseamna ca ai cod/config care ruleaza prima data doar in PROD

// Pair Programming, Continuous Integration, TDD, Refactoring
public class PoezieConfig  {
    @Bean
    public Autor eminescu() {
        System.out.println("Se naste un geniu");
        return new Autor("Eminescu");
}
    @Bean
    @Scope("prototype")
    public Poezie luceafarul() {
        System.out.println("Se printeaza poezia");
        return new Poezie("Luceafarul", eminescu());
    }
}

@Component
class Cititor implements  CommandLineRunner {
    @Autowired
    ApplicationContext spring;
    @Override
    public void run(String... args) throws Exception {
        System.out.println(System.identityHashCode(spring.getBean("luceafarul")));
        System.out.println(System.identityHashCode(spring.getBean("luceafarul")));
//        System.out.println(new Cititor());
    }
}

// n-ai voie sa le atingi
@Data
class Autor {
    private final String nume;
    @PostConstruct
    public void beu() {
        System.out.println("Boem");
    }
}

@Data
class Poezie {
    private final String nume;
    private final Autor autor;
}
