package victor.training.springdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NostalgieConfig implements CommandLineRunner {
    @Bean
    public MirabelaDauer mirabela() {
        // unmarshal dintr-un fisier a unei instanta, si o definea ca bean
        return MirabelaDauer.getInstance();
    }




    @Autowired
    MirabelaDauer mirabelaDauer;

    @Override
    public void run(String... args) throws Exception {
        mirabelaDauer.canta();
    }
}


// -- intr-un jar nemodificabil --
class MirabelaDauer {
    private static MirabelaDauer INSTANCE;

    private MirabelaDauer() {
        System.out.println("S-a nascut o stea");
    }

    public static MirabelaDauer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MirabelaDauer();
        }
        return INSTANCE;
    }
    public void canta() {
        System.out.println("Melancolie, dulce melodie...");
    }
}