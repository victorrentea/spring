package spring.training.resurse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import spring.training.ThreadUtils;

import javax.annotation.PostConstruct;
import java.util.Locale;

@SpringBootApplication
public class PropertiesApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(PropertiesApp.class);
    }

    @Value("${my.secret:12345678}")
    String secret;

    @Bean
    public OldClass oldClass() {
        return OldClass.getInstance();
    }

    @Bean
    public AltaClasa altaClasa(
            @Value("${parola.critica:12345678}") String parola,
            OldClass oldClass) {
        System.out.println("In altaClasa()");
        return new AltaClasa(oldClass, parola, oldClass.getCurrentIp().split("\\."));
    }
    @Bean
    public FinalaMica mica(AltaClasa altaClasa) {
        System.out.println("Instantiez finala mic");
        return new FinalaMica(altaClasa);
    }
    @Bean
    public FinalaMica finalaMica2(AltaClasa altaClasa) {
        System.out.println("Instantiez finala mic");
        return new FinalaMica(altaClasa);
    }

    @Autowired
    private MessageSource messageSource;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Parola este: " + secret);
        Locale locale = new Locale("RO", "RO");
        try {
            altaMetoda(-1);
        } catch (MyException e) {
            String key = e.getCode().code;
            String message = messageSource.getMessage(key, null, locale);
            System.out.println("Fraere: eroare: " + message);
        }
        System.out.println("props: " + props);
    }

    @Autowired
    private StructuredProps props;


    private void altaMetoda(int i) {
        if (i < 0) {
            throw new MyException(MyException.ErrorCode.I_NEGATIVE);
        }
    }
}


//@Component
@RequiredArgsConstructor
class AltaClasa {
    private final OldClass veche;
    private final String parola;
    private final String[] ipParts;
    @PostConstruct
    public void hello() {
        System.out.println("Post Construct in AltaClasa");
        veche.method();
    }
}

//@Component
@RequiredArgsConstructor
class FinalaMica {
    private final AltaClasa altaClasa;
    @PostConstruct
    public void hellomica() {
        System.out.println("Finala mica");
    }
}


// no permission not change
class OldClass {
    private static OldClass INSTANCE;
    private OldClass() {
        currentIp = resolveIp();
    }

    private String resolveIp() {
        ThreadUtils.sleep(1000);
        return "127.0.0.1";
    }

    public static OldClass getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OldClass();
        }
        return INSTANCE;
    }

    private String currentIp;

    public String getCurrentIp() {
        return currentIp;
    }

    public void method() {
        System.out.println("Hello world!");
    }
}
