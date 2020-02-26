package victor.training.springdemo;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

//class NebunFace extends NostalgieConfig {
//    @Override
//    public MirabelaDauer mirabela() {
//        // Il pacalesc
//        return super.mirabela();
//    }
//}
@Configuration
public class NostalgieConfig implements CommandLineRunner {
    @Bean // singleton by default
    public MirabelaDauer mirabela() {
        System.out.println("Acu, acu se naste");
        // unmarshal dintr-un fisier a unei instanta, si o definea ca bean
        return new MirabelaDauer();
    }

    @Bean
    @Scope("prototype")
    public Album electricVibe() {
        System.out.println("Produc album1");
        return new Album("Elektrik ViB", mirabela());
    }
    @Bean
    @Scope("prototype")
    public Album iasomieInMetrou() {
        System.out.println("Produc album2");
        return new Album("Iasomie", mirabela());
    }
    @Autowired
    ApplicationContext context;
    @Override
    public void run(String... args) throws Exception {
        mirabelaDauer.canta();
        System.out.println("Vand: " + context.getBean("electricVibe"));
        System.out.println("Vand: " + context.getBean("iasomieInMetrou"));
    }
    @Autowired
    MirabelaDauer mirabelaDauer;
}

@Data
class Album {
    private final String name;
    private final MirabelaDauer mirabela;
}


// -- intr-un jar nemodificabil --
class MirabelaDauer {
    @PostConstruct
    public void uaaa() {
        System.out.println("S-a nascut o stea");
    }
    public void canta() {
        System.out.println("Melancolie, dulce melodie...");
    }
}